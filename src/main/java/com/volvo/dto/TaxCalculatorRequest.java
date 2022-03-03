package com.volvo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.volvo.validators.VehicleTypeChecker;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaxCalculatorRequest {

    @NotNull(message = "{tax.calculator.vehicleType.required}")
    @NotEmpty(message = "{tax.calculator.vehicleType.required}")
    @VehicleTypeChecker(message = "{tax.calculator.vehicleType.invalid}")
    private String vehicleType;

    @NotNull(message = "{tax.calculator.dates.required}")
    @JsonDeserialize(using = DateDeserializer.class)
    private List<@Valid LocalDateTime> dates;

}
