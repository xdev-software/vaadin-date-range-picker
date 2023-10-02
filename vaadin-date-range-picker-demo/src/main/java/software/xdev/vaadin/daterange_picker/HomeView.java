package software.xdev.vaadin.daterange_picker;

import java.util.Arrays;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.daterange_picker.example.DateRangePickerCustomizedDemo;
import software.xdev.vaadin.daterange_picker.example.DateRangePickerLocalizedDemo;
import software.xdev.vaadin.daterange_picker.example.DateRangePickerParameterDemo;
import software.xdev.vaadin.daterange_picker.example.DateRangePickerRangeExceedingDemo;
import software.xdev.vaadin.daterange_picker.example.DateRangePickerStyledDemo;


@PageTitle("DateRangePicker Examples")
@Route("")
public class HomeView extends Composite<VerticalLayout>
{
	private final Grid<Example> grExamples = new Grid<>();
	
	public HomeView()
	{
		this.grExamples
			.addColumn(new ComponentRenderer<>(example -> {
				final Anchor anchor = new Anchor(example.route(), example.name());
				
				final Span spDesc = new Span(example.desc());
				spDesc.getStyle().set("font-size", "90%");
				
				final VerticalLayout vl = new VerticalLayout(anchor, spDesc);
				vl.setSpacing(false);
				return vl;
			}))
			.setHeader("Available demos");
		
		this.grExamples.setSizeFull();
		this.grExamples.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
		
		this.getContent().add(this.grExamples);
		this.getContent().setHeightFull();
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.grExamples.setItems(Arrays.asList(
			new Example(
				DateRangePickerStyledDemo.NAV,
				"Styled-Demo",
				"dark mode 🌑  and more"),
			new Example(
				DateRangePickerParameterDemo.NAV,
				"Parameter-Demo",
				"configuration is stored in QueryParameters"),
			new Example(
				DateRangePickerLocalizedDemo.NAV,
				"Localized-Demo",
				"🌐 simple localization"),
			new Example(
				DateRangePickerRangeExceedingDemo.NAV,
				"RangeExceeding-Demo",
				"usage of a range exceeding DateRange"),
			new Example(
				DateRangePickerCustomizedDemo.NAV,
				"Customized-Demo",
				"usage of a customized DateRange")
		));
	}
	
	record Example(String route, String name, String desc)
	{
	}
}
