package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
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
        List<SeleniumTableCell> cells = getCells();
        if (columnIndex > (cells.size() - 1)) {
            throw new IndexOutOfBoundsException("Column index " + columnIndex +
                    " too large for row with " + cells.size() + " cells.");
        }

        return cells.get(columnIndex);
    }

    @Override
    public int cellCount() {
        return getCells().size();
    }

    @Override
    @Nonnull
    public Iterator<SeleniumTableCell> iterator() {
        return getCells().iterator();
    }

    @Override
    public void forEach(Consumer<? super SeleniumTableCell> action) {
        getCells().forEach(action);
    }

    @Override
    public Spliterator<SeleniumTableCell> spliterator() {
        return getCells().spliterator();
    }

    private List<SeleniumTableCell> getCells() {
        List<WebElement> cells = findTableChildren(".//td");
        return cells.stream().map(SeleniumTableCellImpl::new).collect(Collectors.toList());
    }
}
