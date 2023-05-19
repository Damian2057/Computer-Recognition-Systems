package p.lodz.pl.backend.service;

import p.lodz.pl.backend.model.PolicyEntity;

import java.util.List;

public interface Connection {
    List<PolicyEntity> getPolicies();
}
