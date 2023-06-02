package p.lodz.pl.backend.repository;

import p.lodz.pl.backend.model.PolicyEntity;

import java.util.List;

public interface Dao {
    List<PolicyEntity> getPolicies();

    void savePolicies(List<PolicyEntity> policies);
}
