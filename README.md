# selenium-table

![Maven Central](https://img.shields.io/maven-central/v/co.boorse/selenium-table) 

This is a Selenium helper library for interacting with HTML tables in a meaningful way.

## Installation

Maven installation:

```xml
<dependency>
  <groupId>co.boorse</groupId>
  <artifactId>selenium-table</artifactId>
  <version>1.0</version>
</dependency>
```

## Usage

First create a `SeleniumTable` object from an existing Selenium `WebElement` that points to a table.

```java
// get the <table> element
WebElement tableElement = driver.findElement(By.xpath("//*[@id=\"table1\"]"));
// get an instance of SeleniumTable
SeleniumTable table = SeleniumTable.getInstance(tableElement);
```

Now you can iterate over the rows and columns using a foreach loop.

```java
for (SeleniumTableRow row : table) {
	for (SeleniumTableCell cell : cells) {
    // do something with the cell contents, for example...
		System.out.println(cell.getText());
	}
}
```

You can also use a traditional for loop.

```java
for (int i = 0; i < table.rowCount(); i++) {
	SeleniumTableRow row = table.get(i);
	for (int j = 0; j < row.cellCount(); j++) {
		SeleniumTableCell cell = row.get(j);
		System.out.println(cell.getText());
	}
}
```

You can get a specific cell at a row and column index.

```java
SeleniumTableCell cell = table.get(0, 1); // get the cell in the first row, second column
```

If the HTML table has headers, `SeleniumTable` can deal with that too.

```java
// get the header row (containing <th> elements)
if (table.hasHeaderRow()) {
	SeleniumTableRow headerRow = table.headerRow();
}
```

Additionally, `SeleniumTable` can locate cells using header or column names. Column names are case-insensitive.

```java
// if there is a header cell containing the text "Header 1"
if (table.hasColumn("Header 1")) {
  // get all cells in the table below the heading "Header 1"
	List<SeleniumTableCell> header1Cells = table.getColumn("Header 1");
}
```

## Release process

To release new versions of the library, follow the steps below:

```bash
mvn clean install
mvn release:prepare
mvn release:perform
git push–tags
git push origin master
```

The release process used to deploy this library to Maven Central is described in [this tutorial](https://dzone.com/articles/publish-your-artifacts-to-maven-central).

Note that GPG signing will not work unless you have the key.