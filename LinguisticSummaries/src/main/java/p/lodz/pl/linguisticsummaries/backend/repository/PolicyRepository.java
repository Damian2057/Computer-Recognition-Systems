package p.lodz.pl.linguisticsummaries.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.linguisticsummaries.backend.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
}
