package p.lodz.pl.dao;

import java.io.FileNotFoundException;
import java.util.List;

public interface Loader<T> {
    List<T> read();
}
