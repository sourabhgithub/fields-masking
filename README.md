
# JsonView

All the functionality of this library really boils down to a custom Jackson serializer.

## Usage

Just initialize a standard Jackson `ObjectMapper` class like so:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sample.masking.JsonView;
import com.sample.masking.JsonViewSerializer;

//initialize jackson
ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());
```
### Exclusion


If you were to return a list of `MyObject`, you may not want to show the `contains` field; with *n* instances of `MyObject` and *m* instances of `MyBigObject` per instance of `MyObject`, you'll be returning n\*m instances.

The typically suggested pattern suggests using the `@JsonIgnore` annotation on the field. However, this effectively makes this field permanently ignored everywhere in your app. What if you want only don't want to show this field when dealing with a single instance rather than a `List`?

Using `JsonView` allows you to filter this field out quickly and easily:

```java

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

import com.sample.masking.json.JsonView;
import static com.sample.masking.json.Match.match;
 private static final String contactFields[] = {"profileImage",address};

 Contact byId = getById(id);
        String response = mapper.writeValueAsString(JsonView.with(byId).onClass(Contact.class, match().exclude(contactFields)));
```

### List Exclusion

The List of this is also possible. For example, let's say you want to remove the list of objects and inside the list:

```java

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
```

```java
import com.sample.masking.json.JsonView;
import static com.sample.masking.json.Match.match;


private static final String addressFields[] = {"address.city"};
List<Contact> allContacts = findAllContacts();
String response = mapper.writeValueAsString(JsonView.with(allContacts).onClass(Contact.class, match().exclude(addressFields)));
```
