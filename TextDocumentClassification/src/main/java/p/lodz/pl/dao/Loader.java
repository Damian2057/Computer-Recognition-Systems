package p.lodz.pl.dao;

import java.util.List;

public interface Loader<T> {
    List<T> read();
}
