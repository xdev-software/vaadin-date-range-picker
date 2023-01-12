package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;

/**
 * Actions which can be done with a {@link DateRangeModel}
 * 
 * @author AB
 *
 * @param <D> DateRange
 * @param <T> Implementer; returned as "Builder"
 */
public interface DateRangeActions<D extends DateRange,T>
{
	LocalDate getStart();
	T setStart(final LocalDate start);
	
	LocalDate getEnd();
	T setEnd(final LocalDate end);
	
	D getDateRange();
	T setDateRange(final D dateRange);
}
