package software.xdev.vaadin.daterange_picker.example;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(DateRangePickerRangeExceedingDemo.NAV)
public class DateRangePickerRangeExceedingDemo extends AbstractSimpleChangeDemo
{
	public static final String NAV = "rangeExceeding";
	
	public DateRangePickerRangeExceedingDemo()
	{
		this.dateRangePicker.withAllowRangeLimitExceeding(true);
		this.initUI();
	}
	
	protected void initUI()
	{
		this.taResult.setSizeFull();
		
		this.getContent().setPadding(false);
		this.getContent().add(new VerticalLayout(this.dateRangePicker), new VerticalLayout(this.taResult));
		this.getContent().getChildren().forEach(comp -> ((HasSize)comp).setHeight("50%"));
		this.getContent().setHeightFull();
		
		this.registerDefaultValueChangeListener();
	}
}
