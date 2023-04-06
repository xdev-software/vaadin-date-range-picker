package software.xdev.vaadin.daterange_picker.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;


@Route(DateRangePickerRangeExceedingDemo.NAV)
public class DateRangePickerRangeExceedingDemo extends Composite<VerticalLayout>
{
	public static final String NAV = "rangeExceeding";
	
	protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
	
	private final DateRangePicker<SimpleDateRange> dateRangePicker =
		new DateRangePicker<>(
			() -> new DateRangeModel<>(LocalDate.now(), LocalDate.now(), SimpleDateRanges.TODAY),
			DATERANGE_VALUES)
			.withAllowRangeLimitExceeding(true);
	
	private final TextArea taResult =
		new TextArea("ValueChangeEvent", "Change something in the datepicker to see the result");
	
	/*
	 * Fields
	 */
	
	public DateRangePickerRangeExceedingDemo()
	{
		this.initUI();
	}
	
	protected void initUI()
	{
		this.taResult.setSizeFull();
		
		this.getContent().setPadding(false);
		this.getContent().add(new VerticalLayout(this.dateRangePicker), new VerticalLayout(this.taResult));
		this.getContent().getChildren().forEach(comp -> ((HasSize)comp).setHeight("50%"));
		this.getContent().setHeightFull();
		
		this.dateRangePicker.addValueChangeListener(ev ->
		{
			final DateRangeModel<SimpleDateRange> modell = ev.getValue();
			
			this.taResult.clear();
			this.taResult.setValue(
					"DateRange: " + modell.getDateRange().getKey() + "\r\n" +
					"Start: " + modell.getStart() + "\r\n" +
					"End: " + modell.getEnd() + "\r\n" +
					(ev.getOldValue() != null ?
						"OldValue-DateRange: " + ev.getOldValue().getDateRange().getKey() + "\r\n" +
						"OldValue-Start: " + ev.getOldValue().getStart() + "\r\n" +
						"OldValue-End: " + ev.getOldValue().getEnd()
						: "OldValue: null")
					+ "\r\n"
					+ "IsFromClient: " + ev.isFromClient()
				);
		});
	}
}
