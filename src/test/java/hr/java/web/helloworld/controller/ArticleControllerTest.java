package hr.java.web.helloworld.controller;

import hr.java.web.helloworld.dto.ArticleDTO;
import hr.java.web.helloworld.service.ArticleService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

  @Mock
  private ArticleService articleService;

  @InjectMocks
  private ArticleController articleController;

  private MockMvc mockMvc;

  private List<ArticleDTO> articles;

  @BeforeEach
  void setUp() {
//    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();

    // Initialize mock data
    ArticleDTO article1 = new ArticleDTO("Article 1", "Description 1",
        new BigDecimal("100.0"), "Category 1");
    ArticleDTO article2 = new ArticleDTO("Article 2", "Description 2",
        new BigDecimal("150.0"), "Category 2");
    articles = Arrays.asList(article1, article2);
  }

  @Test
  void testGetAllArticles() throws Exception {
    // Mocking service method
    when(articleService.getAllArticles()).thenReturn(articles);

    // Perform GET request and verify the response
    mockMvc.perform(get("/web-shop")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].articleName", is("Article 1")))
        .andExpect(jsonPath("$[1].articleName", is("Article 2")));

    // Verify that the service method was called once
    verify(articleService, times(1)).getAllArticles();
  }

  @Test
  void testFilterArticlesByName() throws Exception {
    // Mocking service method
    when(articleService.getArticlesByName("Article 1")).thenReturn(List.of(articles.get(0)));

    // Perform GET request and verify the response
    mockMvc.perform(get("/web-shop/{articleName}", "Article 1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].articleName", is("Article 1")));

    // Verify that the service method was called once with the correct argument
    verify(articleService, times(1)).getArticlesByName("Article 1");
  }
}

