package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.util.Combiner;
import p.lodz.pl.backend.fuzzy.util.Operation;
import p.lodz.pl.backend.fuzzy.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleSubjectLinguisticSummary<R> extends AbstractLinguisticSummary {

    private final List<LinguisticLabel<R>> qualifiers;
    private final String subject;
    private final List<R> policies;
    private final List<Double> weights;

    private List<Summary> summaries;

    public SingleSubjectLinguisticSummary(Quantifier quantifier,
                                          List<LinguisticLabel<R>> qualifiers,
                                          String subject, List<R> policies, List<Double> weights) {

        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject = subject;
        this.policies = policies;
        this.weights = weights;
    }

    @Override
    public List<Summary> generateSummary() {
        summaries = new ArrayList<>();
        generateFirstForm();
        if (!quantifier.isAbsolute()) {
            generateSecondForm();
        }
        return summaries;
    }

    private void generateFirstForm() {
        final int form = 1;
        List<List<Integer>> combinations = Combiner.getFirstFormCombinations(1, qualifiers.size());

        for (List<Integer> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject).append(HAVE);
            int index = 0;
            List<FuzzySet<R>> fuzzySets = new ArrayList<>();
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
                fuzzySets.add(qualifiers.get(i).getFuzzySet());
            }
            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary(Collections.emptyList(), fuzzySets)));
        }
    }

    private void generateSecondForm() {
        final int form = 2;
        List<Pair<List<Integer>, List<Integer>>> combinations = Combiner.getSecondFormCombinations(qualifiers.size());
        for (Pair<List<Integer>, List<Integer>> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject).append(THAT);
            int index = 0;
            List<FuzzySet<R>> qua = new ArrayList<>();
            for (int i : combination.getFirst()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getFirst().size()) {
                    stringBuilder.append(AND);
                }
                qua.add(qualifiers.get(i));
            }
            stringBuilder.append(ALSO);
            index = 0;
            List<FuzzySet<R>> sum = new ArrayList<>();
            for (int i : combination.getSecond()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getSecond().size()) {
                    stringBuilder.append(AND);
                }
                sum.add(qualifiers.get(i));
            }

            summaries.add(new Summary(form, stringBuilder.toString(), getQualityForSummary(qua, sum)));
        }
    }

    public List<LinguisticLabel<R>> getQualifiers() {
        return qualifiers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    private List<Double> getQualityForSummary(List<FuzzySet<R>> qualifierSet,
                                              List<FuzzySet<R>> summarizerSet) {
        double T1 = checkNan(degreeOfTruth(qualifierSet, summarizerSet));
        double T2 = checkNan(degreeOfImprecision(summarizerSet));
        double T3 = checkNan(degreeOfCovering(qualifierSet, summarizerSet));
        double T4 = checkNan(degreeOfAppropriateness(qualifierSet, summarizerSet));
        double T5 = checkNan(lengthOfSummary(summarizerSet.size()));
        double T6 = checkNan(degreeOfQuantifierImprecision());
        double T7 = checkNan(degreeOfQuantifierCardinality());
        double T8 = checkNan(degreeOfSummarizerCardinality(summarizerSet));
        double T9 = checkNan(degreeOfQualifierImprecision(qualifierSet));
        double T10 = checkNan(degreeOfQualifierCardinality(qualifierSet));
        double T11 = checkNan(lengthOfQualifier(qualifierSet.size()));

        if (T3 > 1.0) {
            System.out.println("xd");
        }
        if (T3 * weights.get(3) > 1.0) {
            System.out.println("xd");
        }
        double avg = T1 * weights.get(0)
                + T2 * weights.get(1)
                + T3 * weights.get(2)
                + T4 * weights.get(3)
                + T5 * weights.get(4)
                + T6 * weights.get(5)
                + T7 * weights.get(6)
                + T8 * weights.get(7)
                + T9 * weights.get(8)
                + T10 * weights.get(9)
                + T11 * weights.get(10);

        List<Double> quality = new ArrayList<>();
        quality.add(avg);
        quality.add(T1);
        quality.add(T2);
        quality.add(T3);
        quality.add(T4);
        quality.add(T5);
        quality.add(T6);
        quality.add(T7);
        quality.add(T8);
        quality.add(T9);
        quality.add(T10);
        quality.add(T11);

        return quality;
    }

    /**
     * T1
     */
    private double degreeOfTruth(List<FuzzySet<R>> qualifiers, List<FuzzySet<R>> summarizers) {
        Operation<FuzzySet<R>> operation = new Operation<>();
        FuzzySet<R> s = summarizers.get(0);
        for (FuzzySet<R> qualifier : summarizers) {
            s = operation.and(s, qualifier);
        }
        if (!qualifiers.isEmpty()) {
            FuzzySet<R> q = qualifiers.get(0);
            for (FuzzySet<R> qualifier : qualifiers) {
                q = operation.and(q, qualifier);
            }
            s = operation.and(q, s);
        }

        if (quantifier.isAbsolute()) { // 0 - M
            return quantifier.getMemberShip(policies.stream().mapToDouble(s::getMemberShip).sum());
        } else { // 0 - 1
            return quantifier.getMemberShip(policies.stream().mapToDouble(s::getMemberShip).sum()
                    / policies.size());
        }
    }

    /**
     * T2
     */
    private double degreeOfImprecision(List<FuzzySet<R>> summarizers) {
        return 1.0 - Math.pow(summarizers.stream()
                .mapToDouble(x -> x.degreeOfFuzziness(policies))
                .reduce(1.0, (a, b) -> a * b),
                1.0 / summarizers.size());
    }

    /**
     * T3
     */
    private double degreeOfCovering(List<FuzzySet<R>> qualifiers, List<FuzzySet<R>> summarizers) {
        Operation<FuzzySet<R>> operation = new Operation<>();
        FuzzySet<R> s = summarizers.get(0);
        for (FuzzySet<R> sum : summarizers) {
            s = operation.and(s, sum);
        }
        if (qualifiers.isEmpty()) {
            return 1.0 * s.support(policies).size() / policies.size();
        }

        FuzzySet<R> q = qualifiers.get(0);
        for (FuzzySet<R> qualifier : qualifiers) {
            q = operation.and(q, qualifier);
        }
        FuzzySet<R> t = operation.and(q, s);

        return 1.0 * t.support(policies).size() / q.support(policies).size();
    }

    /**
     * T4
     */
    private double degreeOfAppropriateness(List<FuzzySet<R>> qualifiers, List<FuzzySet<R>> summarizers) {
        return Math.abs(summarizers.stream().mapToDouble(x -> 1.0 * x.support(policies).size() / policies.size())
                .reduce(1.0, (a, b) -> a * b) - degreeOfCovering(qualifiers, summarizers));
    }

    /**
     * T5
     */
    private double lengthOfSummary(int size) {
        return 2.0 * Math.pow(0.5, size);
    }

    /**
     * T6
     */
    private double degreeOfQuantifierImprecision() {
        if (!quantifier.isAbsolute()) {
            return 1.0 - quantifier.support() / quantifier.getDomain().width();
        }
        return 1.0 - quantifier.support() / policies.size();
    }

    /**
     * T7
     */
    private double degreeOfQuantifierCardinality() {
        if (!quantifier.isAbsolute()) {
            return 1.0 - quantifier.cardinality() / quantifier.getDomain().width();
        }
        return 1.0 - quantifier.cardinality() / policies.size();
    }

    /**
     * T8
     */
    private double degreeOfSummarizerCardinality(List<FuzzySet<R>> summarizer) {
        return 1.0 - Math.pow(summarizer.stream()
                .mapToDouble(x -> x.cardinality(policies) / policies.size())
                .reduce(1.0, (a, b) -> a * b), 1.0 / summarizer.size());
    }

    /**
     * T9
     */
    private double degreeOfQualifierImprecision(List<FuzzySet<R>> qualifier) {
        if (qualifier.isEmpty()) {
            return 0.0;
        }
        return 1.0 - Math.pow(qualifier.stream()
                .mapToDouble(x -> x.degreeOfFuzziness(policies))
                .reduce(1.0, (a, b) -> a * b) , 1.0 / qualifier.size());
    }

    /**
     * T10
     */
    private double degreeOfQualifierCardinality(List<FuzzySet<R>> qualifier) {
        if (qualifier.isEmpty()) {
            return 0.0;
        }
        return 1.0 - Math.pow(qualifier.stream()
                .mapToDouble(x -> x.cardinality(policies) / policies.size())
                .reduce(1.0, (a, b) -> a * b), 1.0 / qualifier.size());
    }

    /**
     * T11
     */
    private double lengthOfQualifier(int size) {
        if (size == 0) {
            return 2.0 * Math.pow(0.5, 1);
        }
        return 2.0 * Math.pow(0.5, size);
    }

    private double checkNan(double x) {
        if (Double.isNaN(x)) {
            return 0.0;
        }
        return x;
    }
}
