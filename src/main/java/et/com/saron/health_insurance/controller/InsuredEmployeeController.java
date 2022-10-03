package et.com.saron.health_insurance.controller;


import et.com.saron.health_insurance.dto.*;
import et.com.saron.health_insurance.model.HealthServiceProvider;
import et.com.saron.health_insurance.model.InsuredEmployee;
import et.com.saron.health_insurance.service.HealthServiceProviderService;
import et.com.saron.health_insurance.service.InsuredEmployeeService;
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
@RequestMapping("/insuredEmployee")
@Tag(name = "Insured Employee")
public class InsuredEmployeeController {

    private final InsuredEmployeeService insuredEmployeeService;
    private final Validation validation;

    @Autowired
    public InsuredEmployeeController(InsuredEmployeeService insuredEmployeeService, Validation validation) {
        this.insuredEmployeeService = insuredEmployeeService;
        this.validation = validation;
    }


    @PostMapping("/createCard")
    @Operation(summary = "All fields are required")
    public ResponseEntity<ResponseDto> create(@RequestBody CardNumberRequestDto cardNumberRequestDto) {
        Set<ConstraintViolation<CardNumberRequestDto>> violations = validation.getConstraintViolations(cardNumberRequestDto);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, new ConstraintViolationException(violations).getMessage(),null));
        }
        return insuredEmployeeService.createCard(cardNumberRequestDto);
    }


    @PostMapping("/createDiagnose")
    @Operation(summary = "All fields are required")
    public ResponseEntity<ResponseDto> create(@RequestParam("id") Long id,
                                              @RequestBody InsuredEmployeeRequestDto insuredEmployeeRequestDto) {
        Set<ConstraintViolation<InsuredEmployeeRequestDto>> violations = validation.getConstraintViolations(insuredEmployeeRequestDto);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, new ConstraintViolationException(violations).getMessage(),null));
        }
        return insuredEmployeeService.createDiagnose(id,insuredEmployeeRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "only the fields that needs to be updated, should be sent.")
    public ResponseEntity<ResponseDto> update(@PathVariable("id") Long id,
                                              @RequestBody InsuredEmployeeUpdateRequestDto insuredEmployeeUpdateRequestDto) {

        Set<ConstraintViolation<InsuredEmployeeUpdateRequestDto>> violations = validation.getConstraintViolations(insuredEmployeeUpdateRequestDto);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, new ConstraintViolationException(violations).getMessage(),null));
        }
        return insuredEmployeeService.update(id, insuredEmployeeUpdateRequestDto);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "this will remove the insured employee from db")
    public ResponseEntity<ResponseDto> delete(@PathVariable("id") Long id) {
        return insuredEmployeeService.delete(id);
    }


    @GetMapping("/all")
    @Operation(summary = "sorting orders must be sent as 'asc' and 'desc'. and sorting parameters" +
            "are InsuredEmployee model field names. and the first page is page zero")
    public ResponseEntity<Page<InsuredEmployee>> getAllLessorReports(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam("sortingParameter") String param,
                                                                     @RequestParam("sortingOrder") String order) {
        return insuredEmployeeService.getAll(page, size, param, order);
    }


}
