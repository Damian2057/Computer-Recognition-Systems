package p.lodz.pl.dao;

import p.lodz.pl.constants.Const;

import java.util.List;

public interface DictionaryLoader<T> {
    List<T> read(Const path);
}
