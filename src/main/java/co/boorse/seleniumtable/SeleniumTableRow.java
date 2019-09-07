package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

public interface SeleniumTableRow extends ElementContainer, Iterable<SeleniumTableCell> {

    /**
     * Get an instance of {@link SeleniumTableRow} with the base row element provided.
     *
     * @param rowElement the base row element
     * @return an instance of {@link SeleniumTableRow}.
     */
    static SeleniumTableRow getInstance(WebElement rowElement) {
        return new SeleniumTableRowImpl(rowElement);
    }

    SeleniumTableCell get(int columnIndex);

    int cellCount();
}
