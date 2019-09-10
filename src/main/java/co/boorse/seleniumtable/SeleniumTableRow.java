package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface SeleniumTableRow extends ElementContainer, Iterable<SeleniumTableCell> {

    /**
     * Gets an instance of {@link SeleniumTableRow} with the base row element provided.
     *
     * @param rowElement the base row element
     * @return an instance of {@link SeleniumTableRow}
     */
    static SeleniumTableRow getInstance(WebElement rowElement) {
        return new SeleniumTableRowImpl(rowElement);
    }

    /**
     * Gets the {@link SeleniumTableCell} in this row from the provided index.
     *
     * @param columnIndex the index of the cell in this row
     * @return an instance of {@link SeleniumTableCell}
     */
    SeleniumTableCell get(int columnIndex);

    /**
     * Gets the {@link List} of all {@link SeleniumTableCell} in this row
     *
     * @return {@link List} of {@link SeleniumTableCell}
     */
    List<SeleniumTableCell> cells();

    /**
     * Gets the number of cells in this row.
     *
     * @return the cell count
     */
    int cellCount();

    /**
     * Check if the row is a header row (containing {@code <th>} elements.
     *
     * @return true if header row, otherwise false
     */
    boolean isHeaderRow();
}
