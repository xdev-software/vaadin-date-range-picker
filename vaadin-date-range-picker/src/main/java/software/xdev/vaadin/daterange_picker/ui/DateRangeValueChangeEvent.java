package software.xdev.vaadin.daterange_picker.ui;

/*-
 * #%L
 * DateRangePicker for Vaadin
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

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;

import software.xdev.vaadin.daterange_picker.business.DateRange;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;

public class DateRangeValueChangeEvent<D extends DateRange> extends ComponentValueChangeEvent<DateRangePicker<D>, DateRangeModel<D>>
{
	public DateRangeValueChangeEvent(
		final DateRangePicker<D> source,
		final DateRangeModel<D> oldValue,
		final boolean isFromClient)
	{
		super(source, source, oldValue, isFromClient);
	}
}
