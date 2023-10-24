package com.udg.mirutina.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdatePassword extends LoginDto {

  private String confirmPassword;
  private String newPassword;

  @ApiModelProperty(hidden = true) // not visible on swagger
  private String username;
  
}
