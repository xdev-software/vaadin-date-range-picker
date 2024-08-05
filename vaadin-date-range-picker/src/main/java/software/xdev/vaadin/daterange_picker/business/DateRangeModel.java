/*
 * Copyright © 2020 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;
import java.util.Objects;

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
	public boolean equals(final Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof final DateRangeModel<?> that))
		{
			return false;
		}
		return Objects.equals(this.getStart(), that.getStart())
			&& Objects.equals(this.getEnd(), that.getEnd())
			&& Objects.equals(this.getDateRange(), that.getDateRange());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this.getStart(), this.getEnd(), this.getDateRange());
	}
}
