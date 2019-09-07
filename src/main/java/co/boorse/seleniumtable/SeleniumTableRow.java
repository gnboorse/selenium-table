package co.boorse.seleniumtable;

public interface SeleniumTableRow extends ElementContainer, Iterable<SeleniumTableCell> {
    SeleniumTableCell get(int columnIndex);

    int cellCount();
}
