package com.volvo.controllor;

import com.volvo.converters.TaxInfoToTaxCalculatorResponseConverter;
import com.volvo.dto.TaxCalculatorRequest;
import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.model.TaxInfo;
import com.volvo.model.VehicleType;
import com.volvo.service.CongestionTaxCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/congestion-tax")
@Tag(name = "Congestion Tax Calculator", description = "API to calculate congestion tax")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "200", description = "Success")
})
public class CongestionTaxController {

    @Autowired
    private CongestionTaxCalculatorService congestionTaxCalculatorService;

    @Autowired
    private TaxInfoToTaxCalculatorResponseConverter responseConverter;

    @PostMapping(value = "/calculator")
    @Operation(summary = "Calculate congestion Tax",
            description = "This method to calculate congestion tax")
    @ResponseStatus(HttpStatus.OK)
    public TaxCalculatorResponse calculateTax(
            @RequestBody @Valid TaxCalculatorRequest taxCalculatorRequest) {
        TaxInfo taxInfo = congestionTaxCalculatorService.calculateTax(
                VehicleType.getVehicleType(taxCalculatorRequest.getVehicleType()),
                taxCalculatorRequest.getDates());

        return responseConverter.convert(taxInfo);
    }
}
