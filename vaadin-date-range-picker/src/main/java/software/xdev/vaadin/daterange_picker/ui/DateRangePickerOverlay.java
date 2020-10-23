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
import java.util.Collection;
import java.util.Optional;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.HasItems;
import com.vaadin.flow.shared.Registration;

import software.xdev.vaadin.daterange_picker.business.DateRange;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.DateRangeResult;


/**
 * Overlay of the expanded {@link DateRangePicker}
 * 
 * @author AB
 *
 */
@CssImport(DateRangePickerStyles.LOCATION)
public class DateRangePickerOverlay<D extends DateRange> extends Composite<VerticalLayout> implements
	HasItems<D>,
	FlexComponent<VerticalLayout>
{
	/*
	 * Fields
	 */
	protected boolean readOnly = false;
	
	protected DateRangePicker<D> dateRangePicker;
	protected DateRangeModel<D> model;
	
	protected Binder<DateRangeModel<D>> binder = new Binder<>();
	
	/*
	 * UI-Comp
	 */
	protected final Button btnBackwardRange = new Button(VaadinIcon.ANGLE_LEFT.create());
	protected ComboBox<D> cbDateRange = new ComboBox<>("Period");
	protected final Button btnForwardRange = new Button(VaadinIcon.ANGLE_RIGHT.create());
	
	protected DatePicker dpStart = new DatePicker("Start");
	protected DatePicker dpEnd = new DatePicker("End");
	
	public DateRangePickerOverlay(final DateRangePicker<D> dateRangePicker)
	{
		this.dateRangePicker = dateRangePicker;
		this.model = this.dateRangePicker.getValue();
		
		this.initUI();
		this.bind();
		this.registerListeners();
	}
	
	protected void initUI()
	{
		this.btnBackwardRange.addClassNames(DateRangePickerStyles.FLEX_CHILD_CONTENTSIZE, DateRangePickerStyles.CLICKABLE);
		
		this.cbDateRange.addClassName(DateRangePickerStyles.FLEX_CHILD_AUTOGROW);
		this.setTextFieldDefaultWidthFlexConform(this.cbDateRange);
		
		this.btnForwardRange.addClassNames(DateRangePickerStyles.FLEX_CHILD_CONTENTSIZE, DateRangePickerStyles.CLICKABLE);
		
		final HorizontalLayout hlRange = new HorizontalLayout();
		hlRange.addClassNames(DateRangePickerStyles.FLEX_CHILD_AUTOGROW, DateRangePickerStyles.FLEX_CONTAINER);
		hlRange.setAlignItems(Alignment.BASELINE);
		hlRange.setJustifyContentMode(JustifyContentMode.BETWEEN);
		hlRange.setMargin(false);
		hlRange.setSpacing(true);
		hlRange.setPadding(false);
		hlRange.add(this.btnBackwardRange, this.cbDateRange, this.btnForwardRange);
		
		this.initDatePicker(this.dpStart);
		this.initDatePicker(this.dpEnd);
		
		final HorizontalLayout hlDatepickers = new HorizontalLayout();
		hlDatepickers.addClassNames(DateRangePickerStyles.FLEX_CHILD_AUTOGROW, DateRangePickerStyles.FLEX_CONTAINER);
		hlDatepickers.setMargin(false);
		hlDatepickers.setSpacing(true);
		hlDatepickers.setPadding(false);
		hlDatepickers.add(this.dpStart, this.dpEnd);
		
		this.addClassName(DateRangePickerStyles.FLEX_CONTAINER);
		this.add(hlRange, hlDatepickers);
		this.getContent().setPadding(true);
	}
	
	protected void initDatePicker(final DatePicker dp)
	{
		this.setTextFieldDefaultWidthFlexConform(dp);
		dp.addClassName(DateRangePickerStyles.FLEX_CHILD_AUTOGROW);
		dp.setWeekNumbersVisible(true);
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.cbDateRange.setItemLabelGenerator(this.dateRangePicker.getDateRangeLocalizerFunction());
		
		this.dateRangePicker.getDatePickerI18n().ifPresent(i18n -> {
			this.dpStart.setI18n(i18n);
			this.dpEnd.setI18n(i18n);
		});
	}
	
	protected void setTextFieldDefaultWidthFlexConform(final HasStyle component)
	{
		component.getStyle().set("--vaadin-text-field-default-width", "auto");
	}
	
	protected void bind()
	{
		this.binder.bind(this.dpStart, DateRangeModel::getStart, DateRangeModel::setStart);
		this.binder.bind(this.dpEnd, DateRangeModel::getEnd, DateRangeModel::setEnd);
		this.binder.bind(this.cbDateRange, DateRangeModel::getDateRange, DateRangeModel::setDateRange);
	}
	
	protected void registerListeners()
	{
		this.dpStart.addValueChangeListener(this::onDatePickerValueChanged);
		this.dpEnd.addValueChangeListener(this::onDatePickerValueChanged);
		this.cbDateRange.addValueChangeListener(this::onComboBoxDateRangeValueChanged);
		this.btnBackwardRange.addClickListener(ev -> this.moveRange(-1));
		this.btnForwardRange.addClickListener(ev ->	this.moveRange(+1));
		
		this.binder.addValueChangeListener(ev -> this.fireEvent(new DateRangeOverlayValueChangeEvent(this)));
	}
	
	protected void onDatePickerValueChanged(final ComponentValueChangeEvent<DatePicker, LocalDate> ev)
	{
		if(!ev.isFromClient())
		{
			return;
		}
		this.setValuesFrom(this.model.getDateRange().calcFor(ev.getValue()));
	}
	
	protected void onComboBoxDateRangeValueChanged(final ComponentValueChangeEvent<ComboBox<D>, D> ev)
	{
		if(!ev.isFromClient())
		{
			return;
		}
		this.setValuesFrom(this.model.getDateRange().calcFor(this.model.getStart()));
	}
	
	protected void moveRange(final int dif)
	{
		this.setValuesFrom(this.model.getDateRange().moveDateRange(this.model.getStart(), dif));
	}
	
	protected void setValuesFrom(final Optional<DateRangeResult> optResult)
	{
		if(!optResult.isPresent())
		{
			return;
		}
		
		final DateRangeResult result = optResult.get();
		this.model.setStart(result.getStart());
		this.model.setEnd(result.getEnd());
		
		this.updateFromModel();
		this.fireEvent(new DateRangeOverlayValueChangeEvent(this));
	}
	
	protected void updateFromModel()
	{
		final boolean datepickerReadonly = !this.model.getDateRange().isSetable();
		this.dpStart.setReadOnly(datepickerReadonly);
		this.dpEnd.setReadOnly(datepickerReadonly);
		
		final boolean fastNavEnabled = this.model.getDateRange().isMovable();
		this.btnBackwardRange.setEnabled(fastNavEnabled);
		this.btnForwardRange.setEnabled(fastNavEnabled);
		
		this.dpEnd.setMin(this.model.getStart());
		this.dpStart.setMax(this.model.getEnd());
		
		this.binder.setBean(this.model);
	}
	
	@Override
	public void setItems(final Collection<D> items)
	{
		this.getCbDateRange().setItems(items);
	}
	
	// -- GETTER --
	public ComboBox<D> getCbDateRange()
	{
		return this.cbDateRange;
	}

	public DatePicker getDpStart()
	{
		return this.dpStart;
	}

	public DatePicker getDpEnd()
	{
		return this.dpEnd;
	}

	// -- DATA --
	public DateRangeModel<D> getModel()
	{
		return this.model;
	}

	public void setModel(final DateRangeModel<D> model)
	{
		this.model = model;
		this.updateFromModel();
	}
	
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
		
		this.binder.setReadOnly(readOnly);
		this.btnBackwardRange.setEnabled(!readOnly);
		this.btnForwardRange.setEnabled(!readOnly);
	}
	
	public boolean isReadOnly()
	{
		return this.readOnly;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Registration addValueChangeListener(final ComponentEventListener<DateRangeOverlayValueChangeEvent> listener)
	{
		return this.addListener(DateRangeOverlayValueChangeEvent.class, (ComponentEventListener)listener);
	}
	
	public class DateRangeOverlayValueChangeEvent extends ComponentEvent<DateRangePickerOverlay<D>>
	{
		public DateRangeOverlayValueChangeEvent(final DateRangePickerOverlay<D> source)
		{
			super(source, false);
		}
	}
}
