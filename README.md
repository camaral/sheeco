# sheeco

[![Build Status](https://travis-ci.org/camaral/sheeco.svg)](https://travis-ci.org/camaral/sheeco)


Marshalling and unmarshalling Excel Spreadsheets to Java Objects using annotations.
It takes only 4 steps to start using it.

1. Add maven dependency to your project.

```xml
<dependency>
  <groupId>com.github.camaral</groupId>
  <artifactId>sheeco</artifactId>
  <version>1.0</version>
</dependency>
```

2. Create a Excel spreadsheet.

name|Male?|Birth date|hairLength|hairColor|hairLength|hairColor
----|----------|-----|----------|---------|----------|---------
Floofly|false|2014/12/12|1|orange|1|yellow
Tor|true|2014/11/11|3|black|0|grey

3. Describe the Excel spread sheet on Java classes.

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

4. Read the spreadsheet from a File or from an InputStream.

```java
List<Cat> cats = Sheeco.fromSpreadsheet(new File(cats.xls), Cat.class);
```