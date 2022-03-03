package com.volvo.validators;

import com.volvo.model.VehicleType;
import com.volvo.util.ObjectUtil;
import com.volvo.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class VehicleTypeValidator implements ConstraintValidator<VehicleTypeChecker, String> {

    @Override
    public boolean isValid(String vehicleType, ConstraintValidatorContext context) {
        boolean valid = true;
        if (StringUtil.isNotEmpty(vehicleType)) {
            valid = ObjectUtil.isNotNull(VehicleType.getVehicleType(vehicleType));
        }

        return valid;
    }
}
