package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

class SeleniumTableCellImpl extends ElementContainerImpl implements SeleniumTableCell {

    SeleniumTableCellImpl(WebElement element) {
        super(element);
    }

    @Override
    public String getText() {
        return getElement().getText();
    }
}
