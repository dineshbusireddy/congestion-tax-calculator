package com.volvo.checkers;

import com.volvo.model.VehicleType;

import java.time.LocalDateTime;

/**
 * To check whether the provided vehicle or date tax free or not
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public interface TaxExemptChecker {

    /**
     * Checks whether provided vehicle is tax free or not
     *
     * @param vehicleType the {@link VehicleType}
     * @return true if provided vehicle is tax exempt else false
     */
    boolean isTaxFreeVehicle(VehicleType vehicleType);

    /**
     * Checks whether provided date is tax free or not
     *
     * @param date the {@link LocalDateTime}
     * @return true if provided date is tax exempt else false
     */
    boolean isTaxFreeDay(LocalDateTime date);

}
