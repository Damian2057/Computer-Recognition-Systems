package p.lodz.pl.logic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import p.lodz.pl.logic.model.Policy;
import p.lodz.pl.logic.repository.PolicyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository repository;

    public List<Policy> getAll() {
        return repository.findAll();
    }
}
