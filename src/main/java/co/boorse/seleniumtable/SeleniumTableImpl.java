package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
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

    private boolean hasTBody;

    private boolean hasTHead;

    private boolean hasTFoot;

    SeleniumTableImpl(WebElement tableElement) {
        super(tableElement);
        String tagName = getElement().getTagName();
        if (!tagName.toLowerCase().equals("table")) {
            // this is not an html table!
            throw new IllegalArgumentException("Invalid element of type \"" +
                    tagName + "\" provided. Should be \"table\"");
        }

        // check if there is a tbody element
        this.hasTBody = findTableChild(".//tbody").isPresent();

        // check if there is a thead element
        this.hasTHead = findTableChild(".//thead").isPresent();

        // check if there is a tfoot element
        this.hasTFoot = findTableChild(".//tfoot").isPresent();
    }

    @Override
    public int rowCount() {
        return getRows().size();
    }

    @Override
    public SeleniumTableRow get(int rowIndex) {
        List<SeleniumTableRow> rows = getRows();
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
        return getRows().iterator();
    }

    @Override
    public void forEach(Consumer<? super SeleniumTableRow> action) {
        getRows().forEach(action);
    }

    @Override
    public Spliterator<SeleniumTableRow> spliterator() {
        return getRows().spliterator();
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
        return hasTBody;
    }

    @Override
    public boolean hasTHead() {
        return hasTHead;
    }

    @Override
    public boolean hasTFoot() {
        return hasTFoot;
    }

    /**
     * Utility method for internally getting the list of {@link SeleniumTableRow}.
     * Note that this method is able to fall back to using the {@code tbody} rows
     * which allows us to iterate over the root table object instead of having to
     * call {@link this::body()} all the time.
     *
     * @return {@link List<SeleniumTableRow>}
     */
    private List<SeleniumTableRow> getRows() {
        // get the tr elements
        List<WebElement> elements = findTableChildren(".//" + (hasTBody ? "tbody/tr" : "tr"));
        return elements.stream().map(SeleniumTableRowImpl::new).collect(Collectors.toList());
    }
}
