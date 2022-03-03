package com.volvo.model;

import com.volvo.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public enum VehicleType {
    CAR("CAR"),
    MOTORBIKE("MOTORBIKE"),
    MOTORCYCLES("MOTORCYCLES"),
    BUSSES("BUSSES"),
    EMERGENCY("EMERGENCY"),
    DIPLOMAT("DIPLOMAT"),
    MILITARY("MILITARY"),
    FOREIGN("FOREIGN");

    private static final Map<String, VehicleType> VEHICLE_TYPES = new HashMap<>();

    static {
        for (VehicleType vehicleType : values()) {
            VEHICLE_TYPES.put(vehicleType.getType(), vehicleType);
        }
    }

    VehicleType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public static VehicleType getVehicleType(String type) {
        VehicleType result = null;
        if (StringUtil.isNotEmpty(type)) {
            result = VEHICLE_TYPES.get(type.toUpperCase());
        }

        return result;
    }
}
