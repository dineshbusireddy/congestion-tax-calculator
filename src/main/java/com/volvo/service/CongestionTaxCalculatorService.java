package com.volvo.service;

import com.volvo.checkers.TaxExemptChecker;
import com.volvo.model.TaxInfo;
import com.volvo.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculates the tax by mapping dates in day wise.
 *
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Service
public class CongestionTaxCalculatorService {

    @Autowired
    private TaxExemptChecker taxExemptChecker;

    @Autowired
    private TaxProcessor taxProcessor;

    @Value("${currency}")
    private String currency;

    /**
     * Calculates the tax by sorting provided dates and mapping day wise
     * if the provided vehicle is not Tax exempt vehicle.
     *
     * @param vehicleType the {@link VehicleType}
     * @param dates       the list {@link LocalDateTime} the vehicle passes in tolls
     * @return the calculated {@link TaxInfo}
     */
    public TaxInfo calculateTax(VehicleType vehicleType, List<LocalDateTime> dates) {
        BigDecimal totalFee = BigDecimal.ZERO;
        if (!taxExemptChecker.isTaxFreeVehicle(vehicleType)) {
            Collections.sort(dates);
            Map<LocalDate, List<LocalDateTime>> datesByDay = mapDatesByDay(dates);

            for (Map.Entry<LocalDate, List<LocalDateTime>> datesByDayEntry :
                    datesByDay.entrySet()) {
                totalFee = totalFee.add(taxProcessor.
                        calculateTaxForSingleDay(datesByDayEntry.getValue()));
            }
        }

        return new TaxInfo(totalFee, currency);
    }

    /**
     * Maps the provided dates to day wise
     *
     * @param dates the list {@link LocalDateTime} the vehicle passes in tolls
     * @return the mapped details
     */
    private Map<LocalDate, List<LocalDateTime>> mapDatesByDay(List<LocalDateTime> dates) {
        Map<LocalDate, List<LocalDateTime>> datesByDay = new HashMap<>();
        for (LocalDateTime date : dates) {
            if (!datesByDay.containsKey(date.toLocalDate())) {
                datesByDay.put(date.toLocalDate(), new ArrayList<>());
            }
            datesByDay.get(date.toLocalDate()).add(date);
        }

        return datesByDay;
    }

}
