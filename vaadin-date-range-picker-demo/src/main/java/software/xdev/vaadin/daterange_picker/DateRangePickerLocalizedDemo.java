package software.xdev.vaadin.daterange_picker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.daterange_picker.buisness.DateRangeModell;
import software.xdev.vaadin.daterange_picker.buisness.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.buisness.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;


@Route(DateRangePickerLocalizedDemo.NAV)
public class DateRangePickerLocalizedDemo extends Composite<VerticalLayout>
{
	public static final String NAV = "localized";
	
	protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
	
	private final DateRangePicker<SimpleDateRange> dateRangePicker = new DateRangePicker<>(
			() -> new DateRangeModell<>(LocalDate.now(), LocalDate.now(), SimpleDateRanges.TODAY),
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
		});
	
	private final TextArea taResult = new TextArea();
	
	/*
	 * Fields
	 */
	
	public DateRangePickerLocalizedDemo()
	{
		this.initUI();
	}
	
	protected void initUI()
	{
		this.getContent().add(this.dateRangePicker, this.taResult);
		
		this.dateRangePicker.addValueChangeListener(ev -> {
			final DateRangeModell<SimpleDateRange> modell = ev.getModell();
			
			this.taResult.clear();
			this.taResult.setValue(
				"DateRange: " + modell.getDateRange().getKey() + "\r\n" +
					"Start: " + modell.getStart() + "\r\n" +
					"End: " + modell.getEnd()
				);
		});
	}
	
	// @formatter:off
	// List Must start with Sunday and ends with Saturday... Americans...
	private static List<DayOfWeek> daysOfWeekSortedForDatepicker =
		Stream.concat(
			Stream.of(DayOfWeek.SUNDAY),
			Stream.of(DayOfWeek.values()).filter(dow -> !dow.equals(DayOfWeek.SUNDAY))
				)
		.collect(Collectors.toList());
		
	private static List<String> weekdays =
		daysOfWeekSortedForDatepicker.stream()
			.map(dow -> dow.getDisplayName(TextStyle.FULL, Locale.GERMAN))
			.collect(Collectors.toList());
	private static List<String> weekdaysshort =
		daysOfWeekSortedForDatepicker.stream()
			.map(dow -> dow.getDisplayName(TextStyle.SHORT, Locale.GERMAN))
			.collect(Collectors.toList());
	private static List<String> months =
		Stream.of(Month.values())
			.map(m -> m.getDisplayName(TextStyle.FULL, Locale.GERMAN))
			.collect(Collectors.toList());
	// @formatter:on
	
	/**
	 * Standard DatePickerI18N
	 *
	 * @return
	 */
	public static DatePickerI18n getDatePickerI18n()
	{
		final DatePickerI18n datepicker = new DatePickerI18n();
		datepicker.setFirstDayOfWeek(1);
		
		datepicker.setWeek("Woche");
		datepicker.setCalendar("Kalender");
		
		datepicker.setClear("Leeren");
		datepicker.setCancel("Abbrechen");
		datepicker.setToday("Heute");
		
		datepicker.setMonthNames(months);
		datepicker.setWeekdays(weekdays);
		datepicker.setWeekdaysShort(weekdaysshort);
		return datepicker;
	}
}
