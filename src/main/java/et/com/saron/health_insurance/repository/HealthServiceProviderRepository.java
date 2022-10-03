package et.com.saron.health_insurance.repository;

import et.com.saron.health_insurance.model.HealthServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthServiceProviderRepository extends JpaRepository<HealthServiceProvider, Long> {

    Optional<HealthServiceProvider> findByName(String name);
}