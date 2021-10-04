package conditions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(value = "classpath:Response.properties")
public class LengthLimit implements CheckValidPassword {

  @Value("${lengthFailure}")
  String lengthFailure;

  /**
   * Implement checkPassword method.
   *
   * @param password password
   * @return boolean check if it is valid.
   * @throws Exception Exception
   */
  @Override
  public void checkPassword(String password) throws Exception {
    if (!password.matches(".{5,12}"))
      throw new Exception(lengthFailure);
  }
}
