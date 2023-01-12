package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;

/**
 * Simple implementation of {@link DateRangeResult}
 * 
 * @author AB
 *
 */
public class SimpleDateRangeResult implements DateRangeResult
{
	private final LocalDate start;
	private final LocalDate end;
	
	public SimpleDateRangeResult(final LocalDate start, final LocalDate end)
	{
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public LocalDate getStart()
	{
		return this.start;
	}

	@Override
	public LocalDate getEnd()
	{
		return this.end;
	}
}
