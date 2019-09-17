package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link SeleniumTableRow}.
 */
class SeleniumTableRowImpl extends ElementContainerImpl implements SeleniumTableRow {

    SeleniumTableRowImpl(WebElement element) {
        super(element);
    }

    @Override
    public SeleniumTableCell get(int columnIndex) {
        Optional<WebElement> elementOptional = findChild("(.//td|.//th)" + "[" + (columnIndex + 1) + "]");
        WebElement element = elementOptional.orElseThrow(() -> new IndexOutOfBoundsException("Column index " + columnIndex +
                " too large for row."));
        return SeleniumTableCell.getInstance(element);
    }

    @Override
    public int cellCount() {
        List<WebElement> cellElements = findChildren(".//td|.//th");
        return cellElements.size();
    }

    @Override
    @Nonnull
    public Iterator<SeleniumTableCell> iterator() {
        return new LazyIterator<>(0, cellCount(), this::get);
    }

    @Override
    public List<SeleniumTableCell> cells() {
        List<WebElement> cells = findChildren(".//td|.//th");
        return cells.stream()
                .map(SeleniumTableCell::getInstance)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isHeaderRow() {
        // header rows are rows where all cells are th cells
        List<WebElement> headerCells = findChildren(".//th");
        return headerCells.size() == cellCount();
    }
}
