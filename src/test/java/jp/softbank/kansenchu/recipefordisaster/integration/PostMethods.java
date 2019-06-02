package jp.softbank.kansenchu.recipefordisaster.integration;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.controller.RecipeController;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dto.views.ResponseViews;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "local.api.url.template=http://localhost:%d/recipes/%s" })
public class PostMethods {
  
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
  
  class TestView implements ResponseViews.MessageWithRecipe {}
  
  ObjectMapper jsonMapper = new ObjectMapper();

  @Before
  public void setup() throws IOException {
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }
  
  @Test
  public void addRecipe() throws Exception {
    // setup
    String parameter = jsonMapper.writeValueAsString(TestObjectRepository.newRecipeNoId);
    String expected = jsonMapper.writerWithView(TestView.class)
        .writeValueAsString(TestObjectRepository.addOneResponse);

    String requestUrl = String.format(urlTemplate, port, "");

    // act
    mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(parameter))

        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(recipeService).addRecipe(TestObjectRepository.newRecipeNoId);
  }
  
  @Test
  public void addBadRecipe() throws Exception {
 // setup
    RecipeDto fake = new RecipeDto();
    String parameter = jsonMapper.writeValueAsString(fake);
    String expected = jsonMapper.writeValueAsString(TestObjectRepository.invalidRecipeResponse);
    System.out.println(expected);

    String requestUrl = String.format(urlTemplate, port, "");

    // act
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(parameter))

        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected))
        .andReturn();
    verify(recipeService).addRecipe(fake);
  }
}
