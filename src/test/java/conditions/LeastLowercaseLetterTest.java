package conditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@PropertySource(value = "classpath:Response.properties")
@ContextConfiguration("classpath:LeastLowercaseLetterTestCase.xml")
public class LeastLowercaseLetterTest {

  LeastLowercaseLetter leastLowercaseLetter;

  @Value("${lowercaseFailure}")
  String lowercaseLetterException;

  @Autowired
  @Qualifier("true")
  private Set<String> passTestCase;

  @Autowired
  @Qualifier("false")
  private Set<String> exceptionTestCase;

  @BeforeEach
  public void setup() {
    leastLowercaseLetter = mock(LeastLowercaseLetter.class);
  }

  /**
   * Test the test_case which will return true
   *
   * @throws Exception Exception
   */
  @Test
  public void checkPasswordTestPass() throws Exception {

    doNothing().when(leastLowercaseLetter).checkPassword(anyString());

    for(String testCase: passTestCase) {
      leastLowercaseLetter.checkPassword(testCase);
      verify(leastLowercaseLetter, times(1)).checkPassword(testCase);
    }
  }

  /**
   * Test the test_case which will throws Exception.
   */
  @Test
  public void checkPasswordTestReturnFalse() {
    for(String testCase: exceptionTestCase) {
      try {
        leastLowercaseLetter.checkPassword(testCase);
      } catch (Exception exception) {
        assertEquals(lowercaseLetterException, exception.getMessage());
      }
    }
  }
}
