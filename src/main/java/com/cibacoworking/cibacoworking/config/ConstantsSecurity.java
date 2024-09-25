package com.cibacoworking.cibacoworking.config;

public class ConstantsSecurity {
    //ENDPOINTS

    //front-end part
    public static final String LOCALHOST_FRONT_URL = "http://localhost:5173";

    //User reservations
    public static final String GET_RESERVATIONS_BY_USER = "/api/reservations/user/{userId}";
    public static final String CREATE_RESERVATION_BY_USER = "/api/reservations/user/create";

    //Admin reservations
    public static final String CREATE_RESERVATION_ADMIN = "/api/reservations/admin/create";
    public static final String CREATE_LONG_TERM_RESERVATION_ADMIN = "/api/reservations/admin/create/longterm";
    
    // Espacios (spaces)
     public static final String GET_RESERVATIONS_BY_SPACE_AND_DATE = "/api/spaces/{spaceId}/date-range";

    // Update and delete reservations
    public static final String UPDATE_RESERVATION = "/api/reservations/update/{reservationId}";
    public static final String DELETE_RESERVATION = "/api/reservations/delete/{reservationId}";

}
