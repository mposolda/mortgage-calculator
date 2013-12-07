package cz.possoft.calculator;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Helper methods for formatting numbers.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class CalculatorFormatterUtils 
{
	private static NumberFormat nf;
	static
	{
		initNumberFormatFromLocale("en");
	}
	
	/**
	 * Should be called when locale is initialized.
	 * 
	 * @param locale
	 */
	public static void initNumberFormatFromLocale(String locale)
	{
		nf = NumberFormat.getNumberInstance(new Locale(locale));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	/**
	 * Format string according to required length and numberString.
	 * 
	 * @param requiredLength
	 * @param numberString
	 * @return formatted string
	 */
	public static String formatStringAccordingToLength(int requiredLength, String numberString)
	{
		int lengthToAdd = requiredLength - numberString.length();
		if (lengthToAdd <= 0)
		{
			return numberString;
		}
		
		StringBuilder builder = new StringBuilder();
		for (int i=0 ; i<lengthToAdd ; i++)
		{
			builder.append(" ");
		}
		return builder.append(numberString).toString();
	}	
	
	/**
	 * Format string according to required length and numberString.
	 * 
	 * @param requiredLength
	 * @param doubleValue
	 * @return formatted string
	 */
	public static String getFormattedStringFromDouble(int requiredLength, double doubleValue)
	{
		return formatStringAccordingToLength(requiredLength, nf.format(doubleValue));
	}

}
