// package com.cibacoworking.cibacoworking.controllers;

// import static org.hamcrest.Matchers.is;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
// import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
// import com.cibacoworking.cibacoworking.models.dtos.requests.LoginRequestDTO;
// import com.cibacoworking.cibacoworking.models.dtos.responses.AuthResponseDTO;
// import com.cibacoworking.cibacoworking.services.AuthService;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(AuthController.class)
// public class AuthControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private AuthService authService;

//     private ObjectMapper objectMapper;

//     @BeforeEach
//     public void setUp() {
//         objectMapper = new ObjectMapper();
//     }

//     @Test
//     public void loginSuccessTest() throws Exception {

//         LoginRequestDTO loginRequest = new LoginRequestDTO();
//         loginRequest.setEmail("user@example.com");
//         loginRequest.setPassword("password");

//         UserDTO userDTO = new UserDTO(1, "Test User", "user@example.com", "1234567890", "Test Project", "USER");


//         AuthResponseDTO authResponse = new AuthResponseDTO("355df2s!245d_w4pd", userDTO);
//         authResponse.setToken("sample-jwt-token");


//         when(authService.login(any(LoginRequestDTO.class))).thenReturn(authResponse);


//         mockMvc.perform(post("/auth/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(loginRequest)))
//                 .andExpect(status().isOk())  
//                 .andExpect(jsonPath("$.token", is("sample-jwt-token")));
//     }

//     @Test
//     public void loginFailureTest() throws Exception {

//         LoginRequestDTO loginRequest = new LoginRequestDTO();
//         loginRequest.setEmail("user@example.com");
//         loginRequest.setPassword("wrongpassword");

//         when(authService.login(any(LoginRequestDTO.class))).thenThrow(new CibaCoworkingException("Invalid credentials"));


//         mockMvc.perform(post("/auth/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(loginRequest)))
//                 .andExpect(status().isUnauthorized()) 
//                 .andExpect(jsonPath("$", is("Invalid credentials")));  
//     }
// }