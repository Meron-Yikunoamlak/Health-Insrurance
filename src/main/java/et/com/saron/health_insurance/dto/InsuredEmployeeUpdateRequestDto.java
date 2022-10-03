package et.com.saron.health_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class InsuredEmployeeUpdateRequestDto {

    private String fullName;
    private String cardNumber;
    private String treatment;
    private String additionalInformation;
}
