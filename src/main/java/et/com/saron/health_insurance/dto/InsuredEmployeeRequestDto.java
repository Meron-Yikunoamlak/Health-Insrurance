package et.com.saron.health_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class InsuredEmployeeRequestDto {


    @NotBlank(message = "treatment field can not be blank")
    @NotEmpty(message = "treatment field can not contain empty string")
    @NotNull(message = "treatment field can not be null")
    private String treatment;

    private String additionalInformation;

}
