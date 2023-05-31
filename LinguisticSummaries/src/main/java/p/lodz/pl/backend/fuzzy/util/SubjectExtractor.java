package p.lodz.pl.backend.fuzzy.util;

import p.lodz.pl.backend.model.PolicyEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubjectExtractor {

    public static Pair<List<PolicyEntity>, List<PolicyEntity>> extract(
            List<PolicyEntity> entities,
            Predicate<PolicyEntity> predicate) {
        List<PolicyEntity> firstGroup = entities
                .stream()
                .filter(predicate).toList();
        List<PolicyEntity> secondGroup = new ArrayList<>(entities);
        secondGroup.removeAll(firstGroup);

        return new Pair<>(firstGroup, secondGroup);
    }
}
