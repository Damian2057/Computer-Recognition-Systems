package p.lodz.pl.backend.fuzzy.summary;

import lombok.Builder;

import java.util.List;

@Builder
public record Summary(
        int form,
        String summary,
        List<Double> quality
) {
}
