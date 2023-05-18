package p.lodz.pl.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Policy() {
    private static String id;

    public void setId(String id) {
        Policy.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
