package cz.possoft.calculator;


/**
 * Immutable class for storing informations about one year during mortgage. Some values are passed to constructor
 * and the rest is calculated and can be obtained via getter methods.
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class BaseEntry 
{
	public static final int LENGTH_OF_FORMATTED_STRING = 20;
	
	// attributes from configuration.
	private final double startBalance;	
	private final int lengthOfPeriodInMonths;
	private final double interest;
	private final double monthPayment;
	
	// attributes from configuration, which are related to premature payment of amortization.
	private final int remainingLengthOfCurrentFixationInYears;
	private final double startBalanceForPrematurePayment;
	private final double monthPaymentForPrematurePayment;
	private final boolean payItThisYear;
	private final double penaltyPercentageForPrematurePaymentInTheMiddleOfFixation; 
	
	// Computed attributes
	private final double endBalance;
	private final double interestSum;
	private final double amortSum;
	private final double yearPayment;
	private final double endBalanceForPrematurePayment;
	
	/**
	 * Constructor which is not going to use premature payment.
	 * 
	 * @param startBalance
	 * @param interest
	 * @param monthPayment
	 */
	public BaseEntry(double startBalance, double interest, double monthPayment)
	{
		// 12 is default length of period
		this(startBalance, interest, monthPayment, 12);
	}
	
	/**
	 * Constructor which is not going to use premature payment.
	 * 
	 * @param startBalance
	 * @param interest
	 * @param monthPayment
	 * @param lengthOfPeriodInMonths
	 */
	public BaseEntry(double startBalance, double interest, double monthPayment, int lengthOfPeriodInMonths)
	{
		this(startBalance, interest, monthPayment, lengthOfPeriodInMonths, 3, 0.0, 0.0, false, 0.0);
	}
	
	/**
	 * Constructor with initialization all params.
	 * 
	 * @param startBalance
	 * @param interest
	 * @param monthPayment
	 * @param lengthOfPeriodInMonths
	 * @param remainingLengthOfCurrentFixationInYears
	 * @param startBalanceForPrematurePayment
	 * @param monthPaymentForPrematurePayment
	 * @param payItThisYear
	 * @param penaltyPercentageForPrematurePaymentInTheMiddleOfFixation
	 */
	public BaseEntry(double startBalance, double interest, double monthPayment, int lengthOfPeriodInMonths
			,int remainingLengthOfCurrentFixationInYears
			,double startBalanceForPrematurePayment
			,double monthPaymentForPrematurePayment
			,boolean payItThisYear
			,double penaltyPercentageForPrematurePaymentInTheMiddleOfFixation)
	{
		this.startBalance = startBalance;
		this.interest = interest;
		this.monthPayment = monthPayment;
		this.lengthOfPeriodInMonths = lengthOfPeriodInMonths;
		
		this.remainingLengthOfCurrentFixationInYears = remainingLengthOfCurrentFixationInYears;
		this.startBalanceForPrematurePayment = startBalanceForPrematurePayment;
		this.monthPaymentForPrematurePayment = monthPaymentForPrematurePayment;
		this.payItThisYear = payItThisYear;
		this.penaltyPercentageForPrematurePaymentInTheMiddleOfFixation = penaltyPercentageForPrematurePaymentInTheMiddleOfFixation;
		
		// Compute premature payment
		double prematurePaymentAmount = 0;
		double pom = monthPaymentForPrematurePayment * 12;
		if (payItThisYear)
		{
			prematurePaymentAmount = startBalanceForPrematurePayment + pom;
			// compute penalty for premature payment if it is not done at the end of fixation period
			if (remainingLengthOfCurrentFixationInYears != 1)
			{
				prematurePaymentAmount = prematurePaymentAmount * (1 - penaltyPercentageForPrematurePaymentInTheMiddleOfFixation);
			}
			this.endBalanceForPrematurePayment = 0;
		}
		else
		{
			this.endBalanceForPrematurePayment = startBalanceForPrematurePayment + pom;
		}
		
		interestSum = (startBalance / 100) * interest;
		
		// use helper variable for case that mortgage is going to be fully payed
		double yearPaymentPom = lengthOfPeriodInMonths * monthPayment + prematurePaymentAmount;
		double endBalancePom = startBalance + interestSum - yearPaymentPom;
		if (endBalancePom > 0)
		{
			yearPayment = yearPaymentPom;
			endBalance = endBalancePom;
		}
		// This means that mortgage is going to be fully payed this year.
		else
		{
			yearPayment = startBalance;
			endBalance = -0.01;
		}
		amortSum = startBalance - endBalance;					
	}

	/**
	 * 
	 * @return endBalance value
	 */
	public double getEndBalance() 
	{
		return endBalance;
	}

	/**
	 * 
	 * @return interestSum value
	 */
	public double getInterestSum() 
	{
		return interestSum;
	}

	/**
	 * 
	 * @return amortSum value
	 */
	public double getAmortSum() 
	{
		return amortSum;
	}

	/**
	 * 
	 * @return yearPayment value
	 */
	public double getYearPayment() 
	{
		return yearPayment;
	}
	
	/**
	 * 
	 * @return endBalanceForPrematurePAyment value
	 */
	public double getEndBalanceForPrematurePayment() 
	{
		return endBalanceForPrematurePayment;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING, startBalance) + "|" + CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING, interestSum) + "|" + CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING, amortSum) + "|" + CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING, yearPayment) + "|" + CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING, endBalance) + "|" + CalculatorFormatterUtils.getFormattedStringFromDouble(LENGTH_OF_FORMATTED_STRING - 3, (interestSum/(interestSum+amortSum))*100);
	}
	
}
