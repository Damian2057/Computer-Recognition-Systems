package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.util.Combiner;
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
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
                summaries.add(new Summary(form, stringBuilder.toString(),
                        getQualityForSummary()));
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
            for (int i : combination.getFirst()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getFirst().size()) {
                    stringBuilder.append(AND);
                }
            }
            stringBuilder.append(ALSO);
            index = 0;
            for (int i : combination.getSecond()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getSecond().size()) {
                    stringBuilder.append(AND);
                }
            }

            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary()));
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
            for (int i : combination.getFirst()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getFirst().size()) {
                    stringBuilder.append(AND);
                }
            }
            stringBuilder.append(COMPARED).append(subject2).append(ALSO);
            index = 0;
            for (int i : combination.getSecond()) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.getSecond().size()) {
                    stringBuilder.append(AND);
                }
            }

            summaries.add(new Summary(form, stringBuilder.toString(),
                    getQualityForSummary()));
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
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
                summaries.add(new Summary(form, stringBuilder.toString(),
                        getQualityForSummary()));
            }
        }
    }

    private List<Double> getQualityForSummary() {
        return Collections.emptyList();
    }

    private double degreeOfTruth(int form) {
        return 0.0;
    }
}
