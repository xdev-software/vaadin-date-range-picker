package software.xdev.vaadin.daterange_picker.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;


@Route(DateRangePickerParameterDemo.NAV)
public class DateRangePickerParameterDemo extends Composite<VerticalLayout> implements AfterNavigationObserver
{
	public static final String NAV = "parameter";
	
	protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
	
	public static final String QP_RANGE = "range";
	public static final String QP_RANGE_START = "range_start";
	public static final String QP_RANGE_END = "range_end";
	
	private final DateRangePicker<SimpleDateRange> dateRangePicker = new DateRangePicker<>(
		() -> new DateRangeModel<>(LocalDate.now(), LocalDate.now(), SimpleDateRanges.TODAY),
		DATERANGE_VALUES);
	
	/*
	 * Fields
	 */
	private boolean blockUpdates = true;
	
	public DateRangePickerParameterDemo()
	{
		this.initUI();
	}
	
	protected void initUI()
	{
		this.dateRangePicker.addValueChangeListener(ev -> this.onConfigChanged());
		
		this.getContent().add(this.dateRangePicker);
	}
	
	@SuppressWarnings("checkstyle:MagicNumber")
	protected void onConfigChanged()
	{
		if(this.blockUpdates)
		{
			return;
		}
		
		final DateRangeModel<SimpleDateRange> dateRangeModell = this.dateRangePicker.getValue();
		if(ChronoUnit.DAYS.between(dateRangeModell.getStart(), dateRangeModell.getEnd()) >= 400)
		{
			Notification.show("Selected period too long");
			this.updateCurrentUrlSetDefault();
			return;
		}
		
		this.updateCurrentUrl();
	}
	
	private void updateCurrentUrlSetDefault()
	{
		this.updateCurrentUrl(new Location(DateRangePickerParameterDemo.NAV));
	}
	
	private void updateCurrentUrl()
	{
		final Map<String, String> queryParaMap = new LinkedHashMap<>();
		queryParaMap.put(QP_RANGE, this.dateRangePicker.getDateRange().getKey().toLowerCase());
		
		if(this.dateRangePicker.getDateRange() != SimpleDateRanges.TODAY)
		{
			queryParaMap.put(QP_RANGE_START, this.dateRangePicker.getStart().toString());
		}
		
		if(this.dateRangePicker.getDateRange() == SimpleDateRanges.FREE)
		{
			queryParaMap.put(QP_RANGE_END, this.dateRangePicker.getEnd().toString());
		}
		

		final Map<String, List<String>> queryParas = new LinkedHashMap<>();
		for(final Entry<String, String> entry : queryParaMap.entrySet())
		{
			queryParas.put(entry.getKey(), Collections.singletonList(entry.getValue()));
		}
		
		this.updateCurrentUrl(new Location(DateRangePickerParameterDemo.NAV, new QueryParameters(queryParas)));
	}
	
	private void updateCurrentUrl(final Location location)
	{
		UI.getCurrent().getPage().getHistory().replaceState(null, location);
	}

	@Override
	public void afterNavigation(final AfterNavigationEvent event)
	{
		boolean invalidParameter = false;
		
		final Map<String, List<String>> paras = event.getLocation().getQueryParameters().getParameters();
		
		// --- DateRange-Values ---
		SimpleDateRange dateRange = SimpleDateRanges.TODAY;
		LocalDate start = LocalDate.now();
		LocalDate end = null;
		
		if(paras.containsKey(QP_RANGE) && !paras.get(QP_RANGE).isEmpty())
		{
			final Optional<SimpleDateRange> optQueryDR =
				DATERANGE_VALUES
				.stream()
				.filter(dr -> dr.getKey().equalsIgnoreCase(paras.get(QP_RANGE).get(0)))
				.findFirst();
			
			if(optQueryDR.isPresent())
			{
				dateRange = optQueryDR.get();
			}
			else
			{
				invalidParameter = true;
			}
		}
		
		if(paras.containsKey(QP_RANGE_START) && !paras.get(QP_RANGE_START).isEmpty())
		{
			if(dateRange != SimpleDateRanges.TODAY)
			{
				try
				{
					start = LocalDate.parse(paras.get(QP_RANGE_START).get(0));
				}
				catch(final DateTimeParseException e)
				{
					invalidParameter = true;
				}
			}
			else
			{
				invalidParameter = true;
			}
		}
		
		if(paras.containsKey(QP_RANGE_END) && !paras.get(QP_RANGE_END).isEmpty())
		{
			if(dateRange == SimpleDateRanges.FREE)
			{
				try
				{
					end = LocalDate.parse(paras.get(QP_RANGE_END).get(0));
				}
				catch(final DateTimeParseException e)
				{
					invalidParameter = true;
				}
			}
			else
			{
				invalidParameter = true;
			}
		}
		
		// Check if free range is valid
		if(dateRange == SimpleDateRanges.FREE)
		{
			if(end != null && !start.isAfter(end))
			{
				this.dateRangePicker.setValue(new DateRangeModel<>(start, end, dateRange));
			}
			else
			{
				invalidParameter = true;
			}
		}
		else
		{
			final SimpleDateRange dr = dateRange;
			
			dateRange.calcFor(start)
				.ifPresent(drcr ->
						this.dateRangePicker.setValue(
							new DateRangeModel<>(drcr.getStart(), drcr.getEnd(), dr)));
			
			if(dateRange != this.dateRangePicker.getDateRange() || !start.equals(this.dateRangePicker.getStart()))
			{
				invalidParameter = true;
			}
		}
		
		
		if(invalidParameter)
		{
			Notification.show("Invalid parameter");
		}
			
		this.blockUpdates = false;
		
		this.onConfigChanged();
	}
}
