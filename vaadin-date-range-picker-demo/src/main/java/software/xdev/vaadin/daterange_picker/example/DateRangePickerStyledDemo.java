package software.xdev.vaadin.daterange_picker.example;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;


@Route(DateRangePickerStyledDemo.NAV)
public class DateRangePickerStyledDemo extends AbstractSimpleChangeDemo
{
	public static final String NAV = "styled";
	
	private final Button btnDarkMode = new Button("Toggle theme");
	
	public DateRangePickerStyledDemo()
	{
		this.initUI();
	}
	
	@SuppressWarnings("checkstyle:MagicNumber")
	protected void initUI()
	{
		this.dateRangePicker.setWidthFull();
		
		this.taResult.setSizeFull();
		
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
		
		final SplitLayout splitLayout = new SplitLayout(this.dateRangePicker, new Div());
		splitLayout.setSplitterPosition(25);
		splitLayout.setWidthFull();
		
		this.getContent().setPadding(false);
		this.getContent().add(
			splitLayout,
			new VerticalLayout(this.taResult, this.btnDarkMode));
		this.getContent().getChildren().forEach(comp -> ((HasSize)comp).setHeight("50%"));
		this.getContent().setHeightFull();
		
		this.registerDefaultValueChangeListener();
		
		this.updateBtnDarkMode();
	}
	
	protected void updateBtnDarkMode()
	{
		final boolean isDarkMode = UI.getCurrent().getElement().getThemeList().contains(Lumo.DARK);
		this.btnDarkMode.setText(!isDarkMode ? "Enter the darkness" : "Turn the light on");
		this.btnDarkMode.setIcon(!isDarkMode ? VaadinIcon.MOON_O.create() : VaadinIcon.SUN_O.create());
	}
}
