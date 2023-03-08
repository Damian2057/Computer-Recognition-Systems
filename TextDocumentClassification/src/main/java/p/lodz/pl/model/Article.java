package p.lodz.pl.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Article {
    private final List<String> topics;
    private final List<String> peoples;
    private final String place;
    private final String title;
    private final String body;
}
