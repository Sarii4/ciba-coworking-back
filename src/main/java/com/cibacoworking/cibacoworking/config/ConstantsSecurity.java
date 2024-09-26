package com.cibacoworking.cibacoworking.config;

public class ConstantsSecurity {
    //ENDPOINTS

    //front-end part
    public static final String LOCALHOST_FRONT_URL = "http://localhost:5173";

    //User actions
    public static final String GET_RESERVATIONS_BY_USER = "/api/reservations/user/{userId}";

    
    //Reservations

    public static final String GET_RESERVATION_BY_ID = "/api/reservations/{reservationId}";

    public static final String GET_RESERVATIONS_BY_SPACE_AND_DATE = "/api/spaces/{spaceId}/date-range";
    public static final String GET_TABLES_BY_DATE = "/api/spaces/date-range";
    
    public static final String CREATE_RESERVATION_TABLES = "/api/reservations/create/tables";
    public static final String CREATE_RESERVATION_OFFICES = "/api/reservations/create/offices";
    public static final String UPDATE_RESERVATION = "/api/reservations/update/{reservationId}";
    public static final String DELETE_RESERVATION = "/api/reservations/delete/{reservationId}";

    //Admin reservations
    //public static final String CREATE_RESERVATION_ADMIN = "/api/reservations/admin/create";
    public static final String CREATE_LONG_TERM_RESERVATION_ADMIN = "/api/reservations/admin/create/longterm";
    
    // Espacios (spaces)


}
