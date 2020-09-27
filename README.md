
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

The most common use case for this is when you have an object with an expensive (big) field on it. You may not always want to serialize it. Let's say that you've got this class:

```java
public class MyObject{
  private Long id;
  private String name;
  private MySmallObject smallObj;
  private List<MyBigObject> contains;       //expensive list with many entries

  //getters and setters and/or builder
}
```

If you were to return a list of `MyObject`, you may not want to show the `contains` field; with *n* instances of `MyObject` and *m* instances of `MyBigObject` per instance of `MyObject`, you'll be returning n\*m instances.

The typically suggested pattern suggests using the `@JsonIgnore` annotation on the field. However, this effectively makes this field permanently ignored everywhere in your app. What if you want only don't want to show this field when dealing with a single instance rather than a `List`?

Using `JsonView` allows you to filter this field out quickly and easily:

```java
import com.sample.masking.json.JsonView;
import static com.sample.masking.json.Match.match;

//get a list of the objects
List<MyObject> list = myObjectService.list();

//exclude expensive field
String json = mapper.writeValueAsString(JsonView.with(list).onClass(MyObject.class, match().exclude("contains")));
```

### Inclusion

The inverse of this is also possible. For example, let's say this was your class instead:

```java
public class MyObject{
  private Long id;
  private String name;
  private MySmallObject smallObj;
  @JsonIgnore
  private List<MyBigObject> contains;       //expensive list with many entries

  //getters and setters and/or builder
}
```

You can programmatically include fields that are ignored by default:


```java
import com.sample.masking.json.JsonView;
import static com.sample.masking.json.Match.match;

//get a list of the objects
List<MyObject> list = myObjectService.list();

//exclude expensive field
String json = mapper.writeValueAsString(JsonView.with(list).onClass(MyObject.class, match().include("contains")));
```
