package co.boorse.seleniumtable;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Interface describing a table element that we can interact with in selenium.
 */
public interface SeleniumTable extends Iterable<SeleniumTableRow>, ElementContainer {

    /**
     * Gets an instance of {@link SeleniumTable} with the base table element provided.
     *
     * @param tableElement the base table element
     * @return an instance of {@link SeleniumTable}.
     */
    static SeleniumTable getInstance(WebElement tableElement) {
        return new SeleniumTableImpl(tableElement);
    }

    /**
     * Gets an individual table row by its index in the rows in this table
     *
     * @param rowIndex zero-based row index (top to bottom)
     * @return an instance of {@link SeleniumTableRowImpl}
     * @throws IndexOutOfBoundsException if the row index is out of bounds
     */
    SeleniumTableRow get(int rowIndex);

    /**
     * Gets an individual table cell by its row index and column index in the rows in this table
     *
     * @param rowIndex    zero-based row index (top to bottom)
     * @param columnIndex zero-based column index (left to right)
     * @return an instance of {@link SeleniumTableCellImpl}
     * @throws IndexOutOfBoundsException if either index is out of bounds
     */
    SeleniumTableCell get(int rowIndex, int columnIndex);

    /**
     * Gets the {@link List} of all cells
     * under the given column name.
     * A column name corresponds to the text value of a {@code <th>}
     * element somewhere in the table.
     * @param columnName the name of the column to get cells for
     * @return {@link List} of {@link SeleniumTableCell}
     * @throws UnsupportedOperationException if there is no header row in the table
     * @throws IllegalArgumentException if the provided column name cannot be found in the header row
     */
    List<SeleniumTableCell> getColumn(String columnName);

    /**
     * Gets a flag indicating whether or not the provided column name
     * is present in the table.
     * A column name corresponds to the text value of a {@code <th>}
     * element somewhere in the table.
     * @param columnName the name of the column
     * @return true if the column text is found in a {@code <th>} element, otherwise false
     */
    boolean hasColumn(String columnName);

    /**
     * Gets a list of all rows in the table.
     * Note that this method is able to fall back to using the {@code tbody} rows
     * which allows us to iterate over the root table object instead of having to
     * call {@code body())} all the time.
     *
     * @return {@link List} of {@link SeleniumTableRow}
     */
    List<SeleniumTableRow> rows();

    /**
     * Gets the total number of rows in this table
     *
     * @return the row count
     */
    int rowCount();

    /**
     * Gets a table containing the {@code thead} content.
     *
     * @return {@link SeleniumTable} for the head
     */
    SeleniumTable head();

    /**
     * Gets a table containing the {@code tbody} content.
     *
     * @return {@link SeleniumTable} for the body
     */
    SeleniumTable body();

    /**
     * Gets a table containing the {@code tfoot} content.
     *
     * @return {@link SeleniumTable} for the foot
     */
    SeleniumTable foot();

    /**
     * Gets a flag indicating whether or not the table has a
     * {@code tbody} element.
     *
     * @return true if present, otherwise false
     */
    boolean hasTBody();

    /**
     * Gets a flag indicating whether or not the table has a
     * {@code thead} element.
     *
     * @return true if present, otherwise false
     */
    boolean hasTHead();

    /**
     * Gets a flag indicating whether or not the table has a
     * {@code tfoot} element.
     *
     * @return true if present, otherwise false
     */
    boolean hasTFoot();

    /**
     * Gets the {@link SeleniumTableRow} that is the header for
     * this table.
     *
     * @return null if no header row, otherwise a {@link SeleniumTableRow}
     */
    SeleniumTableRow headerRow();

    /**
     * Gets a flag indicating whether or not the table has a
     * header row.
     *
     * @return true if a header row is present, otherwise false
     */
    boolean hasHeaderRow();

    /**y
     * Gets the table {@code <caption>} if present.
     * @return null if no caption element, otherwise a string
     */
    String getCaption();

    /**
     * Gets a flag indicating whether or not the table has a
     * {@code <caption>} element.
     * @return true if present, otherwise false
     */
    boolean hasCaption();

}
