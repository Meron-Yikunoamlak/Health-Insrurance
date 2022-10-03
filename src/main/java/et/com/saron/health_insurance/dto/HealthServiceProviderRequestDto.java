package et.com.saron.health_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class HealthServiceProviderRequestDto {


    @NotBlank(message = "name field can not be blank")
    @NotEmpty(message = "name field can not contain empty string")
    @NotNull(message = "name field can not be null")
    private String name;

    @NotBlank(message = "address field can not be blank")
    @NotEmpty(message = "address field can not contain empty string")
    @NotNull(message = "address field can not be null")
    private String address;

    private String additionalInformation;
}
