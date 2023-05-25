package p.lodz.pl.backend.fuzzy.util;

public class Pair<T, R> {
    private final T first;
    private final R second;

    public Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    public T getQualifiers() {
        return first;
    }

    public R getSummarizers() {
        return second;
    }
}
