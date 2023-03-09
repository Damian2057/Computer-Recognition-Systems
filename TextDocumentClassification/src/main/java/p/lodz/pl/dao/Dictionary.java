package p.lodz.pl.dao;

import p.lodz.pl.constants.Const;

import java.util.List;

public interface Dictionary {
    List<String> read(Const path);
}
