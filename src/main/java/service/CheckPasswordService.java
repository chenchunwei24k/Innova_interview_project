package service;

import java.util.ArrayList;
import java.util.List;
import conditions.CheckValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@ComponentScan({"conditions", "service"})
@Configuration
@PropertySource(value = "classpath:Response.properties")
public class CheckPasswordService {

  @Value("${valid}")
  String validPassword;

  List<CheckValidPassword> conditions;

  @Autowired
  public CheckPasswordService(List<CheckValidPassword> checkValidPassword) {
    this.conditions = checkValidPassword;
  }

  /**
   * Set checkValidPassword.
   *
   * @param checkValidPassword CheckValidPassword
   */
  public void setCheckValidPassword(List<CheckValidPassword> checkValidPassword) {
    this.conditions = checkValidPassword;
  }

  /**
   * Check password by four conditions.
   *
   * @param password password
   * @return List<String>
   */
  public List<String> checkPassword(String password) {
    List<String> result = new ArrayList<>();
    for(CheckValidPassword checkValidPassword : this.conditions) {
      try {
        checkValidPassword.checkPassword(password);
      } catch (Exception exception) {
        result.add(exception.getMessage());
      }
    }

    if (result.isEmpty())
      result.add(validPassword);

    return result;
  }
}
