package p.lodz.pl.config;

import lombok.Data;

@Data
public class TestMode {
    private final boolean enable;
    private final int numberOfArticles;
}
