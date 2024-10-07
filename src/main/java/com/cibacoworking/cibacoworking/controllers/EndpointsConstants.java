package com.cibacoworking.cibacoworking.controllers;

public class EndpointsConstants {
    //ENDPOINTS

    
    //front-end part
    public static final String ENDPOINT_FRONT_URL = "http://localhost:5173";

    //login/logout
    public static final String LOGIN_URL = "/auth/login";
    public static final String LOGOUT_URL = "/auth/logout";


    //User actions
    public static final String GET_RESERVATIONS_BY_USER = "/api/reservations/user/{userId}";
    //Reservations
    public static final String GET_RESERVATION_BY_ID = "/api/reservations/{reservationId}"; //para modificar reserva / admin/user
    public static final String GET_RESERVATIONS_BY_SPACE_AND_DATE = "/api/spaces/{spaceId}/date-range"; //para ver las horas disponibles sala-despachos y descartarlas en Front / admin/user
    public static final String CREATE_RESERVATION_TABLES_BY_USER = "/api/user/reservations/create/tables"; //crear la reserva de mesas / user
    public static final String CREATE_RESERVATION_OFFICES = "/api/reservations/create/offices"; //crear la reserva de sala-despachos / admin/user
    public static final String UPDATE_RESERVATION = "/api/reservations/update/{reservationId}"; //modificar la reserva / admin/user
    public static final String DELETE_RESERVATION = "/api/reservations/delete/{reservationId}"; //borrar la reserva admin/user

    //Admin reservations
    public static final String CREATE_LONG_TERM_RESERVATION_ADMIN = "/api/admin/reservations/create/longterm"; //crear la reserva de mesas / admin
    
    // Espacios (spaces)
    public static final String GET_SPACE_BY_ID = "/api/spaces/{spaceId}"; //para recibir detalles de un espacio (descripción) / admin/user
    public static final String GET_TABLES_BY_DATE = "/api/spaces/tables/date-range"; //para crear reservas de mesas admin / user
    public static final String GET_TABLES_BY_DATE_WITH_RESERVATIONS = "/api/admin/spaces/tables/reservations/date-range"; //para ver info de reservas de mesas  por el admin / admin

    //Users management by admin
    public static final String GET_ALL_USERS = "/api/admin/users"; //admin
    public static final String GET_USER_BY_ID = "/api/admin/users/{userId}"; //admin
    public static final String CREATE_USER = "/api/admin/create/user"; //admin
    public static final String DELETE_USER = "/api/admin/delete/user/{userId}"; //admin
    public static final String UPDATE_USER = "/api/admin/update/user/{userId}"; //admin
}
