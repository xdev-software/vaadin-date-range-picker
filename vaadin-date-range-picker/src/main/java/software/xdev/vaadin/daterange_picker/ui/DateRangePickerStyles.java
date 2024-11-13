/*
 * Copyright © 2020 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.vaadin.daterange_picker.ui;

/**
 * Styles for the {@link DateRangePicker}
 */
public final class DateRangePickerStyles
{
	private DateRangePickerStyles()
	{
	}
	
	public static final String LOCATION = "./styles/dateRangePicker.css";
	
	public static final String CLICKABLE = "date-range-picker-clickable";
	
	public static final String BUTTON = "date-range-picker-button";
	public static final String OVERLAY_BASE = "date-range-picker-overlay-base";
	public static final String OVERLAY_LAYOUT = "date-range-picker-overlay-layout";
	public static final String OVERLAY_LAYOUT_ROW = "date-range-picker-overlay-layout-row";
	
	/*
	 * FLEX
	 */
	public static final String FLEX_CHILD_AUTOGROW = "date-range-picker-flex-child-autogrow";
	public static final String FLEX_CHILD_CONTENTSIZE = "date-range-picker-flex-child-contentsize";
	
	// Used to remove Vaadin's default padding which adds a lot of blank space to the overlay
	public static final String PADDING_TOP_XS = "date-range-picker-padding-top-xs";
}
