package hr.java.web.helloworld.controller;

import hr.java.web.helloworld.dto.AuthRequestDTO;
import hr.java.web.helloworld.dto.JwtResponseDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ArticleControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthController authController;

  private String accessToken;

  @BeforeEach
  void setUp() {
    AuthRequestDTO authRequest = new AuthRequestDTO();
    authRequest.setUsername("admin");
    authRequest.setPassword("admin");

    if(Optional.ofNullable(accessToken).isEmpty()) {
      JwtResponseDTO jwtResponse = authController.authenticateAndGetToken(authRequest);
      accessToken = jwtResponse.getAccessToken();
    }
  }

  @Test
  void testGetAllArticles() throws Exception {
    mockMvc.perform(get("/web-shop")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(104)))
        .andExpect(jsonPath("$[0].articleName", is("Tesla Model Y")))
        .andExpect(jsonPath("$[1].articleName", is("Apartment on the main square")));
  }

  @Test
  void testFilterArticlesByName() throws Exception {
    mockMvc.perform(get("/web-shop/{articleName}", "Tesla Model Y")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].articleName", is("Tesla Model Y")));
  }
}
