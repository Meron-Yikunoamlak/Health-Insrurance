package et.com.saron.health_insurance.repository;

import et.com.saron.health_insurance.model.HealthServiceProvider;
import et.com.saron.health_insurance.model.InsuredEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsuredEmployeeRepository extends JpaRepository<InsuredEmployee, Long> {

    List<InsuredEmployee> findByHealthServiceProvider (HealthServiceProvider healthServiceProvider);
    Optional<InsuredEmployee> findByCardNumberAndFullName (String cardNumber, String fullName);
}