package et.com.saron.health_insurance.service;

import et.com.saron.health_insurance.dto.CardNumberRequestDto;
import et.com.saron.health_insurance.dto.InsuredEmployeeRequestDto;
import et.com.saron.health_insurance.dto.InsuredEmployeeUpdateRequestDto;
import et.com.saron.health_insurance.dto.ResponseDto;
import et.com.saron.health_insurance.model.HealthServiceProvider;
import et.com.saron.health_insurance.model.InsuredEmployee;
import et.com.saron.health_insurance.repository.HealthServiceProviderRepository;
import et.com.saron.health_insurance.repository.InsuredEmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InsuredEmployeeService {

    private final HealthServiceProviderRepository healthServiceProviderRepository;
    private final InsuredEmployeeRepository insuredEmployeeRepository;
    private final UtilityService utilityService;


    public ResponseEntity<ResponseDto> createCard(CardNumberRequestDto cardNumberRequestDto) {
        Optional<InsuredEmployee> insuredEmployeeOptional = insuredEmployeeRepository
                .findByCardNumberAndFullName(cardNumberRequestDto.getCardNumber(), cardNumberRequestDto.getFullName());
        if (insuredEmployeeOptional.isPresent()) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "card number already registered for this employee", null));
        }
        Optional<HealthServiceProvider> healthServiceProviderOptional = healthServiceProviderRepository.findById(cardNumberRequestDto.getProviderId());
        if (healthServiceProviderOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "provider id -> " + cardNumberRequestDto.getProviderId() + " not found");


        InsuredEmployee insuredEmployee = new InsuredEmployee();
        insuredEmployee.setFullName(cardNumberRequestDto.getFullName());
        insuredEmployee.setCardNumber(cardNumberRequestDto.getCardNumber());
        insuredEmployee.setHealthServiceProvider(healthServiceProviderOptional.get());
        insuredEmployee.setCardNumberGivenOn(LocalDateTime.now());
        insuredEmployeeRepository.save(insuredEmployee);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "registered successfully", insuredEmployee));
    }


    public ResponseEntity<ResponseDto> createDiagnose(Long insuredEmployeeId, InsuredEmployeeRequestDto insuredEmployeeRequestDto) {
        Optional<InsuredEmployee> insuredEmployee = insuredEmployeeRepository.findById(insuredEmployeeId);
        if (insuredEmployee.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id -> " + insuredEmployee + " not found");


        InsuredEmployee insured = insuredEmployee.get();
        insured.setAdditionalInformation(insuredEmployeeRequestDto.getAdditionalInformation());
        insured.setTreatment(insuredEmployeeRequestDto.getTreatment());
        insuredEmployeeRepository.save(insured);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, "registered successfully", insured));
    }


    public ResponseEntity<ResponseDto> update(Long id, InsuredEmployeeUpdateRequestDto insuredEmployeeUpdateRequestDto) {
        var ref = new Object() {
            ResponseEntity<ResponseDto> responseDto;
        };
        Optional<InsuredEmployee> insuredEmployeeRepositoryById = insuredEmployeeRepository.findById(id);
        try {
            insuredEmployeeRepositoryById.ifPresentOrElse(
                    (value) -> {
                        if (insuredEmployeeUpdateRequestDto.getFullName() != null)
                            value.setFullName(insuredEmployeeUpdateRequestDto.getFullName());
                        if (insuredEmployeeUpdateRequestDto.getCardNumber() != null)
                            value.setCardNumber(insuredEmployeeUpdateRequestDto.getCardNumber());
                        if (insuredEmployeeUpdateRequestDto.getAdditionalInformation() != null)
                            value.setAdditionalInformation(insuredEmployeeUpdateRequestDto.getAdditionalInformation());
                        if (insuredEmployeeUpdateRequestDto.getTreatment() != null)
                            value.setTreatment(insuredEmployeeUpdateRequestDto.getTreatment());
                        insuredEmployeeRepository.save(value);
                        ref.responseDto = ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, "updated successfully", value));
                    }, () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id -> " + id + " not found");
                    });

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ref.responseDto;
    }


    public ResponseEntity<ResponseDto> delete(Long id) {
        var ref = new Object() {
            ResponseEntity<ResponseDto> responseDto;
        };
        Optional<InsuredEmployee> insuredEmployee = insuredEmployeeRepository.findById(id);
        insuredEmployee.ifPresentOrElse(
                (value) -> {
                    insuredEmployeeRepository.delete(value);
                    ref.responseDto = ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, "deleted successfully", null));
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id -> " + id + " not found");
                });
        return ref.responseDto;
    }


    public ResponseEntity<Page<InsuredEmployee>> getAll(int page, int size, String param, String order) {
        return ResponseEntity.ok(insuredEmployeeRepository.findAll(utilityService.pageable(page, size, order, param)));
    }

}
