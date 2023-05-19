package p.lodz.pl.backend.fuzzy.linguistic;

import java.util.List;

public class LinguisticVariable<R> {

    private final String name;
    protected final List<Label<R>> labels;

    public LinguisticVariable(String name, List<Label<R>> labels) {
        this.labels = labels;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Label<R>> getLabels() {
        return labels;
    }
}