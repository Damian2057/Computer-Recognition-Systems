package p.lodz.pl.dao;

import java.util.List;

public interface ArticleLoader<T> {
    List<T> read();
}
