package com.udg.mirutina.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore // Esto oculta el controlador en Swagger
public class BasicErrorController implements ErrorController {
  
  @RequestMapping("/error")
  public String getErrorPath() {
      return "Error handling endpoint";
  }

}