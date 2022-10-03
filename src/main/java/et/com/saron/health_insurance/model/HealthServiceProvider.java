package et.com.saron.health_insurance.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "health_service_providers")
@Entity
@ToString
@RequiredArgsConstructor
@Data
public class HealthServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "full_address", nullable = false, length = 255)
    private String fullAddress;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "registered_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredOn;

    @Column(name = "lastUpdatedOn", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdatedOn;

}