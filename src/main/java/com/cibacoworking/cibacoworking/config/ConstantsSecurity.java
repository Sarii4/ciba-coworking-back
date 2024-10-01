package com.cibacoworking.cibacoworking.config;

public class ConstantsSecurity {
    // Constantes de ejemplo
    public static final String SOME_CONSTANT = "some_value";
    public static final String ANOTHER_CONSTANT = "another_value";

    // Constantes para roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // Constantes para reservas
    public static final String CREATE_RESERVATION_TABLES = "CREATE_RESERVATION_TABLES";
    public static final String CREATE_RESERVATION_OFFICES = "CREATE_RESERVATION_OFFICES";
    public static final String CREATE_LONG_TERM_RESERVATION_ADMIN = "CREATE_LONG_TERM_RESERVATION_ADMIN";
    public static final String GET_RESERVATIONS_BY_USER = "GET_RESERVATIONS_BY_USER";
    public static final String GET_RESERVATIONS_BY_SPACE_AND_DATE = "GET_RESERVATIONS_BY_SPACE_AND_DATE";
    public static final String UPDATE_RESERVATION = "UPDATE_RESERVATION";
    public static final String DELETE_RESERVATION = "DELETE_RESERVATION";
    
    // Otras constantes que puedas necesitar
    public static final String JWT_SECRET = "your_jwt_secret"; // Clave secreta para JWT
    public static final long JWT_EXPIRATION = 86400000; // 1 día en milisegundos
}
