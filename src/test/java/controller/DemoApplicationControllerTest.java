package controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.CheckPasswordService;

@WebMvcTest(DemoApplicationController.class)
@PropertySource(value = "classpath:Response.properties")
class DemoApplicationControllerTest {

  static final String mockException = "mock exception";

  private MockMvc mvc;

  private CheckPasswordService checkPasswordService;

  DemoApplicationController demoApplicationController;

  List<String> mockList;

  @Value("${valid}")
  String validPassword;
  @Value("${lowercaseFailure}")
  String lowercaseLetterException;
  @Value("${numericalFailure}")
  String numericalFailureException;
  @Value("${lengthFailure}")
  String lengthFailureException;
  @Value("${sequenceFailure}")
  String sequenceFailureException;

  @BeforeEach
  public void setup() {
    mockList = new ArrayList<>();
    demoApplicationController = new DemoApplicationController();

    mvc = MockMvcBuilders.standaloneSetup(demoApplicationController).build();
  }

  /**
   * Integration Test for checkPassword method which return Valid.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordIntegrationTestReturnValid() throws Exception {
    mockList.add(validPassword);

    mvc.perform(post("/demo/check/{password}", "password123"))
        .andExpect(status().isOk())
        .andExpect(content().string(mockList.toString()));
  }

  /**
   * Integration Test for checkPassword method which return Invalid.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordIntegrationTestReturnInvalid() throws Exception {
    mockList.add(lowercaseLetterException);
    mockList.add(numericalFailureException);
    mockList.add(lengthFailureException);
    mockList.add(sequenceFailureException);

    mvc.perform(post("/demo/check/{password}", "AAA"))
        .andExpect(status().isOk())
        .andExpect(content().string(mockList.toString()));
  }

  /**
   * Mock test for checkPassword method which return valid result.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordMockTest() throws Exception {
    mockList.add(validPassword);
    mockList.add(lowercaseLetterException);

    checkPasswordService = mock(CheckPasswordService.class);
    ReflectionTestUtils.setField(demoApplicationController
        , "checkPasswordService", checkPasswordService);
    when(checkPasswordService.checkPassword(anyString())).thenReturn(mockList);

    mvc.perform(post("/demo/check/{password}", "testPassword"))
        .andExpect(status().isOk())
        .andExpect(content().string(mockList.toString()));
  }

  /**
   * Mock test for checkPassword method which throw exception.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordMockTestThrowsException() throws Exception {
    NullPointerException exception = new NullPointerException(mockException);

    checkPasswordService = mock(CheckPasswordService.class);
    ReflectionTestUtils.setField(demoApplicationController
        , "checkPasswordService", checkPasswordService);
    when(checkPasswordService.checkPassword(anyString())).thenThrow(exception);

    mvc.perform(post("/demo/check/{password}", "testPassword"))
        .andExpect(status().isForbidden())
        .andExpect(content().string(mockException));
  }
}
