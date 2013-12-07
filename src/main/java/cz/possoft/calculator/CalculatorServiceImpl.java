package cz.possoft.calculator;

/**
 * Service for business logic operations.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 */
public class CalculatorServiceImpl 
{
	/**
	 * Helper method (TODO: remove because it's currently not used anywhere)
	 * 
	 * @param startBalance
	 * @param interest
	 * @param monthPayment
	 * @return next entry
	 */
	public BaseEntry getNextBaseEntry(double startBalance, double interest, double monthPayment)
	{
		BaseEntry nextYearEntry = new BaseEntry(startBalance, interest, monthPayment);
		return nextYearEntry;
	}
	
	/**
	 * Calculate month payment value from given parameters ('halving interval' algorithm is used for calculation)
	 * 
	 * @param startBalance
	 * @param interest
	 * @param numberOfYears
	 * @return calculated month payment
	 */
	public double calculateMonthPayment(double startBalance, double interest, int numberOfYears)
	{
		
		// 1) Set variation
		double maxVariation = 0.0000000005d;
		
		// 2) Guess the values for month payment. We need to guess upper border and low border.
		double monthPaymentUp = startBalance / 5 / 12;
		double monthPaymentDown = startBalance / 60 / 12;
		double currentMonthPayment = -1;
		
		// System.out.println("DOWN=" + monthPaymentDown + ", UP=" + monthPaymentUp);
		
		while ((monthPaymentUp - monthPaymentDown) > maxVariation)
		{
			// System.out.println("DOWN=" + monthPaymentDown + ", UP=" + monthPaymentUp);
			currentMonthPayment = (monthPaymentUp + monthPaymentDown) / 2;
			double currentVariation = getRemainingBalanceAfterXYears(startBalance, interest, numberOfYears, currentMonthPayment);
			if (currentVariation > 0)
			{
				monthPaymentDown = currentMonthPayment;
			}
			else if (currentVariation < 0)
			{
				monthPaymentUp = currentMonthPayment;
			}
			// There is only theoretical chance, that variation will be exactly 0 with respect to type "double". So last "else" is mainly for completeness.
			else 
			{
				return currentMonthPayment;
			}
		}
		return currentMonthPayment;
	}
	
	/**
	 * Helper method for calculating the balance after X years from given parameters.
	 * 
	 * @param startBalance
	 * @param interest
	 * @param numberOfYears
	 * @param monthPayment
	 * @return remaining balance
	 */
	private double getRemainingBalanceAfterXYears(double startBalance, double interest, int numberOfYears, double monthPayment)	
	{
		double balance = startBalance;
		for (int i=0 ; i<numberOfYears ; i++)
		{
			BaseEntry entry = new BaseEntry(balance, interest, monthPayment);			
			balance = entry.getEndBalance();
		}
		return balance;
	}	

}
