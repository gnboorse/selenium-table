package co.boorse.seleniumtable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SeleniumTableTest {

    /**
     * Test creating {@link SeleniumTable} instances
     */
    @Test
    public void createTable_Test() {
        // arrange
        WebElement tableElement = mock(WebElement.class);
        when(tableElement.getTagName())
                .thenReturn("table");

        when(tableElement.findElement(eq(By.xpath(".//thead"))))
                .thenThrow(new NoSuchElementException("not found"));
        when(tableElement.findElement(eq(By.xpath(".//tbody"))))
                .thenThrow(new NoSuchElementException("not found"));
        when(tableElement.findElement(eq(By.xpath(".//tfoot"))))
                .thenThrow(new NoSuchElementException("not found"));

        WebElement rowElement = mock(WebElement.class);
        when(tableElement.findElements(eq(By.xpath(".//tr"))))
                .thenReturn(Collections.singletonList(rowElement));

        WebElement cellElement = mock(WebElement.class);
        when(rowElement.findElements(eq(By.xpath(".//td|.//th"))))
                .thenReturn(Collections.singletonList(cellElement));


        // act
        SeleniumTable seleniumTable = SeleniumTable.getInstance(tableElement);

        // assert
        assertFalse(seleniumTable.hasTHead());
        assertFalse(seleniumTable.hasTBody());
        assertFalse(seleniumTable.hasTFoot());

        assertEquals(1, seleniumTable.rowCount());
        assertEquals(rowElement, seleniumTable.rows().get(0).getElement());
        assertEquals(rowElement, seleniumTable.get(0).getElement());
        assertEquals(cellElement, seleniumTable.get(0, 0).getElement());
    }

    /**
     * Test creating {@link SeleniumTable} instances
     */
    @Test
    public void createTableWithTBody_Test() {
        // arrange
        WebElement tableElement = mock(WebElement.class);
        WebElement tbodyElement = mock(WebElement.class);
        when(tableElement.getTagName())
                .thenReturn("table");
        when(tbodyElement.getTagName())
                .thenReturn("tbody");

        when(tableElement.findElement(eq(By.xpath(".//thead"))))
                .thenThrow(new NoSuchElementException("not found"));
        when(tableElement.findElement(eq(By.xpath(".//tbody"))))
                .thenReturn(tbodyElement);
        when(tableElement.findElement(eq(By.xpath(".//tfoot"))))
                .thenThrow(new NoSuchElementException("not found"));

        WebElement rowElement = mock(WebElement.class);
        when(tableElement.findElements(eq(By.xpath(".//tbody/tr"))))
                .thenReturn(Collections.singletonList(rowElement));
        when(tbodyElement.findElements(eq(By.xpath(".//tr"))))
                .thenReturn(Collections.singletonList(rowElement));

        WebElement cellElement = mock(WebElement.class);
        when(rowElement.findElements(eq(By.xpath(".//td|.//th"))))
                .thenReturn(Collections.singletonList(cellElement));


        // act
        SeleniumTable seleniumTable = SeleniumTable.getInstance(tableElement);

        // assert
        assertFalse(seleniumTable.hasTHead());
        assertTrue(seleniumTable.hasTBody());
        assertFalse(seleniumTable.hasTFoot());

        assertEquals(1, seleniumTable.rowCount());
        assertEquals(rowElement, seleniumTable.rows().get(0).getElement());
        assertEquals(rowElement, seleniumTable.get(0).getElement());
        assertEquals(cellElement, seleniumTable.get(0, 0).getElement());

        assertEquals(1, seleniumTable.body().rowCount());
        assertEquals(rowElement, seleniumTable.body().rows().get(0).getElement());
        assertEquals(rowElement, seleniumTable.body().get(0).getElement());
        assertEquals(cellElement, seleniumTable.body().get(0, 0).getElement());
    }

    /**
     * Testing iterable functionality.
     */
    @Test
    public void iterable_Test() {
        // arrange
        WebElement tableElement = mock(WebElement.class);
        when(tableElement.getTagName())
                .thenReturn("table");

        when(tableElement.findElement(eq(By.xpath(".//thead"))))
                .thenThrow(new NoSuchElementException("not found"));
        when(tableElement.findElement(eq(By.xpath(".//tbody"))))
                .thenThrow(new NoSuchElementException("not found"));
        when(tableElement.findElement(eq(By.xpath(".//tfoot"))))
                .thenThrow(new NoSuchElementException("not found"));

        WebElement rowElement = mock(WebElement.class);
        when(tableElement.findElements(eq(By.xpath(".//tr"))))
                .thenReturn(Collections.singletonList(rowElement));

        WebElement cellElement = mock(WebElement.class);
        when(rowElement.findElements(eq(By.xpath(".//td|.//th"))))
                .thenReturn(Collections.singletonList(cellElement));

        List<WebElement> encounteredRows = new ArrayList<>();
        List<WebElement> encounteredCells = new ArrayList<>();

        // act
        SeleniumTable seleniumTable = SeleniumTable.getInstance(tableElement);
        for (SeleniumTableRow row : seleniumTable) {
            encounteredRows.add(row.getElement());
            for (SeleniumTableCell cell : row) {
                encounteredCells.add(cell.getElement());
            }
        }

        // assert
        assertTrue(encounteredRows.contains(rowElement));
        assertTrue(encounteredCells.contains(cellElement));
    }
}
