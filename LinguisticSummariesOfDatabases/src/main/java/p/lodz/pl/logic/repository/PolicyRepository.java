package p.lodz.pl.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.logic.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
}
