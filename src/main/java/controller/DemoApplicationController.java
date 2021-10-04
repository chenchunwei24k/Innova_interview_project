package controller;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CheckPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/demo")
public class DemoApplicationController {

  AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(SpringContext.class);

  CheckPasswordService checkPasswordService = context.getBean(CheckPasswordService.class);

  /**
   * Post the response which checking the password is valid or not.
   *
   * @param password password
   * @return response
   */
  @PostMapping("/check/{password}")
  public ResponseEntity<Object> checkPassword(@PathVariable(value = "password") String password) {
    ResponseEntity<Object> response;

    try {
      List<String> result = checkPasswordService.checkPassword(password);
      response = new ResponseEntity<Object>(result.toString(), HttpStatus.OK);
    } catch (Exception error) {
      System.out.println(error.getMessage());
      response = new ResponseEntity<Object>(error.getMessage(), HttpStatus.FORBIDDEN);
    }

    return response;
  }
}
