package p.lodz.pl.backend.fuzzy.summary;

import lombok.Builder;

@Builder
public record Summary(
        int form,
        String summary,
        double quality
) {
}
