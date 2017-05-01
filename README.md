# sheeco

[![Build Status](https://travis-ci.org/camaral/sheeco.svg)](https://travis-ci.org/camaral/sheeco)


Marshalling and unmarshalling Spreadsheets to Java Objects

Name|Male?|Birth date|hairLength|hairColor|hairLength|hairColor
----|----------|-----|----------|---------|----------|---------
Floofly|false|2014/12/12|1|orange|1|yellow
Tor|true|2014/11/11|3|black|0|grey

```java
@SpreadsheetPayload(name = "Cat")
public class Cat {
	@SpreadsheetAttribute(index = 0)
	String name;
	@SpreadsheetAttribute(index = 1, name="Male?")
	Boolean male;
	@SpreadsheetAttribute(index = 2, name="Birth date")
	Date birthDate;

	@SpreadsheetElement(index = 3)
	Fur body;
	@SpreadsheetElement(index = 5)
	Fur tail;
}
```

```java
@SpreadsheetPayload(name = "Fur")
public class Fur {
	@SpreadsheetAttribute(index = 0)
	private Integer hairLength;
	@SpreadsheetAttribute(index = 1)
	private String hairColor;
}
```

```java
List<Cat> cats = Sheeco.fromSpreadsheet(new File(cats.xls), Cat.class);
```