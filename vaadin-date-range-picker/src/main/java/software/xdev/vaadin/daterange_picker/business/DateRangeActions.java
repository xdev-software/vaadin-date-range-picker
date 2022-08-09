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
