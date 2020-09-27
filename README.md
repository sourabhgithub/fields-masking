
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
