package software.xdev.vaadin.daterange_picker.ui;

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
