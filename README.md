# sheeco

Marshalling and unmarshalling Spreadsheets to Java Objects

```java
@SpreadsheetPayload(name = "Cat")
public class Cat {
	@SpreadsheetAttribute(index = 0)
	String name;
	@SpreadsheetAttribute(index = 2, name="Birth date")
	Date birthDate;
	@SpreadsheetAttribute(index = 1, name="Male?")
	Boolean male;

	@SpreadsheetElement(index = 3)
	Fur body;
	@SpreadsheetElement(index = 5)
	Fur tail;
}
```