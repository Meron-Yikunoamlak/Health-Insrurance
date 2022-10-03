package et.com.saron.health_insurance.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "insured_employees")
@Entity
@ToString
@RequiredArgsConstructor
@Data
public class InsuredEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "additional_information")
    private String additionalInformation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "health_service_providers")
    private HealthServiceProvider healthServiceProvider;

    @Column(name = "card_number", nullable = false, length = 200)
    private String cardNumber;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "card_number_given_on")
    private LocalDateTime cardNumberGivenOn;

    @Column(name = "registered_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredOn;

    @Column(name = "lastUpdatedOn", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdatedOn;
}