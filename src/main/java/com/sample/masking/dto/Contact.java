package com.sample.masking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Contact {

  public String name;

  public String company;

  public String profileImage;

  @JsonFormat(pattern = "yyyy-MM-dd")
  public String birthDate;

  public Phone phoneNumber;

  public String email;

  public Address address;
}