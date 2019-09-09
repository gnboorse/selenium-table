package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

public interface SeleniumTableCell extends ElementContainer {
    /**
     * Gets an instance of {@link SeleniumTableCell} with the base cell element provided.
     *
     * @param cellElement the base cell element
     * @return an instance of {@link SeleniumTableCell}.
     */
    static SeleniumTableCell getInstance(WebElement cellElement) {
        return new SeleniumTableCellImpl(cellElement);
    }

    /**
     * Gets the text contained in this cell.
     * @return the cell contents as a string
     */
    String getText();

    /**
     * Gets a flag indicating whether this cell is a {@code <th>} cell or not.
     * @return true if a header cell, otherwise false
     */
    boolean isHeaderCell();
}
