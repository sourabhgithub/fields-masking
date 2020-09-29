package com.sample.masking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

  public static final List<String> MIN = Arrays.asList("profileImage","phoneNumber");
  public static final List<String> ALL = Arrays.asList("address.city","phoneNumber");

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String company;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String profileImage;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String birthDate;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Phone phoneNumber;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Address address;

  public Contact maskedContactDto(Contact contactDto, List<String> maskFields) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Contact contact = new Contact();
    Contact maskingFieldsOnly = getMaskingFieldsOnly(contactDto, maskFields, contact);
    return maskingFieldsOnly;
  }

  private <T> T getMaskingFieldsOnly(T contactDto, List<String> maskFields, T object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    for(PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(object)) {
      if(maskFields.contains(propertyDescriptor.getName())) {
        Field[] fields = object.getClass().getFields();
        for(Field field : fields) {
          if(field.getName().equals(propertyDescriptor.getName()) && field.getType().equals(propertyDescriptor.getPropertyType())) {
            PropertyUtils.setProperty(object, propertyDescriptor.getName(), field.get(contactDto));
          }
        }
      }
    }
    return (T) object;
  }


}