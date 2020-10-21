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

@PageTitle("DateRangePicker Examples")
@Route("")
public class HomeView extends Composite<VerticalLayout>
{
	private final Grid<Example> grExamples = new Grid<>();
	
	public HomeView()
	{
		// @formatter:off
		this.grExamples
			.addColumn(new ComponentRenderer<>(example -> {
				final Anchor anchor = new Anchor(example.getRoute(), example.getName());
				
				final Span spDesc = new Span(example.getDesc());
				spDesc.getStyle().set("font-style", "italic");
				spDesc.getStyle().set("font-size", "90%");
				
				final VerticalLayout vl = new VerticalLayout(anchor, spDesc);
				vl.setSpacing(false);
				return vl;
			}))
			.setHeader("Available demos");
		
		// @formatter:on
		
		this.grExamples.setSizeFull();
		this.grExamples.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
		
		this.getContent().add(this.grExamples);
		this.getContent().setHeightFull();
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		// @formatter:off
		this.grExamples.setItems(Arrays.asList(
			new Example(DateRangePickerParameterDemo.NAV, "Parameter-Demo", "configuration is stored in QueryParameters"),
			new Example(DateRangePickerLocalizedDemo.NAV, "Localized-Demo", "simple localization"),
			new Example(DateRangePickerCustomizedDemo.NAV, "Customized-Demo", "usage of a customized DateRange")
		));
		// @formatter:on
	}
	
	
	static class Example
	{
		private final String route;
		private final String name;
		private final String desc;
		
		public Example(final String route, final String name, final String desc)
		{
			super();
			this.route = route;
			this.name = name;
			this.desc = desc;
		}

		public String getRoute()
		{
			return this.route;
		}

		public String getName()
		{
			return this.name;
		}

		public String getDesc()
		{
			return this.desc;
		}
	}
}
