package cz.possoft.calculator.runner.swing;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.possoft.calculator.locale.LocalizationManager;
import cz.possoft.calculator.locale.Text;

/**
 * 
 * top panel with the forms and all labels and text fields.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class TopPanel extends JPanel 
{
	private static final long serialVersionUID = 135348;
	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	private JTextField loanTF;
	private JTextField interestTF;
	private JTextField lengthTF;
	
	private JLabel fixationLB;
	private JTextField fixationTF;
	private JLabel monthAmountForPrematurePaymentLB;
	private JTextField monthAmountForPrematurePaymentTF;
	private JLabel periodOfPrematurePaymentLB;
	private JTextField periodOfPrematurePaymentTF;
	private JLabel penaltyPercentageForPrematurePaymentInTheMiddleOfFixationLB;
	private JTextField penaltyPercentageForPrematurePaymentInTheMiddleOfFixationTF;
	
	/**
	 * GUI initialization
	 */
	public void init()
	{
        double size[][] =
        {{297, 297, 297},
        {170}};
        setLayout(new TableLayout(size));
                
        add(getPanel1(), "0,0,0,0");
        add(getPanel2(), "1,0,0,0");
        add(getPanel3(), "2,0,0,0");
        enableOrDisablePrematurePaymentComponents(false);
	}
	
	/**
	 * 
	 * @return loan value from text field
	 */
	public double getLoanVal()
	{
		return getDoubleFromTextField(loanTF);
	}
	
	/**
	 * 
	 * @return interest value from text field
	 */
	public double getInterestVal()
	{
		return getDoubleFromTextField(interestTF);
	}
	
	/**
	 * 
	 * @return lenth value from text field
	 */
	public int getLengthVal()
	{
		return getIntegerFromTextField(lengthTF);
	}	
	
	/**
	 * 
	 * @return fixation value from text field
	 */
	public int getFixationVal()
	{
		return getIntegerFromTextField(fixationTF);
	}	
	
	/**
	 * 
	 * @return value of premature payment from text field.
	 */
	public double getMonthAmountForPrematurePayment()
	{
		if (monthAmountForPrematurePaymentTF.isEnabled())
		{
			return getDoubleFromTextField(monthAmountForPrematurePaymentTF);
		}
		// return 0 if premature payment checkbox is disabled
		else
		{
			return 0;
		}
	}
	
	/**
	 * 
	 * @return value of period from text field
	 */
	public int getPeriodOfPrematurePaymentVal()
	{
		return getIntegerFromTextField(periodOfPrematurePaymentTF);
	}	
	
	/**
	 * 
	 * @return value of pealty from text field
	 */
	public double getPenaltyPercentageForPrematurePaymentInTheMiddleOfFixationVal()
	{
		return getDoubleFromTextField(penaltyPercentageForPrematurePaymentInTheMiddleOfFixationTF);
	}	
	
	/**
	 * 
	 * @return left panel
	 */
	private JPanel getPanel1()
	{
		if (panel1 == null)
		{
			panel1 = new JPanel();
			double sizePanel1[][] = 
	        {{10, 130, 145, 10},
			{20, 25, 22, 25, 23, 25, 20}};
			panel1.setLayout(new TableLayout(sizePanel1));
			panel1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							
			LocalizationManager pm = LocalizationManager.getInstance();
			panel1.add(initLabelWithTooltip(pm.getMessageForKey(Text.LOAN_LABEL), pm.getMessageForKey(Text.LOAN_TOOLTIP)), "1,1,0,0");
			loanTF = new JTextField("1200000.0");
			panel1.add(loanTF, "2,1,0,0");
			
			panel1.add(initLabelWithTooltip(pm.getMessageForKey(Text.INTEREST_LABEL), pm.getMessageForKey(Text.INTEREST_TOOLTIP)), "1,3,0,0");
			interestTF = new JTextField("4.0");
			panel1.add(interestTF, "2,3,0,0");
			
			panel1.add(initLabelWithTooltip(pm.getMessageForKey(Text.LOAN_LENGTH_LABEL), pm.getMessageForKey(Text.LOAN_LENGTH_TOOLTIP)), "1,5,0,0");
			lengthTF = new JTextField("30");
			panel1.add(lengthTF, "2,5,0,0");			
		}
		return panel1;
	}
	
	/**
	 * 
	 * @return middle panel
	 */
	private JPanel getPanel2()
	{
		if (panel2 == null)
		{
			panel2 = new JPanel();
			double sizePanel2[][] = 
	        {{10, 130, 145, 10},
			{20, 25, 22, 25, 23, 25, 20}};
			panel2.setLayout(new TableLayout(sizePanel2));
			panel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			
			LocalizationManager pm = LocalizationManager.getInstance();
			
			JCheckBox box1 = new JCheckBox("", false);	
			box1.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent ae)
	        	{
	        		enableOrDisablePrematurePaymentComponents(((JCheckBox)ae.getSource()).isSelected());	        		
	        	}
	        });			
			panel2.add(initLabelWithTooltip(pm.getMessageForKey(Text.PREMATURE_PAYMENT_LABEL), pm.getMessageForKey(Text.PREMATURE_PAYMENT_TOOLTIP)), "1,1,0,0");
			panel2.add(box1, "2,1,0,0");
			
			fixationLB = initLabelWithTooltip(pm.getMessageForKey(Text.FIXATION_LENGTH_LABEL), pm.getMessageForKey(Text.FIXATION_LENGTH_TOOLTIP)); 
			panel2.add(fixationLB, "1,3,0,0");
			fixationTF = new JTextField("3");
			panel2.add(fixationTF, "2,3,0,0");
			
			monthAmountForPrematurePaymentLB = initLabelWithTooltip(pm.getMessageForKey(Text.MONTH_AMOUNT_FOR_PREMATURE_PAYMENT_LABEL), pm.getMessageForKey(Text.MONTH_AMOUNT_FOR_PREMATURE_PAYMENT_TOOLTIP)); 
			panel2.add(monthAmountForPrematurePaymentLB, "1,5,0,0");
			monthAmountForPrematurePaymentTF = new JTextField("0");
			panel2.add(monthAmountForPrematurePaymentTF, "2,5,0,0");					
		}
		return panel2;
	}	
	
	/**
	 * 
	 * @return right panel
	 */
	private JPanel getPanel3()
	{
		if (panel3 == null)
		{
			panel3 = new JPanel();
			double sizePanel3[][] = 
	        {{10, 130, 145, 10},
			{20, 25, 22, 25, 23, 25, 20}};
			panel3.setLayout(new TableLayout(sizePanel3));
			panel3.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));			
			
			LocalizationManager pm = LocalizationManager.getInstance();
			
			periodOfPrematurePaymentLB = initLabelWithTooltip(pm.getMessageForKey(Text.PERIOD_FOR_PREMATURE_PAYMENT_LABEL), pm.getMessageForKey(Text.PERIOD_FOR_PREMATURE_PAYMENT_TOOLTIP)); 
			panel3.add(periodOfPrematurePaymentLB, "1,1,0,0");
			periodOfPrematurePaymentTF = new JTextField("3");
			panel3.add(periodOfPrematurePaymentTF, "2,1,0,0");	
			
			penaltyPercentageForPrematurePaymentInTheMiddleOfFixationLB = initLabelWithTooltip(pm.getMessageForKey(Text.PENALE_LABEL), pm.getMessageForKey(Text.PENALE_TOOLTIP)); 
			panel3.add(penaltyPercentageForPrematurePaymentInTheMiddleOfFixationLB, "1,3,0,0");
			penaltyPercentageForPrematurePaymentInTheMiddleOfFixationTF = new JTextField("0.1");
			panel3.add(penaltyPercentageForPrematurePaymentInTheMiddleOfFixationTF, "2,3,0,0");			
//			
//			panel3.add(new JLabel("Neco:"), "1,5,0,0");
//			panel3.add(new JTextField("1"), "2,5,0,0");			
		}
		return panel3;
	}
	
	/**
	 * This is called when checkbox is pressed. It is used to enable or disable particular components in GUI.
	 * 
	 * @param selected
	 */
	private void enableOrDisablePrematurePaymentComponents(boolean selected)
	{
		fixationLB.setEnabled(selected);
		fixationTF.setEnabled(selected);
		monthAmountForPrematurePaymentLB.setEnabled(selected);
		monthAmountForPrematurePaymentTF.setEnabled(selected);
		periodOfPrematurePaymentLB.setEnabled(selected);
		periodOfPrematurePaymentTF.setEnabled(selected);
		penaltyPercentageForPrematurePaymentInTheMiddleOfFixationLB.setEnabled(selected);
		penaltyPercentageForPrematurePaymentInTheMiddleOfFixationTF.setEnabled(selected);
	}	
	
	/**
	 * Initialization of label
	 * @param text
	 * @param labelText
	 * @return
	 */
	private static JLabel initLabelWithTooltip(String text, String labelText)
	{
		JLabel label = new JLabel(text);
		label.setToolTipText(labelText);
		return label;
	}	
	
	/**
	 * Helper method for convert double value from text field
	 * @param textField
	 * @return
	 */
	private static double getDoubleFromTextField(JTextField textField)
	{
		String valStr = textField.getText();
		return Double.valueOf(valStr);
	}
	
	/**
	 * Helper method for convert int value from text field
	 * @param textField
	 * @return
	 */	
	private static int getIntegerFromTextField(JTextField textField)
	{
		String valStr = textField.getText();
		return Integer.valueOf(valStr);
	}		

}
