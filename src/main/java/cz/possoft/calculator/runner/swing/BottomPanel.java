package cz.possoft.calculator.runner.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import cz.possoft.calculator.BaseEntry;
import cz.possoft.calculator.CalculatorFormatterUtils;
import cz.possoft.calculator.CalculatorServiceImpl;
import cz.possoft.calculator.locale.LocalizationManager;
import cz.possoft.calculator.locale.Text;

/**
 * BottomPanel where is calculated table with mortgage values.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class BottomPanel extends JPanel
{	
	private static final long serialVersionUID = 1489;
	
	private CalculatorApplet calculatorApplet;
	private JTable table;
	private JLabel monthPaymentLabel;
	
	CalculatorServiceImpl calculator = new CalculatorServiceImpl();
	String[] columnNames;
	
	/**
	 * Public constructor
	 * 
	 * @param calculatorAppletRef
	 */
	public BottomPanel(CalculatorApplet calculatorAppletRef)
	{
		calculatorApplet = calculatorAppletRef;
		LocalizationManager pm = LocalizationManager.getInstance();		
		columnNames = new String[] {pm.getMessageForKey(Text.YEAR_TB), 
				                    pm.getMessageForKey(Text.START_BALANCE_TB), 
				                    pm.getMessageForKey(Text.INTEREST_TB), 
				                    pm.getMessageForKey(Text.AMORT_TB), 
				                    pm.getMessageForKey(Text.YEAR_PAYMENT_TB), 
				                    pm.getMessageForKey(Text.END_BALANCE_TB), 
				                    pm.getMessageForKey(Text.ITEREST_AMORT_TB)};
	}
	
	/**
	 * Panel initialization
	 */
	public void init()
	{
        double size[][] =
        {{50, 350, 100, 350, 50},
        {30, 30, 40, 400, 30}};
        setLayout(new TableLayout(size));
            
        LocalizationManager pm = LocalizationManager.getInstance();		
        JButton countBT = new JButton(pm.getMessageForKey(Text.CALCULATE_BUTTON));
        countBT.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        		calculate();
        	}
        });
        add(countBT, "2,1,0,0");
        monthPaymentLabel = new JLabel();
        setMonthPaymentLabelValue(null, null);
        add(monthPaymentLabel, "1,2,3,0");
        Component scrollPaneWitTable = initTable();
        add(scrollPaneWitTable, "1,3,3,0");
	}	
	
	/**
	 * Called after pressing button "calculate"
	 */
	private void calculate()
	{
		// get values from Top panel GUI
		TopPanel tp = calculatorApplet.getTopPanel();
		double loan = tp.getLoanVal();
		double interest = tp.getInterestVal();
		int length = tp.getLengthVal();
//		System.out.println("Loan=" + loan + ", Interest=" + interest + ", Length=" + length);		
		
		// calculate month payment and set it to label
		double monthPayment = calculator.calculateMonthPayment(loan, interest , length);
		setMonthPaymentLabelValue(monthPayment, monthPayment + tp.getMonthAmountForPrematurePayment());
		
		// Calculate all values for table
		Object[][] rowData = calculateRowDataFromCalcService(loan, interest, monthPayment, length, tp.getFixationVal(), tp.getMonthAmountForPrematurePayment(), tp.getPeriodOfPrematurePaymentVal(), tp.getPenaltyPercentageForPrematurePaymentInTheMiddleOfFixationVal());
		
		// Set values to table now		
		TableModel model = new HelperTableModel(columnNames, rowData);
		table.setModel(model);
		
		// change length of columns
		changeColumnLengths();
	}
	
	/**
	 * Initialization of table is called during startup of GUI program.
	 * 
	 */
	private Component initTable()
	{				
		Object[][] values = {};	
		table = new JTable(values, columnNames);		        

		//table.setPreferredScrollableViewportSize(new Dimension(600, 70));
        table.setFillsViewportHeight(true);
		
        changeColumnLengths();
        
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
	}
	
	/**
	 * Called when table is changed.
	 */
	private void changeColumnLengths()
	{
        // set column length (not all columns should have same length
        TableColumn column = null;
        for (int i=0 ; i<columnNames.length ; i++)
        {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) 
            {
                column.setPreferredWidth(50); //third column is bigger
            } 
            else if (i == 1) 
            {
                column.setPreferredWidth(130);
            }
            else if (i == 2) 
            {
                column.setPreferredWidth(130);
            }
            else if (i == 3) 
            {
                column.setPreferredWidth(130);
            }            
            else if (i == 4) 
            {
                column.setPreferredWidth(130);
            }
            else if (i == 5) 
            {
                column.setPreferredWidth(130);
            }
            else if (i == 6) 
            {
                column.setPreferredWidth(100);
            }            
        }		
	}
	
	/**
	 * Called when recalculating label with "month payment"
	 */
	private void setMonthPaymentLabelValue(Double valueMortgage, Double valueAll)
	{
		LocalizationManager pm = LocalizationManager.getInstance();		
		String text = pm.getMessageForKey(Text.MONTH_PAYMENT_PREFIX) + " ";
		if (valueMortgage != null)
		{
			text = text + CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, valueMortgage);
		}
		text = text + " , " + pm.getMessageForKey(Text.MONTH_PAYMENT_ALL_PREFIX) + " ";
		if (valueAll != null)
		{
			text = text + CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, valueAll);
		}
		monthPaymentLabel.setText(text);
	}
	
	/**
	 * Calculate all data for table.
	 * 
	 * TODO: move to CalculatorServiceImpl somehow because it contains mostly business logic.
	 * 
	 * @param loan
	 * @param interest
	 * @param monthPayment
	 * @param length
	 * @return
	 */
	private Object[][] calculateRowDataFromCalcService(double loan, double interest, double monthPayment, int length, 
			int fixationLengthInYears, double monthAmountForPrematurePayment, int periodOfPrematurePayment, double penaltyPercentageForPrematurePaymentInTheMiddleOfFixation)
	{
		// init variable
		double balance = loan;
		double interestSumCZK = 0;
		double amortSumCZK = 0;
		double sumCZK = 0;
		
		int currentRemainingFixation = fixationLengthInYears;
		int currentRemainingPeriod = periodOfPrematurePayment;
		double currentBalanceForPrematurePayment = 0;
		
		Object[][] valuesToReturn = new Object[length + 1][columnNames.length];			
		for (int i=0 ; i<length ; i++)
		{
			valuesToReturn[i] = new Object[columnNames.length];
			BaseEntry entry = new BaseEntry(balance, interest , monthPayment, 12, currentRemainingFixation, currentBalanceForPrematurePayment, monthAmountForPrematurePayment, currentRemainingPeriod==1, penaltyPercentageForPrematurePaymentInTheMiddleOfFixation);
			valuesToReturn[i][0] = CalculatorFormatterUtils.formatStringAccordingToLength(6, String.valueOf(i + 1));
			String[] entryAsArray = entry.toString().split("\\|");
			for (int j=0 ; j<entryAsArray.length ; j++)
			{
				valuesToReturn[i][j+1] = entryAsArray[j];
			}
			//System.out.println(CalculatorFormatterUtils.formatStringAccordingToLength(6, String.valueOf(i + 1)) + "|" + entry);
			
			interestSumCZK += entry.getInterestSum();
			amortSumCZK += entry.getAmortSum();
			sumCZK     += entry.getYearPayment();
			balance = entry.getEndBalance();
			
			currentRemainingFixation -= 1;
			// restart fixation if it ends this year.
			if (currentRemainingFixation == 0)
			{
				currentRemainingFixation = fixationLengthInYears;
			}
			currentRemainingPeriod -=1;
			// restart period if it ends this year.
			if (currentRemainingPeriod == 0)
			{
				currentRemainingPeriod = periodOfPrematurePayment;
			}	
			currentBalanceForPrematurePayment = entry.getEndBalanceForPrematurePayment();
			
			// End calculation if the whole mortgage is payed
			if (balance <= 0)
			{
				break;
			}
		}
		
		valuesToReturn[length] = new Object[columnNames.length];
		valuesToReturn[length][2] = CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, interestSumCZK);
		valuesToReturn[length][3] = CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, amortSumCZK);
		valuesToReturn[length][4] = CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, sumCZK);
		
		return valuesToReturn;
	}
	
	/**
	 * 
	 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
	 *
	 */
	private class HelperTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 134221;
		
		private String[] columnNames;
		private Object[][] rowData;
		
		private HelperTableModel(String[] columnNames, Object[][] rowData)
		{
			this.columnNames = columnNames; 
			this.rowData = rowData; 
		}
		
		public String getColumnName(int col) 
		{
	        return columnNames[col].toString();
	    }
		
	    public int getRowCount() 
	    { 
	    	return rowData.length; 
	    }
	    
	    public int getColumnCount() 
	    { 
	    	return columnNames.length; 
	    }
	    
	    public Object getValueAt(int row, int col) 
	    {
	        return rowData[row][col];
	    }
	    
	    public boolean isCellEditable(int row, int col)
	    { 
	    	return true; 
	    }
	    
	    public void setValueAt(Object value, int row, int col) 
	    {
	        rowData[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }		
	}

}
