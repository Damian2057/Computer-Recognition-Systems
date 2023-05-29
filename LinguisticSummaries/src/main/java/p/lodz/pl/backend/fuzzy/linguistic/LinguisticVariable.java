package p.lodz.pl.backend.fuzzy.linguistic;

import java.util.List;

public class LinguisticVariable<R> {

    private final String linguisticVariableName;
    protected final List<LinguisticLabel<R>> labels;

    public LinguisticVariable(String name, List<LinguisticLabel<R>> labels) {
        this.labels = labels;
        this.linguisticVariableName = name;
    }

    public String getLinguisticVariableName() {
        return linguisticVariableName;
    }

    public List<LinguisticLabel<R>> getLabels() {
        return labels;
    }

    public void addLabel(LinguisticLabel<R> label) {
        labels.add(label);
    }

    public void deleteLabel(LinguisticLabel<R> label) {
        labels.remove(label);
    }
}