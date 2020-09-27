package com.sample.masking.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Address {

  @NotNull
  @Size(max = 30)
  public String addressLine;


  @Size(max = 15)
  public String city;


  @Size(max = 2)
  public String state;

  @Size(max = 6)
  public String zipCode;


  @Size(max = 2)
  public String countryCode;
}