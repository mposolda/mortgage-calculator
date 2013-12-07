package cz.possoft.calculator.runner.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Container;
import java.awt.Frame;

import javax.swing.JApplet;

import cz.possoft.calculator.CalculatorFormatterUtils;
import cz.possoft.calculator.locale.LocalizationManager;
import cz.possoft.calculator.locale.Text;

/**
 * Main applet of application
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class CalculatorApplet extends JApplet
{
	private static final long serialVersionUID = 134433;
	
	private TopPanel topPanel;
	private BottomPanel bottomPanel;
	private LocalizationSelectorPanel localizationSelectorPanel;
	
    /**
     *  Initialization of main panel
     */
    public void init() {
        double size[][] =
        {{900},
        {170,530}};        
        
        Container mainPanel=getContentPane();
        mainPanel.setLayout(new TableLayout(size));        
        
        mainPanel.add(getLocalizationSelectorPanel(), "0,0,0,1");
        
        // These panels are initialized after locale is selected.
//        mainPanel.add(getTopPanel(), "0,0,0,0");
//        mainPanel.add(getBottomPanel(), "0,1,0,0");
    }
    
    /**
     * It's executed after selecting of localization by user. Calculator panels are shown and whole application GUI
     * is initialized with selected locale.
     * 
     * @param locale
     */
    public void selectLocaleAndShowCalculator(String locale)
    {
    	// init localizationManager
        LocalizationManager.setLocale(locale);
        LocalizationManager.getInstance();
        
        // init number formatter
        CalculatorFormatterUtils.initNumberFormatFromLocale(locale);
        
        // localize title of frame if frame exists
        Container current = this;
        while (current != null)
        {
        	current = current.getParent();
        	if ((current != null) && (current instanceof Frame))
        	{
        		((Frame)current).setTitle(LocalizationManager.getInstance().getMessageForKey(Text.MORTGAGE_CALCULATOR));
        		// break from while when frame founded
        		current = null;
        	}
        }
    	localizationSelectorPanel.setVisible(false);
    	
    	// initialize main panels
    	getContentPane().add(getTopPanel(), "0,0,0,0");
    	getContentPane().add(getBottomPanel(), "0,1,0,0");    	
    }
    
    /**
     * 
     * @return LocalizationSelectorPanel instance
     */
    private LocalizationSelectorPanel getLocalizationSelectorPanel()
    {
    	if (localizationSelectorPanel == null)
    	{
    		localizationSelectorPanel = new LocalizationSelectorPanel(this);
    		localizationSelectorPanel.init();
    	}
    	return localizationSelectorPanel;
    }    
    
    /**
     * Public method because it's called from BottomPanel 
     * 
     * @return TopPanel instance
     */
    public TopPanel getTopPanel()
    {
    	if (topPanel == null)
    	{
    		topPanel = new TopPanel();
    		topPanel.init();
    	}
    	return topPanel;
    }
    
    /**
     * 
     * @return bottompanel instance
     */
    private BottomPanel getBottomPanel()
    {
    	if (bottomPanel == null)
    	{
    		bottomPanel = new BottomPanel(this);
    		bottomPanel.init();
    	}
    	return bottomPanel;
    }    

}
