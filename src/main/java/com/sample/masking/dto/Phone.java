package com.sample.masking.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Phone {

  @Size(min = 10, max = 10)
  public String workNumber;

  @Size(min = 10, max = 10)
  public String personalNumber;
}