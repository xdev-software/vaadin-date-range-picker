package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;

import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

/**
 * Model for {@link DateRangePicker}
 * 
 * @author AB
 *
 */
public class DateRangeModel<D extends DateRange> implements DateRangeActions<D, DateRangeModel<D>>
{
	private LocalDate start;
	private LocalDate end;
	private D dateRange;
	
	public DateRangeModel(final LocalDate start, final LocalDate end, final D dateRange)
	{
		super();
		this.start = start;
		this.end = end;
		this.dateRange = dateRange;
	}

	@Override
	public LocalDate getStart()
	{
		return this.start;
	}
	
	@Override
	public DateRangeModel<D> setStart(final LocalDate start)
	{
		this.start = start;
		return this;
	}
	
	@Override
	public LocalDate getEnd()
	{
		return this.end;
	}
	
	@Override
	public DateRangeModel<D> setEnd(final LocalDate end)
	{
		this.end = end;
		return this;
	}
	
	@Override
	public D getDateRange()
	{
		return this.dateRange;
	}
	
	@Override
	public DateRangeModel<D> setDateRange(final D dateRange)
	{
		this.dateRange = dateRange;
		return this;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.dateRange == null ? 0 : this.dateRange.hashCode());
		result = prime * result + (this.end == null ? 0 : this.end.hashCode());
		result = prime * result + (this.start == null ? 0 : this.start.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(this.getClass() != obj.getClass())
		{
			return false;
		}
		final DateRangeModel<?> other = (DateRangeModel<?>)obj;
		if(this.dateRange == null)
		{
			if(other.dateRange != null)
			{
				return false;
			}
		}
		else if(!this.dateRange.equals(other.dateRange))
		{
			return false;
		}
		if(this.end == null)
		{
			if(other.end != null)
			{
				return false;
			}
		}
		else if(!this.end.equals(other.end))
		{
			return false;
		}
		if(this.start == null)
		{
			if(other.start != null)
			{
				return false;
			}
		}
		else if(!this.start.equals(other.start))
		{
			return false;
		}
		return true;
	}
	
	
}
