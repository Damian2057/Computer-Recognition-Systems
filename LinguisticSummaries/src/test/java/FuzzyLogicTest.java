import lombok.extern.java.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import p.lodz.pl.backend.fuzzy.function.GaussianFunction;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.summary.MultiSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.SingleSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.fuzzy.util.Combiner;
import p.lodz.pl.backend.fuzzy.util.Operation;
import p.lodz.pl.backend.fuzzy.util.Pair;
import p.lodz.pl.backend.fuzzy.util.SubjectExtractor;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;
import p.lodz.pl.backend.repository.MockRepository;

import java.util.ArrayList;
import java.util.List;

@Log
public class FuzzyLogicTest {

    private MockRepository mockRepository;
    private Dao dao;

    @BeforeTest
    public void setUp() {
        this.mockRepository = new MockRepository();
        this.dao = new DBConnection();
    }

    @Test
    public void OperationTest() {
        FuzzySet<Double> fuzzySet = new FuzzySet<>(x -> x, new TriangularFunction(new ContinuousDomain(0, 1), 0, 0.5, 1));
        double a = fuzzySet.getMemberShip(0.5);
        Operation<FuzzySet<Double>> operation = new Operation<>();
        FuzzySet<Double> fuzzySet1 = operation.not(fuzzySet);
        double b = fuzzySet1.getMemberShip(0.5);

        Assert.assertNotEquals(a, b);
    }

    @Test
    public void continuousDomainAndDiscreteDomainTest() {
        ContinuousDomain domain = new ContinuousDomain(0, 1);
        FuzzySet<Double> fuzzySet = new FuzzySet<>(x -> x, new TriangularFunction(domain, 0, 0.5, 1));
        List<Double> points = new ArrayList<>();
        points.add(-1.0);
        points.add(2.0);
        points.add(3.0);
        DiscreteDomain discreteDomain = new DiscreteDomain(points);
        FuzzySet<Double> fuzzySet1 = new FuzzySet<>(x -> x, new TriangularFunction(discreteDomain, 0, 0.5, 1));
        Operation<FuzzySet<Double>> operation = new Operation<>();
        FuzzySet<Double> fuzzySetOp = operation.and(fuzzySet, fuzzySet1);
        Assert.assertEquals(fuzzySetOp.<ContinuousDomain>getDomainCast().getMinDomain(),
                -1.0);
        Assert.assertEquals(fuzzySetOp.<ContinuousDomain>getDomainCast().getMaxDomain(),
                3.0);

    }

    @Test
    public void gaussianFunctionTest() {
        ContinuousDomain domain = new ContinuousDomain(0, 1);
        FuzzySet<Double> fuzzySet = new FuzzySet<>(x -> x, new GaussianFunction(domain, 0.5, 0.5));
        double value = fuzzySet.support() / fuzzySet.getDomain().width();
        Assert.assertEquals(value, 1);

    }

    @Test
    public void logicFirstFormTest() {
        int size = Combiner.getFirstFormCombinations(1, 3).size();
        Assert.assertEquals(size, 7);
    }

    @Test
    public void logicSecondFormTest() {
        int size = Combiner.getSecondFormCombinations(3).size();
        Assert.assertEquals(size, 12);
    }

    @Test
    public void oneSubjectSummaryTest() {
        Quantifier quantifier = mockRepository.findAllQuantifiers().get(1);
        LinguisticVariable<PolicyEntity> linguisticVariable = mockRepository.findAllLinguisticVariables().get(0);
        LinguisticLabel<PolicyEntity> label1 = linguisticVariable.getLabels().get(0);
        LinguisticLabel<PolicyEntity> label2 = linguisticVariable.getLabels().get(1);
        LinguisticLabel<PolicyEntity> label3 = linguisticVariable.getLabels().get(2);
//        LinguisticLabel<PolicyEntity> label4 = linguisticVariable.getLabels().get(3);
        List<LinguisticLabel<PolicyEntity>> labels = List.of(label1, label2, label3);

        List<Double> weights = new java.util.ArrayList<>(List.of(0.3, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07));
        weights.add(0.07);
        weights.add(0.07);

        SingleSubjectLinguisticSummary<PolicyEntity> linguisticSummary = new SingleSubjectLinguisticSummary<>(quantifier,
                labels,
                "car",
                dao.getPolicies(),
                weights);
        List<Summary> summaries = linguisticSummary.generateSummary();

        summaries.forEach(s -> log.info(s.form() + " " + s.summary() + " " + s.quality()));

        Assert.assertEquals(summaries.size(), 19);
    }

    @Test
    public void multiSubjectSummaryTest() {
        List<PolicyEntity> policyEntities = dao.getPolicies();

        //Filter
        LinguisticLabel<PolicyEntity> filter = mockRepository
                .findLinguisticLabelByName("hatchback");

        //Quantifier and labales
        Quantifier quantifier = mockRepository.findAllQuantifiers().get(1);
        LinguisticVariable<PolicyEntity> linguisticVariable = mockRepository.findAllLinguisticVariables().get(0);
        LinguisticLabel<PolicyEntity> label1 = linguisticVariable.getLabels().get(0);
        LinguisticLabel<PolicyEntity> label2 = linguisticVariable.getLabels().get(1);
        LinguisticLabel<PolicyEntity> label3 = linguisticVariable.getLabels().get(2);
        LinguisticLabel<PolicyEntity> label4 = linguisticVariable.getLabels().get(3);
        List<LinguisticLabel<PolicyEntity>> labels = List.of(label1, label2, label3);

        Pair<List<PolicyEntity>, List<PolicyEntity>> pair =
                SubjectExtractor.extract(policyEntities, p -> filter.getMemberShip(p) > 0.0);


        MultiSubjectLinguisticSummary<PolicyEntity> linguisticSummary =
                new MultiSubjectLinguisticSummary<>(quantifier,
                        labels,
                        "policies for a hatchback car",
                        "rest",
                        pair.getFirst(),
                        pair.getSecond());

        List<Summary> summaries = linguisticSummary.generateSummary();
        summaries.addAll(linguisticSummary.generateFourthForm());

        summaries.forEach(s -> log.info(s.form() + " " + s.summary() + " " + s.quality()));

        Assert.assertEquals(summaries.size(), 38);
    }

    @Test
    public void fuzzyLogicTest() {
        List<LinguisticVariable<PolicyEntity>> linguisticVariable = mockRepository.findAllLinguisticVariables();
        List<PolicyEntity> entities = dao.getPolicies();

        for (LinguisticVariable<PolicyEntity> variable : linguisticVariable) {
            for (LinguisticLabel<PolicyEntity> label : variable.getLabels()) {
                System.out.println(label.getLabelName());
                Assert.assertTrue(label.isNormal());
                Assert.assertTrue(label.isConvex());
                Assert.assertFalse(label.isEmpty(entities));
                Assert.assertEquals(label.heightOfSet(), 1.0);
            }
        }

    }
}
