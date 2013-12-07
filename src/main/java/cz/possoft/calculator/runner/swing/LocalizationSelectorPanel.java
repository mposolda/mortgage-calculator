package cz.possoft.calculator.runner.swing;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Opening panel with locale selection.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class LocalizationSelectorPanel extends JPanel
{
	private static final long serialVersionUID = 13443;
	
	private Map<String, String> localizationMap = new TreeMap<String, String>();
	private CalculatorApplet calcApplet;
	
	private JComboBox localizationComboBox;
	
	public LocalizationSelectorPanel(CalculatorApplet calcApplet)
	{		
		this.calcApplet = calcApplet;
	}
	
	/**
	 * Method to initialize localization map, which is used by localeCheckbox. It's protected, so that subclass can
	 * add more localizations if needed.
	 * 
	 * TODO: move to some config. file
	 */
	protected void initLocalizationMap()
	{
		localizationMap.put("Czechue", "cs");
		localizationMap.put("English", "en");
	}
	
	/**
	 * Initialization of panel
	 */
	public void init() 
	{
		// initialize localization map first.
		initLocalizationMap();
		
		
		// initialize panel content then
        double size[][] =
        {{300, 100, 100, 50, 400},
        {200, 80, 20, 70, 30, 100, 200}};        
        
        setLayout(new TableLayout(size));        
        
        JButton closeButton = new JButton("Confirm");
        closeButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        		String selectedLocale = localizationMap.get(localizationComboBox.getSelectedItem().toString());        		
        		calcApplet.selectLocaleAndShowCalculator(selectedLocale) ;        		
        	}
        });
        add(new JLabel("Localization:"), "1,2,0,0");
        localizationComboBox = new JComboBox(localizationMap.keySet().toArray());
        add(localizationComboBox, "2,2,3,0");
        add(closeButton, "2,4,0,0");
    }	

}
