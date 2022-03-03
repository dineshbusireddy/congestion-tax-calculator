package com.volvo.checkers;

import com.volvo.model.VehicleType;

import java.time.LocalDateTime;

public interface TaxExemptChecker {

    boolean isTaxFreeVehicle(VehicleType vehicleType);

    boolean isTaxFreeDay(LocalDateTime date);

}
