package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

public interface SeleniumTableCell extends ElementContainer {
    /**
     * Get an instance of {@link SeleniumTableCell} with the base cell element provided.
     *
     * @param cellElement the base cell element
     * @return an instance of {@link SeleniumTableCell}.
     */
    static SeleniumTableCell getInstance(WebElement cellElement) {
        return new SeleniumTableCellImpl(cellElement);
    }
    String getText();
}
