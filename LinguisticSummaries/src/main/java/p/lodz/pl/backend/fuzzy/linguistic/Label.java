package p.lodz.pl.backend.fuzzy.linguistic;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
import p.lodz.pl.backend.fuzzy.util.Extractor;

public class Label<R> extends FuzzySet<R> {

    private final String linguisticVariableName;
    private final String labelName;

    public Label(String linguisticVariableName, String labelName, Extractor<R> extractor, MembershipFunction function) {
        super(extractor, function);
        this.linguisticVariableName = linguisticVariableName;
        this.labelName = labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getLinguisticVariableName() {
        return linguisticVariableName;
    }
}
