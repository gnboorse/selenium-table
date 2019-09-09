package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of {@link SeleniumTableRow}.
 */
class SeleniumTableRowImpl extends ElementContainerImpl implements SeleniumTableRow {

    SeleniumTableRowImpl(WebElement element) {
        super(element);
    }

    @Override
    public SeleniumTableCell get(int columnIndex) {
        List<SeleniumTableCell> cells = cells();
        if (columnIndex > (cells.size() - 1)) {
            throw new IndexOutOfBoundsException("Column index " + columnIndex +
                    " too large for row with " + cells.size() + " cells.");
        }

        return cells.get(columnIndex);
    }

    @Override
    public int cellCount() {
        return cells().size();
    }

    @Override
    @Nonnull
    public Iterator<SeleniumTableCell> iterator() {
        return cells().iterator();
    }

    @Override
    public void forEach(Consumer<? super SeleniumTableCell> action) {
        cells().forEach(action);
    }

    @Override
    public Spliterator<SeleniumTableCell> spliterator() {
        return cells().spliterator();
    }

    @Override
    public List<SeleniumTableCell> cells() {
        List<WebElement> cells = findChildren(".//td");
        List<WebElement> headerCells = findChildren(".//th");
        return Stream.concat(cells.stream(), headerCells.stream())
                .map(SeleniumTableCell::getInstance)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isHeaderRow() {
        // header rows are rows where all cells are th cells
        return cells().stream().allMatch(SeleniumTableCell::isHeaderCell);
    }
}
