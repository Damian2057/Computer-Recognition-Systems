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

public class MultiSubjectLinguisticSummary<R> extends AbstractLinguisticSummary {

    private final List<LinguisticLabel<R>> qualifiers;
    private final String subject1;
    private final String subject2;
    private final List<R> firstGroup;
    private final List<R> secondGroup;

    private List<Summary> summaries;

    public MultiSubjectLinguisticSummary(Quantifier quantifier,
                                         List<LinguisticLabel<R>> qualifiers,
                                         String subject1,
                                         String subject2,
                                         List<R> firstGroup,
                                         List<R> secondGroup) {
        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.firstGroup = firstGroup;
        this.secondGroup = secondGroup;
    }

    @Override
    public List<Summary> generateSummary() {
        this.summaries = new ArrayList<>();
        generateFirstForm();
        generateSecondForm();
        generateThirdForm();
        generateFourthForm();

        return summaries;
    }

    private void generateFirstForm() {
        final int form = 1;
        List<List<Integer>> combinations = Combiner
                .getFirstFormCombinations(1, qualifiers.size());

        for (List<Integer> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject1).append(COMPARED).append(subject2).append(HAVE);
            int index = 0;
            List<FuzzySet<R>> sum = new ArrayList<>();
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
                sum.add(qualifiers.get(i));
                summaries.add(new Summary(form, stringBuilder.toString(),
                        getQualityForSummary(form, Collections.emptyList(), sum)));
            }
        }
    }

    private void generateSecondForm() {
        final int form = 2;
        List<Pair<List<Integer>, List<Integer>>> combinations = Combiner
                .getSecondFormCombinations(qualifiers.size());

        for (Pair<List<Integer>, List<Integer>> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject1).append(COMPARED).append(subject2).append(THAT);
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

            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary(form, qua, sum)));
        }
    }

    private void generateThirdForm() {
        final int form = 3;
        List<Pair<List<Integer>, List<Integer>>> combinations = Combiner
                .getSecondFormCombinations(qualifiers.size());

        for (Pair<List<Integer>, List<Integer>> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject1).append(THAT);
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
            stringBuilder.append(COMPARED).append(subject2).append(ALSO);
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

            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary(form, qua, sum)));
        }
    }

    private void generateFourthForm() {
        final int form = 4;
        List<List<Integer>> combinations = Combiner
                .getFirstFormCombinations(1, qualifiers.size());

        for (List<Integer> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(THERE).append(subject1)
                    .append(THAN).append(subject2).append(THAT);
            int index = 0;
            List<FuzzySet<R>> sum = new ArrayList<>();
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
                sum.add(qualifiers.get(i));
            }
            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary(form, Collections.emptyList(), sum)));
        }
    }

    private List<Double> getQualityForSummary(int form,
                                              List<FuzzySet<R>> qualifierSet,
                                              List<FuzzySet<R>> summarizerSet) {
        if (form == 1) {
            return List.of(degreeOfTruthForFirstForm(summarizerSet));
        } else if (form == 2) {
            return List.of(degreeOfTruthForSecondForm(qualifierSet, summarizerSet));
        } else if (form == 3) {
            return List.of(degreeOfTruthForThirdForm(qualifierSet, summarizerSet));
        } else if (form == 4) {
            return List.of(degreeOfTruthForFourthForm(summarizerSet));
        } else {
            throw new IllegalArgumentException("Form " + form + " is not supported");
        }
    }

    private double degreeOfTruthForFirstForm(List<FuzzySet<R>> summarizers) {
        Operation<FuzzySet<R>> operation = new Operation<>();
        FuzzySet<R> s = summarizers.get(0);
        for (FuzzySet<R> qualifier : summarizers) {
            s = operation.and(s, qualifier);
        }
        double nominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(s::getMemberShip)
                .sum();
        double denominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(s::getMemberShip)
                .sum() +
                1.0 / secondGroup.size() * secondGroup.stream()
                .mapToDouble(s::getMemberShip)
                .sum();
        return quantifier.getMemberShip(nominator / denominator);
    }

    private double degreeOfTruthForSecondForm(List<FuzzySet<R>> qualifiers,
                                              List<FuzzySet<R>> summarizers) {
        Operation<FuzzySet<R>> operation = new Operation<>();
        FuzzySet<R> s = summarizers.get(0);
        for (FuzzySet<R> qualifier : summarizers) {
            s = operation.and(s, qualifier);
        }
        FuzzySet<R> q = summarizers.get(0);
        for (FuzzySet<R> qualifier : qualifiers) {
            q = operation.and(q, qualifier);
        }
        double nominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(s::getMemberShip)
                .sum();
        double denominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(s::getMemberShip)
                .sum() +
                1.0 / secondGroup.size() * secondGroup.stream().mapToDouble(q::getMemberShip).sum();
        return quantifier.getMemberShip(nominator / denominator);
    }

    private double degreeOfTruthForThirdForm(List<FuzzySet<R>> qualifiers,
                                             List<FuzzySet<R>> summarizers) {
        Operation<FuzzySet<R>> operation = new Operation<>();
        FuzzySet<R> s = summarizers.get(0);
        for (FuzzySet<R> qualifier : summarizers) {
            s = operation.and(s, qualifier);
        }
        FuzzySet<R> q = summarizers.get(0);
        for (FuzzySet<R> qualifier : qualifiers) {
            q = operation.and(q, qualifier);
        }
        double nominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(q::getMemberShip)
                .sum();
        double denominator = 1.0 / firstGroup.size() * firstGroup.stream()
                .mapToDouble(q::getMemberShip)
                .sum() +
                1.0 / secondGroup.size() * secondGroup.stream().mapToDouble(q::getMemberShip).sum();
        return quantifier.getMemberShip(nominator / denominator);
    }

    private double degreeOfTruthForFourthForm(List<FuzzySet<R>> summarizerSet) {
        return 0.0;
    }
}
