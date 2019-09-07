package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Interface describing a table element that we can interact with in selenium.
 */
public interface SeleniumTable extends Iterable<SeleniumTableRow>, ElementContainer {

    /**
     * Get an instance of {@link SeleniumTable} with the base table element provided.
     *
     * @param tableElement the base table element
     * @return an instance of {@link SeleniumTable}.
     */
    static SeleniumTable getInstance(WebElement tableElement) {
        return new SeleniumTableImpl(tableElement);
    }

    ;

    /**
     * Get an individual table row by its index in the rows in this table
     *
     * @param rowIndex zero-based row index (top to bottom)
     * @return an instance of {@link SeleniumTableRowImpl}
     * @throws IndexOutOfBoundsException if the row index is out of bounds
     */
    SeleniumTableRow get(int rowIndex);

    /**
     * Get an individual table cell by its row index and column index in the rows in this table
     *
     * @param rowIndex    zero-based row index (top to bottom)
     * @param columnIndex zero-based column index (left to right)
     * @return an instance of {@link SeleniumTableCellImpl}
     * @throws IndexOutOfBoundsException if either index is out of bounds
     */
    SeleniumTableCell get(int rowIndex, int columnIndex);

    /**
     * Get a list of all rows in the table.
     * Note that this method is able to fall back to using the {@code tbody} rows
     * which allows us to iterate over the root table object instead of having to
     * call {@link this::body()} all the time.
     *
     * @return {@link List<SeleniumTableRow>} list of rows
     */
    List<SeleniumTableRow> rows();

    /**
     * Get the total number of rows in this table
     *
     * @return the row count
     */
    int rowCount();

    /**
     * Get a table containing the {@code thead} content.
     *
     * @return {@link SeleniumTable} for the head
     */
    SeleniumTable head();

    /**
     * Get a table containing the {@code tbody} content.
     *
     * @return {@link SeleniumTable} for the body
     */
    SeleniumTable body();

    /**
     * Get a table containing the {@code tfoot} content.
     *
     * @return {@link SeleniumTable} for the foot
     */
    SeleniumTable foot();

    boolean hasTBody();

    boolean hasTHead();

    boolean hasTFoot();

}
