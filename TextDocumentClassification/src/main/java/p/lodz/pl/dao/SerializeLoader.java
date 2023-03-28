package p.lodz.pl.dao;

public interface SerializeLoader<T> {
    T read();
    void write(T obj);
    boolean isFileExist();
}
