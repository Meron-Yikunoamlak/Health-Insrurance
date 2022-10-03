package et.com.saron.health_insurance.controller;


import et.com.saron.health_insurance.dto.HealthServiceProviderRequestDto;
import et.com.saron.health_insurance.dto.ResponseDto;
import et.com.saron.health_insurance.model.HealthServiceProvider;
import et.com.saron.health_insurance.service.HealthServiceProviderService;
import et.com.saron.health_insurance.validationHandler.Validation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/healthServiceProvider")
@Tag(name = "Health Service Provider")
public class HealthServiceProviderController {

    private final HealthServiceProviderService healthServiceProviderService;
    private final Validation validation;

    @Autowired
    public HealthServiceProviderController(HealthServiceProviderService healthServiceProviderService, Validation validation) {
        this.healthServiceProviderService = healthServiceProviderService;
        this.validation = validation;
    }

    @PostMapping("/")
    @Operation(summary = "All fields are required")
    public ResponseEntity<ResponseDto> create(@RequestBody HealthServiceProviderRequestDto healthServiceProviderRequestDto) {
        Set<ConstraintViolation<HealthServiceProviderRequestDto>> violations = validation.getConstraintViolations(healthServiceProviderRequestDto);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, new ConstraintViolationException(violations).getMessage(),null));
        }
        return healthServiceProviderService.create(healthServiceProviderRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "only the fields that needs to be updated, should be sent.")
    public ResponseEntity<ResponseDto> update(@PathVariable("id") Long id,
                                              @RequestBody HealthServiceProviderRequestDto healthServiceProviderRequestDto) {

        Set<ConstraintViolation<HealthServiceProviderRequestDto>> violations = validation.getConstraintViolations(healthServiceProviderRequestDto);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, new ConstraintViolationException(violations).getMessage(),null));
         }
        return healthServiceProviderService.update(id, healthServiceProviderRequestDto);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "this will remove the provider from db")
    public ResponseEntity<ResponseDto> delete(@PathVariable("id") Long id) {
        return healthServiceProviderService.delete(id);
    }


    @GetMapping("/all")
    @Operation(summary = "sorting orders must be sent as 'asc' and 'desc'. and sorting parameters" +
            "are HealthServiceProvider model field names")
    public ResponseEntity<Page<HealthServiceProvider>> getAllLessorReports(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size",defaultValue = "10") int size,
                                                                           @RequestParam(value = "sortingParameter",defaultValue = "id") String param,
                                                                           @RequestParam(value = "sortingOrder",defaultValue = "asc") String order) {
        return healthServiceProviderService.getAll(page, size, param, order);
    }


}
