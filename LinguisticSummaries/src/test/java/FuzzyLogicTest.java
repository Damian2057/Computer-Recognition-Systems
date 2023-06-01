import org.testng.Assert;
import org.testng.annotations.Test;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.summary.MultiSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.util.Combiner;
import p.lodz.pl.backend.fuzzy.summary.SingleSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.fuzzy.util.Operation;
import p.lodz.pl.backend.fuzzy.util.Pair;
import p.lodz.pl.backend.fuzzy.util.SubjectExtractor;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;
import p.lodz.pl.backend.repository.MockRepository;
import java.util.Collections;
import java.util.List;

public class FuzzyLogicTest {

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
        MockRepository mockRepository = new MockRepository();
        Dao dao = new DBConnection();

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

        for (Summary s : summaries) {
            String result = s.form() + " " + s.summary() + " " + s.quality();
            System.out.println(result);
        }

        Assert.assertEquals(summaries.size(), 19);
    }

    @Test
    public void multiSubjectSummaryTest() {
        MockRepository mockRepository = new MockRepository();
        Dao dao = new DBConnection();
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
//        LinguisticLabel<PolicyEntity> label4 = linguisticVariable.getLabels().get(3);
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

        for (Summary s : summaries) {
            String result = s.form() + " " + s.summary() + " " + s.quality();
            System.out.println(result);
        }

        Assert.assertEquals(summaries.size(), 38);
    }
}
