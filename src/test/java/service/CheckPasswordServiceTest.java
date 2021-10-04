package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import conditions.CheckValidPassword;
import conditions.LeastLowercaseLetter;
import conditions.LeastNumericalDigits;
import conditions.LengthLimit;
import conditions.RepeatSequence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "classpath:Response.properties")
public class CheckPasswordServiceTest {

  static final String mockLowercaseLetterException = "mock lowercase exception";
  static final String mockNumericalFailureException = "mock numerical exception";
  static final String mockLengthFailureException = "mock length exception";
  static final String mockSequenceFailureException = "mock repeat exception";

  @Value("${valid}")
  String validPassword;

  CheckPasswordService checkPasswordService;

  LeastLowercaseLetter leastLowercaseLetter = Mockito.mock(LeastLowercaseLetter.class);
  LeastNumericalDigits leastNumericalDigits = Mockito.mock(LeastNumericalDigits.class);
  LengthLimit lengthLimit = Mockito.mock(LengthLimit.class);
  RepeatSequence repeatSequence = Mockito.mock(RepeatSequence.class);

  List<CheckValidPassword> checkValidPasswordList;

  @BeforeEach
  public void setup() {
    checkValidPasswordList = new ArrayList<>();
    checkValidPasswordList.add(leastLowercaseLetter);
    checkValidPasswordList.add(leastNumericalDigits);
    checkValidPasswordList.add(lengthLimit);
    checkValidPasswordList.add(repeatSequence);

    checkPasswordService = new CheckPasswordService(checkValidPasswordList);
  }

  /**
   * Mock Test for checkPassword method which throw exception.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordThrowExceptionTest() throws Exception {
    List<String> excepted = new ArrayList<>();
    excepted.add(mockLowercaseLetterException);
    excepted.add(mockNumericalFailureException);
    excepted.add(mockLengthFailureException);
    excepted.add(mockSequenceFailureException);

    doThrow(new Exception(mockLowercaseLetterException))
        .when(leastLowercaseLetter).checkPassword(anyString());
    doThrow(new Exception(mockNumericalFailureException))
        .when(leastNumericalDigits).checkPassword(anyString());
    doThrow(new Exception(mockLengthFailureException))
        .when(lengthLimit).checkPassword(anyString());
    doThrow(new Exception(mockSequenceFailureException))
        .when(repeatSequence).checkPassword(anyString());

    List<String> result = checkPasswordService.checkPassword("mockPassword");

    assertEquals(excepted, result);
  }

  /**
   * Mock test for checkPassword method which return valid result.
   *
   * @throws Exception
   */
  @Test
  public void checkPasswordPassConditionsTest() throws Exception {
    List<String> excepted = new ArrayList<>();
    excepted.add(validPassword);

    doNothing().when(leastLowercaseLetter).checkPassword(anyString());
    doNothing().when(leastNumericalDigits).checkPassword(anyString());
    doNothing().when(lengthLimit).checkPassword(anyString());
    doNothing().when(repeatSequence).checkPassword(anyString());

    List<String> result = checkPasswordService.checkPassword("mockPassword");

    assertEquals(excepted, result);
  }

}
