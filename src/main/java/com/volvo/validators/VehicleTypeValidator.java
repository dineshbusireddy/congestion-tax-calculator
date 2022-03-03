package com.volvo.validators;

import com.volvo.model.VehicleType;
import com.volvo.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VehicleTypeValidator implements ConstraintValidator<VehicleTypeChecker, String> {

    @Override
    public boolean isValid(String vehicleType, ConstraintValidatorContext context) {
        boolean valid = true;
        if (StringUtil.isNotEmpty(vehicleType)) {
            valid = VehicleType.getVehicleType(vehicleType) != null;
        }
        return valid;
    }
}
