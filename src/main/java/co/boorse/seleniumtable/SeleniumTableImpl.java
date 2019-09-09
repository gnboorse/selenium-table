package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link SeleniumTable}.
 */
class SeleniumTableImpl extends ElementContainerImpl implements SeleniumTable {

    SeleniumTableImpl(WebElement tableElement) {
        super(tableElement);
        String tagName = getElement().getTagName();
        String[] allowedTags = {"table", "tbody", "thead", "tfoot"};
        if (!Arrays.asList(allowedTags).contains(tagName.toLowerCase())) {
            // this is not an html table!
            throw new IllegalArgumentException("Invalid element of type \"" +
                    tagName + "\" provided. Should be \"table\"");
        }
    }

    @Override
    public int rowCount() {
        return rows().size();
    }

    @Override
    public SeleniumTableRow get(int rowIndex) {
        List<SeleniumTableRow> rows = rows();
        if (rowIndex > (rows.size() - 1)) {
            throw new IndexOutOfBoundsException("Row index " + rowIndex +
                    " too large for table with " + rows.size() + " rows.");
        }

        return rows.get(rowIndex);
    }

    @Override
    public SeleniumTableCell get(int rowIndex, int columnIndex) {
        return get(rowIndex).get(columnIndex);
    }

    @Override
    @Nonnull
    public Iterator<SeleniumTableRow> iterator() {
        return rows().iterator();
    }

    @Override
    public void forEach(Consumer<? super SeleniumTableRow> action) {
        rows().forEach(action);
    }

    @Override
    public Spliterator<SeleniumTableRow> spliterator() {
        return rows().spliterator();
    }

    @Override
    public SeleniumTable head() {
        Optional<WebElement> thead = findChild(".//thead");
        WebElement theadElement = thead.orElseThrow(() ->
                new NoSuchElementException("No element of type \"thead\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public SeleniumTable body() {
        Optional<WebElement> tbody = findChild(".//tbody");
        WebElement theadElement = tbody.orElseThrow(() ->
                new NoSuchElementException("No element of type \"tbody\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public SeleniumTable foot() {
        Optional<WebElement> tfoot = findChild(".//tfoot");
        WebElement theadElement = tfoot.orElseThrow(() ->
                new NoSuchElementException("No element of type \"tfoot\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public boolean hasTBody() {
        return findChild(".//tbody").isPresent();
    }

    @Override
    public boolean hasTHead() {
        return findChild(".//thead").isPresent();
    }

    @Override
    public boolean hasTFoot() {
        return findChild(".//tfoot").isPresent();
    }

    public List<SeleniumTableRow> rows() {
        // get the tr elements
        List<WebElement> elements = findChildren(".//" + (hasTBody() ? "tbody/tr" : "tr"));
        return elements.stream().map(SeleniumTableRow::getInstance).collect(Collectors.toList());
    }

    @Override
    public boolean hasHeaderRow() {
        return headerRow() != null;
    }

    @Override
    public SeleniumTableRow headerRow() {
        if (hasTHead()) {
            SeleniumTable thead = head();
            if (thead.rowCount() > 0) {
                SeleniumTableRow firstRow = thead.get(0);
                if (firstRow.isHeaderRow()) {
                    return firstRow;
                }
            }
        }
        List<SeleniumTableRow> rows = rows();
        return rows.stream()
                .filter(SeleniumTableRow::isHeaderRow)
                .findFirst().orElse(null);
    }

    @Override
    public String getCaption() {
        Optional<WebElement> caption = findChild(".//caption");
        return caption.map(WebElement::getText).orElse(null);
    }

    @Override
    public boolean hasCaption() {
        return getCaption() != null;
    }

    @Override
    public List<SeleniumTableCell> getColumn(String columnName) {
        // find the index of this column name
        int foundIndex = getColumnIndex(columnName);

        // if the column cannot be found, we throw an exception
        if (foundIndex == -1) {
            throw new IllegalArgumentException("Unable to find column named \"" + columnName +
                    "\" in table.");
        }

        // column index found... now we get all cells at that index
        List<SeleniumTableCell> columnCells = new ArrayList<>();
        for (SeleniumTableRow row : rows()) {
            try {
                SeleniumTableCell cell = row.get(foundIndex);
                columnCells.add(cell);
            } catch (IndexOutOfBoundsException ignored) {
                // for now we ignore rows that don't have the column
            }
        }

        return columnCells;
    }

    @Override
    public boolean hasColumn(String columnName) {
        return getColumnIndex(columnName) > -1;
    }

    private int getColumnIndex(String columnName) {
        // get the header row
        SeleniumTableRow headerRow = headerRow();
        // if there is no header row, we throw an exception
        if (headerRow == null) {
            throw new UnsupportedOperationException("Cannot get cells for column \"" + columnName +
                    "\" on table without header row.");
        }

        // find the index of this column name
        int foundIndex = -1;
        for (int i = 0; i < headerRow.cellCount(); i++) {
            if (headerRow.get(i).getText().equalsIgnoreCase(columnName)) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }
}
