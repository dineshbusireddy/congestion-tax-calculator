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

@Service
public class CongestionTaxCalculatorService {

    @Autowired
    private TaxExemptChecker taxExemptChecker;

    @Autowired
    private TaxProcessor taxProcessor;

    @Value("${currency}")
    private String currency;

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
