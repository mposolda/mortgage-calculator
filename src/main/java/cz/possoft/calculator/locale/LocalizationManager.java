package cz.possoft.calculator.locale;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Managing localization and resource bundles.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 */
public class LocalizationManager {
	
	private static LocalizationManager singleton = null;
		
	private ResourceBundle localizationBundle;
	private static String locale;
	
	/**
	 * Private constructor.
	 * 
	 * @param locale
	 */
	private LocalizationManager(String locale) 
	{
		localizationBundle = ResourceBundle.getBundle("messages", new Locale(locale));	
	}
	
	/**
	 * This should be called before first calling of getInstance.
	 * 
	 * @param locale
	 */
	public static void setLocale(String locale) 
	{
		if (singleton != null)
		{
			throw new IllegalStateException("PropertiesManager.getInstance has been already called!");
		}
		LocalizationManager.locale = locale;
	}	
	
	public static LocalizationManager getInstance() 
	{
		if (locale == null)
		{
			throw new IllegalStateException("PropertiesManager.setLocale should be called before first call to PropertiesManager.getInstance.");
		}
		if (singleton == null)
		{
			synchronized (LocalizationManager.class)
			{
				if (singleton == null)
				{
					singleton = new LocalizationManager(locale);
				}
			}
		}
		return singleton;
	}	
	
	/**
	 * Get message for specific key from localized resource bundle. Key is taken from main properties file messages.properties in main lib "PossoftNetClient".
	 */
	public String getMessageForKey(String key) 
	{
		return getMessageFromBundleSafe(localizationBundle, key);
	}
	
	/**
	 * This is safe version for getting message from bundle. If message is not found for given key in given bundle, then value of key is returned.
	 * No exception is thrown.
	 */
	private static String getMessageFromBundleSafe(ResourceBundle bundle, String key) 
	{
		String result = null;
		try 
		{
			result = bundle.getString(key);
		}
		catch (MissingResourceException mre) 
		{
			result = key;
		}
		return result;
	}	    
		
}
