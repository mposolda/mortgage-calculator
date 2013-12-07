package cz.possoft.calculator.runner.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.possoft.calculator.BaseEntry;
import cz.possoft.calculator.CalculatorFormatterUtils;
import cz.possoft.calculator.CalculatorServiceImpl;

/**
 * Used for running console application with the calculator.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class ConsoleRunner 
{
	
	public static void main(String[] args) throws IOException
	{
		// read constants from console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Obnos uveru (pujcky) v CZK: ");
		double START_BALANCE = Double.valueOf(reader.readLine()); 
		System.out.print("Urok (v procentech): ");
		double INTEREST_PERCENTAGE = Double.valueOf(reader.readLine()); 
		System.out.print("Pocet roku na splaceni: ");
		int YEARS = Integer.parseInt(reader.readLine()); 
		
		// create calculator and count it
		CalculatorServiceImpl calculator = new CalculatorServiceImpl();
		
		// Calculate number of month and year payment
		double monthPayment = calculator.calculateMonthPayment(START_BALANCE, INTEREST_PERCENTAGE , YEARS);
		System.out.println("Mesicni splatka: " + CalculatorFormatterUtils.getFormattedStringFromDouble(12, monthPayment) + ", Rocni splatka: " + CalculatorFormatterUtils.getFormattedStringFromDouble(12, (monthPayment * 12)));
		
		// init variable
		double balance = START_BALANCE;
		double interestSumCZK = 0;
		double amortSumaCZK = 0;
		double sumCZK = 0;
		
		System.out.println("  Rok |             startB |               urok |               umor |         Rocni spl. |         endBalance | Procento uroku");
		for (int i=0 ; i<YEARS ; i++)
		{
			BaseEntry entry = new BaseEntry(balance, INTEREST_PERCENTAGE , monthPayment);
			System.out.println(CalculatorFormatterUtils.formatStringAccordingToLength(6, String.valueOf(i + 1)) + "|" + entry);
			
			interestSumCZK += entry.getInterestSum();
			amortSumaCZK += entry.getAmortSum();
			sumCZK     += entry.getYearPayment();
			balance = entry.getEndBalance();
		}
		
		System.out.println("                           |" 
				+ CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, interestSumCZK) + "|"
				+ CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, amortSumaCZK) + "|"
				+ CalculatorFormatterUtils.getFormattedStringFromDouble(BaseEntry.LENGTH_OF_FORMATTED_STRING, sumCZK) + "|"		
		 );
						
	}	

}
