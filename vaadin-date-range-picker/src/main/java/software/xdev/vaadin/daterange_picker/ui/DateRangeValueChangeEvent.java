package software.xdev.vaadin.daterange_picker.ui;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;

import software.xdev.vaadin.daterange_picker.business.DateRange;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;

public class DateRangeValueChangeEvent<D extends DateRange> extends ComponentValueChangeEvent<DateRangePicker<D>, DateRangeModel<D>>
{
	public DateRangeValueChangeEvent(
		DateRangePicker<D> source)
	{
		super(source, source, null, false);
	}

	/**
	 * {@inheritDoc}
	 * @deprecated Not implemented
	 */
	@Deprecated
	@Override
	public DateRangeModel<D> getOldValue()
	{
		return super.getOldValue();
	}

	/**
	 * {@inheritDoc}
	 * @deprecated Not implemented
	 */
	@Deprecated
	@Override
	public boolean isFromClient()
	{
		return super.isFromClient();
	}
}