package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;

/**
 * Result of a {@link DateRange} calculation
 * @author AB
 *
 */
public interface DateRangeResult
{
	LocalDate getStart();
	LocalDate getEnd();
}
