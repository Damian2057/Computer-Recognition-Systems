package p.lodz.pl.linguisticsummaries.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Policy {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
