package software.xdev.vaadin.daterange_picker.example.customized;

import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;


public final class CustomDateRanges
{
	private CustomDateRanges()
	{
	}
	
	// No Today-DateRange
	
	public static final CustomDateRange DAY = new CustomDateRange()
		.from(SimpleDateRanges.DAY)
		.withTag("has 24 hours");
	
	public static final CustomDateRange WEEK = new CustomDateRange()
		.from(SimpleDateRanges.WEEK)
		.withTag("has 7 days");
	
	public static final CustomDateRange MONTH = new CustomDateRange()
		.from(SimpleDateRanges.MONTH)
		.withTag("has 28-31 days");
	
	public static final CustomDateRange QUARTER = new CustomDateRange()
		.from(SimpleDateRanges.QUARTER)
		.withTag("has 3 months");
	
	public static final CustomDateRange HALF_YEAR = new CustomDateRange()
		.from(SimpleDateRanges.HALF_YEAR)
		.withTag("has 6 months");
	
	public static final CustomDateRange YEAR = new CustomDateRange()
		.from(SimpleDateRanges.YEAR)
		.withTag("has 12 months");
	
	// No Free-DateRange
	
	public static CustomDateRange[] allValues()
	{
		return new CustomDateRange[] {
			DAY, WEEK, MONTH, QUARTER, HALF_YEAR, YEAR
		};
		
	}
}
