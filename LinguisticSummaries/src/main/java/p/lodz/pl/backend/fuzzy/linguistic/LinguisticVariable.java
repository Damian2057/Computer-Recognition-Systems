package p.lodz.pl.backend.fuzzy.linguistic;

import java.util.List;

public class LinguisticVariable<R> {

    private final String linguisticVariableName;
    protected final List<Label<R>> labels;

    public LinguisticVariable(String name, List<Label<R>> labels) {
        this.labels = labels;
        this.linguisticVariableName = name;
    }

    public String getLinguisticVariableName() {
        return linguisticVariableName;
    }

    public List<Label<R>> getLabels() {
        return labels;
    }
}