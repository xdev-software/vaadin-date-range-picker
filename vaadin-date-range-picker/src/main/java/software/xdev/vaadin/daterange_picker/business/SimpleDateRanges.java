package software.xdev.vaadin.daterange_picker.business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;

/**
 * Contains predefined {@link SimpleDateRange SimpleDateRanges}
 * 
 * @author AB
 *
 */
public final class SimpleDateRanges
{
	private SimpleDateRanges()
	{
		// No Impls
	}
	
	// @formatter:off
	public static final SimpleDateRange TODAY = new SimpleDateRange()
		.withKey("TODAY")
		.withDefaultDesc("Today")
		.withMovable(false)
		.withSettable(false)
		.withCalcForFunc(date -> new SimpleDateRangeResult(LocalDate.now(), LocalDate.now()));
	
	public static final SimpleDateRange DAY = new SimpleDateRange()
		.withKey("DAY")
		.withDefaultDesc("Day")
		.withMovePeriod(Period.ofDays(1))
		.withCalcForFunc(date -> new SimpleDateRangeResult(date, date));
	
	public static final SimpleDateRange WEEK = new SimpleDateRange()
		.withKey("WEEK")
		.withDefaultDesc("Week")
		.withMovePeriod(Period.ofWeeks(1))
		.withCalcForFunc(date -> {
			final LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			return new SimpleDateRangeResult(start, start.plusDays(6));
		});
	
	public static final SimpleDateRange MONTH = new SimpleDateRange()
		.withKey("MONTH")
		.withDefaultDesc("Month")
		.withMovePeriod(Period.ofMonths(1))
		.withCalcForFunc(date ->
			new SimpleDateRangeResult(
				date.with(TemporalAdjusters.firstDayOfMonth()),
				date.with(TemporalAdjusters.lastDayOfMonth())));
	
	public static final SimpleDateRange QUARTER = new SimpleDateRange()
		.withKey("QUARTER")
		.withDefaultDesc("Quarter")
		.withMovePeriod(Period.ofMonths(3))
		.withCalcForFunc(date -> {
			final int startMonth = (int)Math.floor((date.getMonthValue() - 1) / 3.0) * 3 + 1;
			final int endMonth = startMonth + 2;
			
			return new SimpleDateRangeResult(
				LocalDate.of(date.getYear(), startMonth, 1),
				LocalDate.of(date.getYear(), endMonth, 1).with(TemporalAdjusters.lastDayOfMonth()));
		});
	
	public static final SimpleDateRange HALF_YEAR = new SimpleDateRange()
		.withKey("HALF_YEAR")
		.withDefaultDesc("Half year")
		.withMovePeriod(Period.ofMonths(6))
		.withCalcForFunc(date -> {
			final int startMonth = (int)Math.floor((date.getMonthValue() - 1) / 6.0) * 6 + 1;
			final int endMonth = startMonth + 5;
			
			return new SimpleDateRangeResult(
				LocalDate.of(date.getYear(), startMonth, 1),
				LocalDate.of(date.getYear(), endMonth, 1).with(TemporalAdjusters.lastDayOfMonth()));
		});
	
	public static final SimpleDateRange YEAR = new SimpleDateRange()
		.withKey("YEAR")
		.withDefaultDesc("Years")
		.withMovePeriod(Period.ofYears(1))
		.withCalcForFunc(date ->
			new SimpleDateRangeResult(
				date.with(TemporalAdjusters.firstDayOfYear()),
				date.with(TemporalAdjusters.lastDayOfYear())));
	
	public static final SimpleDateRange FREE = new SimpleDateRange()
		.withKey("FREE")
		.withDefaultDesc("Free")
		.withMovable(false)
		.withCalcable(false);
	// @formatter:on
	
	public static SimpleDateRange[] allValues()
	{
		return new SimpleDateRange[] {
			TODAY, DAY, WEEK, MONTH, QUARTER, HALF_YEAR, YEAR, FREE
		};
		
	}
}
