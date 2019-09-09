package co.boorse.seleniumtable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link ElementContainer}.
 */
class ElementContainerImpl implements ElementContainer {
    private WebElement element;

    public ElementContainerImpl(WebElement element) {
        this.element = element;
    }

    @Override
    public WebElement getElement() {
        return element;
    }

    /**
     * Find a child element with the provided relative xpath.
     *
     * @param xpath
     * @return
     */
    protected Optional<WebElement> findChild(String xpath) {
        try {
            return Optional.ofNullable(getElement().findElement(By.xpath(xpath)));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Find all child elements that match the provided relative xpath.
     * @param xpath
     * @return
     */
    protected List<WebElement> findChildren(String xpath) {
        return getElement().findElements(By.xpath(xpath));
    }
}
