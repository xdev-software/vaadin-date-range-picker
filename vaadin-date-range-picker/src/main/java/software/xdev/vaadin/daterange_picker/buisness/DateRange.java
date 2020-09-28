package software.xdev.vaadin.daterange_picker.buisness;

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
import java.time.Period;
import java.util.Optional;

public interface DateRange
{
	/**
	 * Returns the identification key, e.g. DAY, MONTH, etc
	 * @return identification key
	 */
	String getKey();
	
	/**
	 * Returns the {@link Period} to move the date, if any
	 * @return {@link Period} to move the date
	 */
	Optional<Period> getOptMovePeriod();
	
	/**
	 * Retunns the default description, e.g. "Day" or "Half year"
	 * @return default description
	 */
	String getDefaultDesc();
	
	/**
	 *  Trys to return the calculated {@link DateRangeResult} for the supplied date
	 * @param date date that is used for calculation
	 * @return calculated {@link DateRangeResult}
	 */
	Optional<DateRangeResult> calcFor(LocalDate date);
	
	/**
	 * Trys to return a moved {@link DateRangeResult}
	 * @param baseDate Date that is used as a base
	 * @param dif Count of moves
	 * @return moved {@link DateRangeResult}
	 */
	Optional<DateRangeResult> moveDateRange(LocalDate baseDate, int dif);
	
	/**
	 * Returns if the {@link DateRange} is movable<br>
	 * Example:
	 * <ul>
	 * 	<li> <code>false</code> for TODAY</li>
	 * 	<li> <code>true</code> for MONTH</li>
	 * 	<li> <code>false</code> for FREE</li>
	 * </ul>
	 * @return if the {@link DateRange} is movable
	 */
	boolean isMovable();
	
	/**
	 * Returns if the {@link DateRange} is calcable<br>
	 * Example:
	 * <ul>
	 * 	<li> <code>true</code> for TODAY</li>
	 * 	<li> <code>true</code> for MONTH</li>
	 * 	<li> <code>false</code> for FREE</li>
	 * </ul>
	 * @return if the {@link DateRange} is calcable
	 */
	boolean isCalcable();
	
	/**
	 * Returns if the {@link DateRange} is settable from a date<br>
	 * Example:
	 * <ul>
	 * 	<li> <code>false</code> for TODAY</li>
	 * 	<li> <code>true</code> for MONTH</li>
	 * 	<li> <code>true</code> for FREE</li>
	 * </ul>
	 * @return if the {@link DateRange} is settable from a date
	 */
	boolean isSetable();
}
