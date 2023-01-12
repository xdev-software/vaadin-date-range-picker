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
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
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
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;


/**
 * Represents a Date-Range-Picker
 * 
 * @author AB
 *
 */
@CssImport(DateRangePickerStyles.LOCATION)
public class DateRangePicker<D extends DateRange> extends Composite<VerticalLayout> implements
	FlexComponent,
	HasItems<D>,
	DateRangeActions<D, DateRangePicker<D>>,
	HasValue<DateRangeValueChangeEvent<D>, DateRangeModel<D>>
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
	protected DateRangeModel<D> model;
	
	/*
	 * Config
	 */
	
	protected Optional<Locale> formatLocale = Optional.empty();
	protected ItemLabelGenerator<D> dateRangeLocalizerFunction = DateRange::getDefaultDescription;
	protected Optional<DatePickerI18n> datePickerI18n = Optional.empty();
	protected boolean closeOnOutsideClick = true;
	
	/*
	 * UI-Components
	 */
	protected final Button btnOverview = new Button();
	
	protected final Div overlayContainer = new Div();
	protected final DateRangePickerOverlay<D> overlay = new DateRangePickerOverlay<>(this);
	
	public DateRangePicker(final DateRangeModel<D> defaultModel)
	{
		this(defaultModel, new ArrayList<>());
	}
	
	public DateRangePicker(final DateRangeModel<D> defaultModel, final D[] items)
	{
		this(defaultModel, new ArrayList<>(Arrays.asList(items)));
	}
	
	public DateRangePicker(final DateRangeModel<D> defaultModel, final Collection<D> items)
	{
		this.model = Objects.requireNonNull(defaultModel);
		this.overlay.setItems(items);
		
		this.initUI();
		this.registerListeners();
	}
	
	public DateRangePicker(final Supplier<DateRangeModel<D>> defaultModelSupplier)
	{
		this(defaultModelSupplier.get());
	}
	
	public DateRangePicker(final Supplier<DateRangeModel<D>> defaultModelSupplier, final D[] items)
	{
		this(defaultModelSupplier.get(), items);
	}
	
	public DateRangePicker(final Supplier<DateRangeModel<D>> defaultModelSupplier, final Collection<D> items)
	{
		this(defaultModelSupplier.get(), items);
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
		return this.formatLocale.orElse(DEFAULT_LOCALE);
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
	 * 
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
	 * 
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
	 * 
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
		this.btnOverview.setWidthFull();
		
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
			this.model = ev.getSource().getModel();
			
			this.updateFromModel(false);
			this.fireEvent(new DateRangeValueChangeEvent<>(this, ev.getOldValue(), ev.isFromClient()));
		});
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.setLocaleFromClient();
		
		this.updateFromModel(true);
		
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
			// Check if the click was done on this element
			"  var isClickInside = spEl.contains(event.target);\r\n" +
			"  if (!isClickInside) {\r\n" +
			"    spEl.$server.clickOutsideOccured()\r\n" +
			"  }\r\n" +
			"}; \r\n" +
			"document.body.addEventListener('click'," + funcName + ");";
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
	
	protected void updateFromModel(final boolean updateOverlay)
	{
		if(updateOverlay)
		{
			this.tryFixInvalidModel();
		}
		
		final DateTimeFormatter formatter =
			DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(this.getFormatLocale());
		
		// @formatter:off
		this.btnOverview.setText(
			this.model.getStart().format(formatter) +
			(
				this.model.getStart().equals(this.model.getEnd()) ?
					"" :
					" - " + this.model.getEnd().format(formatter)
			)
		);
		// @formatter:on
		
		if(updateOverlay)
		{
			this.overlay.setModel(this.model);
		}
	}
	
	protected void tryFixInvalidModel()
	{
		// @formatter:off
		this.model.getDateRange()
			.calcFor(this.model.getStart())
			.ifPresent(result -> {
				this.model.setStart(result.getStart());
				this.model.setEnd(result.getEnd());
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
	 * 
	 * @param label
	 */
	public void setStartLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getDpStart().setLabel(label);
	}
	
	/**
	 * Sets the label for the overlay End-DatePicker
	 * 
	 * @param label
	 */
	public void setEndLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getDpEnd().setLabel(label);
	}
	
	/**
	 * Sets the label for the overlay DateRange-ComboBox
	 * 
	 * @param label
	 */
	public void setDateRangeOptionsLabel(final String label)
	{
		Objects.requireNonNull(label);
		this.getOverlay().getCbDateRange().setLabel(label);
	}
	
	// --- DATA ---
	
	/**
	 * Uses the given {@link DateRange} and calculates with the current Date
	 * the {@link DateRangeModel}, which is then
	 * set by {@link DateRangePicker#setValue(DateRangeModel)}
	 * 
	 * @param range
	 */
	public void setDateRangeForToday(final D range)
	{
		range.calcFor(LocalDate.now()).ifPresent(
			result -> this.setValue(new DateRangeModel<>(result.getStart(), result.getEnd(), range)));
	}
	
	@Override
	public void setItems(final Collection<D> items)
	{
		this.overlay.setItems(items);
	}
	
	@Override
	public LocalDate getStart()
	{
		return this.model.getStart();
	}
	
	@Override
	public DateRangePicker<D> setStart(final LocalDate start)
	{
		this.model.setStart(start);
		this.updateFromModel(true);
		return this;
	}
	
	@Override
	public LocalDate getEnd()
	{
		return this.model.getEnd();
	}
	
	@Override
	public DateRangePicker<D> setEnd(final LocalDate end)
	{
		this.model.setEnd(end);
		this.updateFromModel(true);
		return this;
	}
	
	@Override
	public D getDateRange()
	{
		return this.model.getDateRange();
	}
	
	@Override
	public DateRangePicker<D> setDateRange(final D dateRange)
	{
		this.model.setDateRange(dateRange);
		this.updateFromModel(true);
		return this;
	}
	
	@Override
	public void setValue(final DateRangeModel<D> value)
	{
		Objects.requireNonNull(value);
		
		this.model = value;
		this.updateFromModel(true);
	}
	
	@Override
	public DateRangeModel<D> getValue()
	{
		return this.model;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Registration addValueChangeListener(final ValueChangeListener<? super DateRangeValueChangeEvent<D>> listener)
	{
		@SuppressWarnings("rawtypes")
		final ComponentEventListener componentListener =
			event -> listener.valueChanged((DateRangeValueChangeEvent<D>)event);
		
		return ComponentUtil.addListener(this, DateRangeValueChangeEvent.class, componentListener);
	}
	
	/**
	 * DateRangePicker always has a value<br>
	 * However for compatibility reasons (with Vaadin) this returns {@code null}
	 * @return {@code null}
	 */
	@Override
	public DateRangeModel<D> getEmptyValue()
	{
		return null;
	}
	
	/**
	 * DateRangePicker always has a value<br>
	 * Therefore this always returns {@code false}
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean isEmpty()
	{
		return false;
	}
	
	/**
	 * Do not use this method, as it throws a {@link UnsupportedOperationException}<br>
	 * The calling of clear is not supported because DateRangePicker always has a value<br>
	 * Use {@link DateRangePicker#setValue(DateRangeModel)} instead.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void clear()
	{
		throw new UnsupportedOperationException("The calling of clear is not supported because DateRangePicker always has a value");
	}
	
	@Override
	public void setReadOnly(final boolean readOnly)
	{
		this.getOverlay().setReadOnly(readOnly);
	}
	
	@Override
	public boolean isReadOnly()
	{
		return this.getOverlay().isReadOnly();
	}
	
	/**
	 * The required indicator is not implemented<br>
	 * <br>
	 * This method doesn't have any functionallity
	 */
	@Override
	public void setRequiredIndicatorVisible(final boolean requiredIndicatorVisible)
	{
		// Not required/implemented
	}
	
	/**
	 * The required indicator is not implemented<br>
	 * This will always return {@code false}
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean isRequiredIndicatorVisible()
	{
		return false;
	}
}
