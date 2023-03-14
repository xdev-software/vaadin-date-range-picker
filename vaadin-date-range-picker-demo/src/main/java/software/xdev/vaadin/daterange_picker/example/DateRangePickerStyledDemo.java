package software.xdev.vaadin.daterange_picker.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;


@Route(DateRangePickerStyledDemo.NAV)
public class DateRangePickerStyledDemo extends Composite<VerticalLayout>
{
	public static final String NAV = "styled";
	
	protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
	
	private final DateRangePicker<SimpleDateRange> dateRangePicker =
		new DateRangePicker<>(
			() -> new DateRangeModel<>(LocalDate.now(), LocalDate.now(), SimpleDateRanges.TODAY),
			DATERANGE_VALUES);
	
	private final TextArea taResult =
		new TextArea("ValueChangeEvent", "Change something in the datepicker to see the result");
	
	private final Button btnDarkMode = new Button("Toogle theme");
	
	/*
	 * Fields
	 */
	
	public DateRangePickerStyledDemo()
	{
		this.initUI();
	}
	
	protected void initUI()
	{
		this.btnDarkMode.addClickListener(ev ->
		{
			final ThemeList themeList = UI.getCurrent().getElement().getThemeList();
			
			if(themeList.contains(Lumo.DARK))
			{
				themeList.remove(Lumo.DARK);
			}
			else
			{
				themeList.add(Lumo.DARK);
			}
			
			this.updateBtnDarkMode();
		});
		
		this.taResult.setSizeFull();
		
		this.getContent().setPadding(false);
		this.getContent().add(new VerticalLayout(this.dateRangePicker), new VerticalLayout(this.taResult, this.btnDarkMode));
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
		
		this.updateBtnDarkMode();
	}
	
	protected void updateBtnDarkMode()
	{
		final boolean isDarkMode = UI.getCurrent().getElement().getThemeList().contains(Lumo.DARK);
		this.btnDarkMode.setText(!isDarkMode ? "Enter the darkness" : "Turn the light on");
		this.btnDarkMode.setIcon(!isDarkMode ? VaadinIcon.MOON_O.create() : VaadinIcon.SUN_O.create());
	}
}
