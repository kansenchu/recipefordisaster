package jp.softbank.kansenchu.recipefordisaster.integration;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.controller.RecipeController;
import jp.softbank.kansenchu.recipefordisaster.dto.views.ResponseViews;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "local.api.url.template=http://localhost:%d/recipes/%s" })
public class PatchMethods {
  
  @SpyBean
  RecipeService recipeService;
  
  @Autowired
  RecipeController recipeController;

  @Autowired
  WebApplicationContext wac;

  @LocalServerPort
  int port;

  @Value("${local.api.url.template}")
  String urlTemplate;

  static MockMvc mockMvc;
  
  ObjectMapper jsonMapper = new ObjectMapper();
  
  ObjectWriter jsonWriter = jsonMapper.writerWithView(ResponseViews.MessageWithRecipe.class);
  ObjectWriter errorWriter = jsonMapper.writerWithView(ResponseViews.MessageOnly.class);

  @Before
  public void setup() throws IOException {
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }
  
  @Test
  public void editRecipe() throws Exception {
    // setup
    String parameter = jsonMapper.writeValueAsString(TestObjectRepository.newRecipeNoId);
    String expected = jsonWriter.writeValueAsString(TestObjectRepository.editedResponse);

    String requestUrl = String.format(urlTemplate, port, 1);

    // act
    mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(parameter))

        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(recipeService).editRecipe(1, TestObjectRepository.newRecipeNoId);
  }
  
  @Test
  public void editNonexistentRecipe() throws Exception {
    // setup
    String parameter = jsonMapper.writeValueAsString(TestObjectRepository.newRecipeNoId);
    String expected = errorWriter.writeValueAsString(TestObjectRepository.notFoundResponse);
    String requestUrl = String.format(urlTemplate, port, 999);

    // act
    mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(parameter))

        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(recipeService).editRecipe(999, TestObjectRepository.newRecipeNoId);
  }
}
