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
package software.xdev.vaadin.daterange_picker.business;

import java.time.LocalDate;

/**
 * Simple implementation of {@link DateRangeResult}
 */
public class SimpleDateRangeResult implements DateRangeResult
{
	private final LocalDate start;
	private final LocalDate end;
	
	public SimpleDateRangeResult(final LocalDate start, final LocalDate end)
	{
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public LocalDate getStart()
	{
		return this.start;
	}

	@Override
	public LocalDate getEnd()
	{
		return this.end;
	}
}
