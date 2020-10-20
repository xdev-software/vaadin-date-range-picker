package software.xdev.vaadin.daterange_picker.ui;

/*-
 * #%L
 * vaadin-date-range-picker
 * %%
 * Copyright (C) 2020 XDEV Software
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.HasItems;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.shared.Registration;

import software.xdev.vaadin.daterange_picker.business.DateRange;
import software.xdev.vaadin.daterange_picker.business.DateRangeActions;
import software.xdev.vaadin.daterange_picker.business.DateRangeModell;


/**
 * Represents a Date-Range-Picker
 * 
 * @author AB
 *
 */
@CssImport(DateRangePickerStyles.LOCATION)
public class DateRangePicker<D extends DateRange> extends Composite<VerticalLayout> implements
	FlexComponent<VerticalLayout>,
	HasItems<D>,
	DateRangeActions<D, DateRangePicker<D>>
{
	public static final Locale DEFAULT_LOCALE = Locale.US;
	protected static int nextID = 0;
	
	protected static synchronized int getNextID()
	{
		return ++nextID;
	}
	
	/*
	 * Fields
	 */
	protected boolean expanded = false;
	protected DateRangeModell<D> modell;
		
	/*
	 * Config
	 */
	
	protected Optional<Locale> formatLocale = Optional.empty();
	protected ItemLabelGenerator<D> dateRangeLocalizerFunction = DateRange::getDefaultDesc;
	protected Optional<DatePickerI18n> datePickerI18n = Optional.empty();
	protected boolean closeOnOutsideClick = true;
	
	/*
	 * UI-Comp
	 */
	protected final Button btnOverview = new Button();
	
	protected final Div overlayContainer = new Div();
	protected final DateRangePickerOverlay<D> overlay = new DateRangePickerOverlay<>(this);
	
	public DateRangePicker(final DateRangeModell<D> defaultModell)
	{
		this(defaultModell, new ArrayList<>());
	}
	
	public DateRangePicker(final DateRangeModell<D> defaultModell, final D[] items)
	{
		this(defaultModell, new ArrayList<>(Arrays.asList(items)));
	}
	
	public DateRangePicker(final DateRangeModell<D> defaultModell, final Collection<D> items)
	{
		this.modell = defaultModell;
		this.overlay.setItems(items);
		
		this.initUI();
		this.registerListeners();
	}
	
	public DateRangePicker(final Supplier<DateRangeModell<D>> defaultModellSupplier)
	{
		this(defaultModellSupplier.get());
	}
	
	public DateRangePicker(final Supplier<DateRangeModell<D>> defaultModellSupplier, final D[] items)
	{
		this(defaultModellSupplier.get(), items);
	}
	
	public DateRangePicker(final Supplier<DateRangeModell<D>> defaultModellSupplier, final Collection<D> items)
	{
		this(defaultModellSupplier.get(), items);
	}
	
	// -- Initializers --
	
	public DateRangePicker<D> withCloseOnOutsideClick(final boolean closeOnOutsideClick)
	{
		this.closeOnOutsideClick = closeOnOutsideClick;
		return this;
	}
	
	public boolean isCloseOnOutsideClick()
	{
		return this.closeOnOutsideClick;
	}
	
	public DateRangePicker<D> withDatePickerI18n(final DatePickerI18n datePickerI18n)
	{
		this.datePickerI18n = Optional.ofNullable(datePickerI18n);
		return this;
	}
	
	public Optional<DatePickerI18n> getDatePickerI18n()
	{
		return this.datePickerI18n;
	}
	
	public DateRangePicker<D> withFormatLocale(final Locale locale)
	{
		this.formatLocale = Optional.ofNullable(locale);
		return this;
	}
	
	public Locale getFormatLocale()
	{
		return this.formatLocale.isPresent() ? this.formatLocale.get() : DEFAULT_LOCALE;
	}
	
	public DateRangePicker<D> withDateRangeLocalizerFunction(final ItemLabelGenerator<D> dateRangeLocalizerFunction)
	{
		this.dateRangeLocalizerFunction = dateRangeLocalizerFunction;
		return this;
	}
			
	public ItemLabelGenerator<D> getDateRangeLocalizerFunction()
	{
		return this.dateRangeLocalizerFunction;
	}
	
	/**
	 * Shortcut for {@link DateRangePicker#setStartLabel(String)}
	 * @param label
	 * @return
	 */
	public DateRangePicker<D> withStartLabel(final String label)
	{
		this.setStartLabel(label);
		return this;
	}
	
	/**
	 * Shortcut for {@link DateRangePicker#setEndLabel(String)}
	 * @param label
	 * @return
	 */
	public DateRangePicker<D> withEndLabel(final String label)
	{
		this.setEndLabel(label);
		return this;
	}
	
	/**
	 * Shortcut for {@link DateRangePicker#setDateRangeOptionsLabel(String)}
	 * @param label
	 * @return
	 */
	public DateRangePicker<D> withDateRangeOptionsLabel(final String label)
	{
		this.setDateRangeOptionsLabel(label);
		return this;
	}
	
	// -- END Initializers --
	
	protected void initUI()
	{
		// Set an unique ID for each element
		this.setId("DateRangePickerID" + getNextID());
		
		this.btnOverview.addClassNames(DateRangePickerStyles.BUTTON, DateRangePickerStyles.CLICKABLE);
		this.btnOverview.setMinWidth("20em");
		
		this.btnOverview.setDisableOnClick(true);
		
		this.overlay.addClassName(DateRangePickerStyles.OVERLAY_LAYOUT);
		
		this.overlay.setWidthFull();
		this.overlay.setHeight("auto");
		
		this.overlayContainer.setWidthFull();
		this.overlayContainer.addClassName(DateRangePickerStyles.OVERLAY_BASE);
		this.overlayContainer.add(this.overlay);
		
		this.getContent().setSpacing(false);
		this.getContent().setPadding(false);
		this.setSizeUndefined();
		this.add(this.btnOverview, this.overlayContainer);
		
		this.setExpanded(false);
	}
	
	protected void registerListeners()
	{
		this.btnOverview.addClickListener(ev ->
		{
			this.toggle();
			ev.getSource().setEnabled(true);
		});
		this.overlay.addValueChangeListener(ev ->
		{
			this.updateFromModell();
			this.fireEvent(new DateRangeValueChangeEvent(this, this.modell));
		});
	}
	
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.setLocaleFromClient();
		
		this.updateFromModell();
		
		this.addClickOutsideListener();
	}
	
	protected void setLocaleFromClient()
	{
		this.formatLocale = Optional.ofNullable(VaadinService.getCurrentRequest().getLocale());
	}
	
	protected void addClickOutsideListener()
	{
		if(!this.isCloseOnOutsideClick())
		{
			return;
		}
		
		/*
		 * JS-Explanation
		 * Base Function (see https://stackoverflow.com/a/28432139/13122067)
		 * If a click was trigger, check if it originated from this element
		 */
		final String funcName = "outsideClickFunc" + this.getId().get();
		
		// @formatter:off
		final String jsCommand =
			// Define Click-Function
			"var " + funcName + " = function(event) {\r\n" +
			// Get the current Element
			"  var spEl = document.getElementById('" + this.getId().get() + "');\r\n" +
			"  if (!spEl) {\r\n" +
			// If the element got detached/removed, then als delete the listener of the base element
			"    document.removeEventListener('click'," + funcName + ")\r\n" +
			"    return;\r\n" +
			"  }\r\n" +
			// Check if a Vaadin overlay caused the click
			"  if(event.target.id == 'overlay') {\r\n" +
			"    return;\r\n" +
			"  }\r\n" +
			// Check if the click was done by this element
			"  var isClickInside = spEl.contains(event.target);\r\n" +
			"  if (!isClickInside) {\r\n" +
			"    spEl.$server.clickOutsideOccured()\r\n" +
			"  }\r\n" +
			"}; \r\n" +
			"document.addEventListener('click'," + funcName + ");";
		// @formatter:on
		
		this.getContent().getElement().executeJs(jsCommand);
	}
	
	@ClientCallable
	protected void clickOutsideOccured()
	{
		if(!this.isCloseOnOutsideClick())
		{
			return;
		}
		
		if(this.isExpanded())
		{
			this.setExpanded(false);
		}
	}
	
	public void updateFromModell()
	{
		this.tryFixInvalidModell();
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(this.getFormatLocale());
		
		// @formatter:off
		this.btnOverview.setText(
			this.modell.getStart().format(formatter) +
			(
				this.modell.getStart().equals(this.modell.getEnd()) ?
					"" :
					" - " + this.modell.getEnd().format(formatter)
			)
		);
		// @formatter:on
		
		this.overlay.setModell(this.modell);
	}
	
	protected void tryFixInvalidModell()
	{
		// @formatter:off
		this.modell.getDateRange()
			.calcFor(this.modell.getStart())
			.ifPresent(result -> {
				this.modell.setStart(result.getStart());
				this.modell.setEnd(result.getEnd());
			});
		// @formatter:on
	}
	
	protected void toggle()
	{
		this.setExpanded(!this.isExpanded());
	}
	
	protected synchronized void setExpanded(final boolean expanded)
	{
		this.expanded = expanded;
		this.btnOverview.setIcon(expanded ? VaadinIcon.CARET_DOWN.create() : VaadinIcon.CARET_UP.create());
		
		this.overlay.setVisible(expanded);
	}
	
	public synchronized boolean isExpanded()
	{
		return this.expanded;
	}
	
	// --- GET UI ELEMENTS ---
	
	public DateRangePickerOverlay<D> getOverlay()
	{
		return this.overlay;
	}
	
	public Button getBtnOverview()
	{
		return this.btnOverview;
	}

	public Div getOverlayContainer()
	{
		return this.overlayContainer;
	}
	
	// -- LABELS --
	
	/**
	 * Sets the label for the overlay Start-DatePicker
	 * @param label
	 */
	public void setStartLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getDpStart().setLabel(label);
	}
	
	/**
	 * Sets the label for the overlay End-DatePicker
	 * @param label
	 */
	public void setEndLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getDpEnd().setLabel(label);
	}
	
	/**
	 * Sets the label for the overlay DateRange-ComboBox
	 * @param label
	 */
	public void setDateRangeOptionsLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getCbDateRange().setLabel(label);
	}
	
	// --- DATA ---

	public void setModellForTodayAndByDateRange(final D range)
	{
		range.calcFor(LocalDate.now())
			.ifPresent(result -> this.setModell(new DateRangeModell<>(result.getStart(), result.getEnd(), range)));
	}
	
	public void setModell(final DateRangeModell<D> modell)
	{
		this.modell = modell;
		this.updateFromModell();
	}
	
	public DateRangeModell<D> getModell()
	{
		return this.modell;
	}
	
	@Override
	public void setItems(final Collection<D> items)
	{
		this.overlay.setItems(items);
	}

	@Override
	public LocalDate getStart()
	{
		return this.modell.getStart();
	}

	@Override
	public DateRangePicker<D> setStart(final LocalDate start)
	{
		this.modell.setStart(start);
		this.updateFromModell();
		return this;
	}

	@Override
	public LocalDate getEnd()
	{
		return this.modell.getEnd();
	}

	@Override
	public DateRangePicker<D> setEnd(final LocalDate end)
	{
		this.modell.setEnd(end);
		this.updateFromModell();
		return this;
	}

	@Override
	public D getDateRange()
	{
		return this.modell.getDateRange();
	}

	@Override
	public DateRangePicker<D> setDateRange(final D dateRange)
	{
		this.modell.setDateRange(dateRange);
		this.updateFromModell();
		return this;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Registration addValueChangeListener(final ComponentEventListener<DateRangeValueChangeEvent> listener)
	{
		return this.addListener(DateRangeValueChangeEvent.class, (ComponentEventListener)listener);
	}
	
	public class DateRangeValueChangeEvent extends ComponentEvent<DateRangePicker<D>>
	{
		protected final DateRangeModell<D> modell;
		
		public DateRangeValueChangeEvent(final DateRangePicker<D> source, final DateRangeModell<D> modell)
		{
			super(source, false);
			this.modell = modell;
		}

		public DateRangeModell<D> getModell()
		{
			return this.modell;
		}
	}
}
