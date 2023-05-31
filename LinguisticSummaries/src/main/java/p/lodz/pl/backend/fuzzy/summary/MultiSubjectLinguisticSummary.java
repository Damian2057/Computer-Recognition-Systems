package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.ArrayList;
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
    }

    private void generateSecondForm() {
        final int form = 2;
    }

    private void generateThirdForm() {
        final int form = 3;
    }

    private void generateFourthForm() {
        final int form = 4;
    }

    private double degreeOfTruth(int form) {
        return 0.0;
    }
}
