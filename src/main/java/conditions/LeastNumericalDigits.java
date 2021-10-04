package conditions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(value = "classpath:Response.properties")
public class LeastNumericalDigits implements CheckValidPassword {

  @Value("${numericalFailure}")
  String numericalFailure;

  /**
   * Implement checkPassword method.
   *
   * @param password password
   * @return boolean check if it is valid.
   * @throws Exception Exception
   */
  @Override
  public void checkPassword(String password) throws Exception {
    if (!password.matches(".*[0-9].*"))
      throw new Exception(numericalFailure);
  }
}
