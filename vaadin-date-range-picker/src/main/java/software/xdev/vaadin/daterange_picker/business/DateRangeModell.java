package software.xdev.vaadin.daterange_picker.business;

/*-
 * #%L
 * vaadin-date-range-picker
 * %%
 * Copyright (C) 2020 XDEV Software
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.time.LocalDate;

import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

/**
 * Modell for {@link DateRangePicker}
 * 
 * @author AB
 *
 */
public class DateRangeModell<D extends DateRange> implements DateRangeActions<D, DateRangeModell<D>>
{
	private LocalDate start;
	private LocalDate end;
	private D dateRange;
	
	public DateRangeModell(final LocalDate start, final LocalDate end, final D dateRange)
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
	public DateRangeModell<D> setStart(final LocalDate start)
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
	public DateRangeModell<D> setEnd(final LocalDate end)
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
	public DateRangeModell<D> setDateRange(final D dateRange)
	{
		this.dateRange = dateRange;
		return this;
	}
}
