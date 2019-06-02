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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "local.api.url.template=http://localhost:%d/recipes/%s" })
public class DeleteMethods {
  
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
  public void deleteOneRecipe() throws Exception {
    //setup
    int recipeId = 1;
    String requestUrl = String.format(urlTemplate, port, recipeId);
    
    String expected = errorWriter.writeValueAsString(TestObjectRepository.deletedResponse);
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
      //verify
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json(expected));

    verify(recipeService).deleteRecipe(recipeId);
  }
  
  @Test
  public void deleteNonexistentRecipe() throws Exception {
    //setup
    int recipeId = 999;
    String requestUrl = String.format(urlTemplate, port, recipeId);

    String expected = errorWriter.writeValueAsString(TestObjectRepository.notFoundResponse);
    System.out.println(expected);
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
        //verify
        .andExpect(status().is(200))
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(recipeService).deleteRecipe(recipeId);
  }
}
