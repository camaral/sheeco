# sheeco

Marshalling and unmarshalling Spreadsheets to Java Objects

Name|Birth date|Male?|hairLength|hairColor|hairLength|hairColor
----|----------|-----|----------|---------|----------|---------
Floofly|2014/12/12|false|1|orange|1|yellow
Tor|2014/11/11|true|3|black|0|grey

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

```java
List<Cat> cats = Sheeco.fromSpreadsheet(new File(cats.xls), Cat.class);
```