package software.xdev.vaadin.daterange_picker.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;


@Route(DateRangePickerLocalizedDemo.NAV)
public class DateRangePickerLocalizedDemo extends Composite<VerticalLayout>
{
	public static final String NAV = "localized";
	
	protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
	
	private final DateRangePicker<SimpleDateRange> dateRangePicker =
		new DateRangePicker<>(
			() -> new DateRangeModel<>(LocalDate.now(), LocalDate.now(), SimpleDateRanges.TODAY),
			DATERANGE_VALUES)
			.withDatePickerI18n(getDatePickerI18n())
			.withDateRangeLocalizerFunction(dr -> {
				if(dr == SimpleDateRanges.TODAY)
				{
					return "Today - Heute";
				}
				else if(dr == SimpleDateRanges.DAY)
				{
					return "Day - Tag";
				}
				else if(dr == SimpleDateRanges.WEEK)
				{
					return "Week - Woche";
				}
				else if(dr == SimpleDateRanges.MONTH)
				{
					return "Month - Monat";
				}
				else if(dr == SimpleDateRanges.QUARTER)
				{
					return "Quarter - Quartal";
				}
				else if(dr == SimpleDateRanges.HALF_YEAR)
				{
					return "Half year - Halbjahr";
				}
				else if(dr == SimpleDateRanges.YEAR)
				{
					return "Year - Jahr";
				}
				else if(dr == SimpleDateRanges.FREE)
				{
					return "Free - Frei";
				}
				
				return "?";
			})
			.withStartLabel("Start - Anfang")
			.withEndLabel("End - Ende")
			.withDateRangeOptionsLabel("Period - Zeitraum");
	
	private final TextArea taResult =
		new TextArea("ValueChangeEvent", "Change something in the datepicker to see the result");
	
	private final Button btnToogleReadonly = new Button("Toogle Readonly");
	
	/*
	 * Fields
	 */
	
	public DateRangePickerLocalizedDemo()
	{
		this.initUI();
	}
	
	protected void initUI()
	{
		this.taResult.setSizeFull();
		this.getContent().add(this.dateRangePicker, this.taResult, this.btnToogleReadonly);
		
		this.btnToogleReadonly.addClickListener(ev ->
			this.dateRangePicker.setReadOnly(!this.dateRangePicker.isReadOnly()));
		
		this.dateRangePicker.addValueChangeListener(ev ->
		{
			final DateRangeModel<SimpleDateRange> modell = ev.getValue();
			
			this.taResult.clear();
			this.taResult.setValue(Map.of(
					"DateRange", modell.getDateRange().getKey(),
					"Start", modell.getStart().toString(),
					"End", modell.getEnd().toString()
				).entrySet()
				.stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining("\r\n")));
		});
	}
	
	// List Must start with Sunday and ends with Saturday... Americans...
	private static final List<DayOfWeek> DAYS_OF_WEEK_SORTED_FOR_DATEPICKER =
		Stream.concat(
				Stream.of(DayOfWeek.SUNDAY),
				Stream.of(DayOfWeek.values()).filter(dow -> !dow.equals(DayOfWeek.SUNDAY)))
			.toList();
	
	private static final List<String> WEEKDAYS = DAYS_OF_WEEK_SORTED_FOR_DATEPICKER.stream()
		.map(dow -> dow.getDisplayName(TextStyle.FULL, Locale.GERMAN))
		.toList();
	private static final List<String> WEEKDAYS_SHORT = DAYS_OF_WEEK_SORTED_FOR_DATEPICKER.stream()
		.map(dow -> dow.getDisplayName(TextStyle.SHORT, Locale.GERMAN))
		.toList();
	private static final List<String> MONTHS = Stream.of(Month.values())
		.map(m -> m.getDisplayName(TextStyle.FULL, Locale.GERMAN))
		.toList();
	
	/**
	 * Standard DatePickerI18N
	 */
	public static DatePickerI18n getDatePickerI18n()
	{
		final DatePickerI18n datepicker = new DatePickerI18n();
		datepicker.setFirstDayOfWeek(1);
		
		datepicker.setCancel("Abbrechen");
		datepicker.setToday("Heute");
		
		datepicker.setMonthNames(MONTHS);
		datepicker.setWeekdays(WEEKDAYS);
		datepicker.setWeekdaysShort(WEEKDAYS_SHORT);
		return datepicker;
	}
}
