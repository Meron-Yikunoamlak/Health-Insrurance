package et.com.saron.health_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class CardNumberRequestDto {


    @NotNull(message = "providerId field can not be null")
    @PositiveOrZero(message = "providerId value should be non negative")
    @Min(message = "minimum id value is 1", value = 1)
    private Long providerId;

    @NotBlank(message = "fullName field can not be blank")
    @NotEmpty(message = "fullName field can not contain empty string")
    @NotNull(message = "fullName field can not be null")
    private String fullName;

    @NotBlank(message = "cardNumber field can not be blank")
    @NotEmpty(message = "cardNumber field can not contain empty string")
    @NotNull(message = "cardNumber field can not be null")
    private String cardNumber;
}
