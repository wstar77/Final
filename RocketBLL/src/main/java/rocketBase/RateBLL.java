package rocketBase;

import org.apache.poi.ss.formula.functions.*;
import java.util.ArrayList;
import exceptions.RateException;
import rocketData.LoanRequest;
import rocketDomain.RateDomainModel;
public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException
	{
		//TODO - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		
		
		//TODO - RocketBLL RateBLL.getRate
		//			obviously this should be changed to return the determined rate
		ArrayList<RateDomainModel> allRates = _RateDAL.getAllRates();
		
		double IRate = -1.0;
		
		RateDomainModel dmRate = null;
		
		for(RateDomainModel rate: allRates){
			if (rate.getiMinCreditScore()<=GivenCreditScore){
				IRate = rate.getdInterestRate();
				dmRate = rate;
			}
		}
		
		
		if (dmRate == null)){
			throw new RateException(dmRate);
		}
		
		else{
			return IRate;
		}
		
	}
		
		
	
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return FinanceLib.pmt(r/12, n, p, f, t);
	}
}
