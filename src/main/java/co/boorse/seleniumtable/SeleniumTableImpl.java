package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
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
        Optional<WebElement> thead = findTableChild(".//thead");
        WebElement theadElement = thead.orElseThrow(() ->
                new NoSuchElementException("No element of type \"thead\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public SeleniumTable body() {
        Optional<WebElement> tbody = findTableChild(".//tbody");
        WebElement theadElement = tbody.orElseThrow(() ->
                new NoSuchElementException("No element of type \"tbody\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public SeleniumTable foot() {
        Optional<WebElement> tfoot = findTableChild(".//tfoot");
        WebElement theadElement = tfoot.orElseThrow(() ->
                new NoSuchElementException("No element of type \"tfoot\" found on table."));
        return new SeleniumTableImpl(theadElement);
    }

    @Override
    public boolean hasTBody() {
        return findTableChild(".//tbody").isPresent();
    }

    @Override
    public boolean hasTHead() {
        return findTableChild(".//thead").isPresent();
    }

    @Override
    public boolean hasTFoot() {
        return findTableChild(".//tfoot").isPresent();
    }

    public List<SeleniumTableRow> rows() {
        // get the tr elements
        List<WebElement> elements = findTableChildren(".//" + (hasTBody() ? "tbody/tr" : "tr"));
        return elements.stream().map(SeleniumTableRow::getInstance).collect(Collectors.toList());
    }
}
