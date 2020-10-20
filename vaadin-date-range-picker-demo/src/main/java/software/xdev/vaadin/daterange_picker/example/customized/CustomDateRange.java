package software.xdev.vaadin.daterange_picker.example.customized;

import software.xdev.vaadin.daterange_picker.buisness.AbstractDateRange;

public class CustomDateRange extends AbstractDateRange<CustomDateRange>
{
	/**
	 * Stores some data
	 */
	private String tag;
	
	public CustomDateRange withTag(final String tag)
	{
		this.tag = tag != null ? tag : "";
		return this;
	}
	
	public String getTag()
	{
		return this.tag;
	}
}
