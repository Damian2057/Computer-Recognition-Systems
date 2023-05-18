package p.lodz.pl.linguisticsummaries.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import p.lodz.pl.linguisticsummaries.backend.repository.PolicyRepository;

@Component
public class PolicyService {

    @Autowired
    private PolicyRepository repository;

    public void Test() {
        System.out.println(repository.findAll());
    }
}
