import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * COP3530: Project 1 - Array Searches and Sorts
 * <p>
 * Description: This project was designed to parse a CSV file based on records 
 * of each state including a 2019 report for each sate's population size, 
 * a 2020 report for median household income, and an early 2021 report for 
 * covid19 cases & casualties. These records are then placed into a state array with 
 * the various fields listed.
 * 
 * <p>
 * The program first prompts user for the name of requested file to which it will prompt the user to choose one of seven 
 * various actions. If any input does not meet the program's requirements, an error message will appear.
 * The actions included: print a state report, order states by name, order states by COVID-19 cases, 
 * order states by median household income, find and print a particular state's records, 
 * display a Spearman's Rho correlation for {Case Rate, Death Rate} X {MHI, VCR}. 
 * If any of these actions are no longer requested, then the user has the option to quit from the program. 
 *
 * @author Eyob Tekle
 * @version 2/3/2021
 */
public class Project1 {
	
	/**
	 * Main method prompts user to enter the name of a file, and if found will be further prompted 
	 * to pick one of seven choices. Print a report of all states, sort states by name, fatality rate, 
	 * or median household income, search a particular state, print states' Spearman's Rho correlation, 
	 * or quit the program.
	 * 
	 * @param arguments of the main method
	 * @return N/A
	 */
	public static void main(String[] args) 
	{
		System.out.println("Please type the name of the file you wish to open.");
		Scanner scan = new Scanner(System.in);	
		Scanner inFile = null;
		int arrSize = 50;
		boolean m = true;	
		
		while(m)
		{
			try
			{	
				//String file = scan.next();
				inFile = new Scanner(new File(scan.next()));
				inFile.nextLine();
				inFile.useDelimiter(",|\n");
				System.out.println("Successfully Opened");		
				m = false;
			 	
			}//End Try
			catch (FileNotFoundException e)
			{
				System.err.println("|-----File Not Found! Re-entry Required-----|\n");
			}//End Catch
		}//End While Loop
		
		String name, capitol, region; 
		int popul, c19Cases, c19Dths; 
		double medHsInc, vCrimeRt;
		double[] c19CFR = new double[arrSize];				
		double[] caseRate = new double[arrSize]; 
		double[] deathRate = new double[arrSize];
		
		State states[] = new State[50];
		State state = null;
		for(int i = 0; inFile.hasNext(); i++)
		{
			name = inFile.next();				capitol = inFile.next();
			region = inFile.next();	    		inFile.next();
			popul = inFile.nextInt();	    	c19Cases = inFile.nextInt();
			c19Dths = inFile.nextInt();  		medHsInc = inFile.nextInt();
			vCrimeRt = inFile.nextDouble();
			
			state = new State(name, capitol, region, popul, c19Cases, c19Dths, medHsInc, vCrimeRt);
			states[i] = state;
		}//End Loop;	
				
		int num = 0;				
		boolean nameSorter = false;
		
		do
		{	
			boolean n = true;	
			while(n)
			{
				try
				{
					System.out.printf("\nWould you like to: \n"
							+ "1. Print a States Report\n"
							+ "2. Sort by name\n"
							+ "3. Sort by COVID-19 Case Fatality Rate\n"
							+ "4. Sort by Median Household Income\n"
							+ "5. Find & Print a State for a given name\n"
							+ "6. Print Spearman's p correlation matrix\n"
							+ "7. Quit\n"
							+ "Enter your choice: ");		
					
					num = scan.nextInt();
					n = false;
				}//End Try
				catch(InputMismatchException e)
				{
					System.out.println("Input Not Accepted: Please Enter 1-7!");
					scan.next();
				}//End Catch
			}//End While Loop
			
			covidRates(states, c19CFR, caseRate, deathRate);
			
			switch (num)
			{
				case 1:
					stateReport(states, c19CFR, caseRate, deathRate);
					break;
					
				case 2:
					nameSorter(states, arrSize);					
					nameSorter = true;
					break;
					
				case 3:
					sortCovidCFR(states, c19CFR);
					nameSorter = false;
					break;
					
				case 4:
					sortMHI(states);
					nameSorter = false;
					break;
					
				case 5:
					System.out.printf("\nEnter State name: ");
					stateFinder(scan.next(), nameSorter, states, c19CFR, caseRate, deathRate);							
					break;
					
				case 6:
					spearmanRho(states, caseRate, deathRate);
					break;
					
				case 7:
					System.out.println("Goodbye!\n");
					System.exit(1);
					break;
					
				default:
					System.out.println("Input Not Accepted: Please Enter 1-7!");		
			}//End switch			
		}while(!(num == 7));//End Do While Loop
		
		scan.close();
		inFile.close();
	}//End Main Method
	
	
	/**
	 * This method calculates the COVID-19 fatality rate, case rate, and death rate 
	 * for each state from the number of cases deaths and population size.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @param covidFR: COVID-19 case fatality rate of each state.
	 * @param caseRate: COVID-19 case rate of each state.
	 * @param deathRate: COVID-19 death rate of each state.
	 * @return N/A
	 */
	public static void covidRates(State[] s, double[] c19CFR, double[] caseRate, double[] deathRate)
	{
		int pSample = 100000;
		
		for(int i = 0; i < c19CFR.length; i++)
		{
			c19CFR[i] = (double)s[i].getC19_Deaths()/s[i].getC19_Cases();
			caseRate[i] = (double)s[i].getC19_Cases() * pSample / s[i].getPopulation();
			deathRate[i] = (double)s[i].getC19_Deaths() * pSample / s[i].getPopulation();
		}//End loop
		
	}//End Method
	
	
	/**
	 * This method is used to print a a listed report of each state and their records.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @param covidFR: COVID-19 case fatality rate of each state.
	 * @param caseRate: COVID-19 case rate of each state.
	 * @param deathRate: COVID-19 death rate of each state.
	 * @return N/A
	 */
	public static void stateReport(State[] s, double[] covidFR, double[] caseRate, double[] deathRate)
	{
		String space = " ";
		int arrSize = covidFR.length;
		
		System.out.printf("\nName %19s MHI %12s VCR %12s CFR %14s Case Rate %9s Death Rate\n"
				+ "------------------------------------------------------------------------------------------------------------\n"
				, space, space, space, space, space);
		
		for(int i = 0; i < arrSize; i++)
		{
			System.out.printf("%-24s %d %16.1f %19.6f %17.2f %18.2f \n"
					, s[i].getName(), (int) s[i].getMHI(), s[i].getVCR(), covidFR[i], caseRate[i], deathRate[i]);
		}//End loop
	}//End Method
	
	
	/**
	 * This method sorts the states by their name.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @param arrSize: number of states included in the file.
	 * @return N/A
	 */
	public static void nameSorter(State[] s, int arrSize)
	{
		for (int outer = 0; outer < arrSize - 1; outer++ )
		{
			for(int inner = arrSize - 1; inner > outer; inner--)
			{
				if(s[inner].getName().compareTo(s[inner-1].getName()) < 0)
				{					
					State tempS = s[inner];
					s[inner] = s[inner-1];
					s[inner-1] = tempS;
					
				}//End If
			}//End Inner Loop
		}//End Outer Loop
		
		System.out.println("\nStates have been sorted by Name.");
	}//End Method
	
	
	/**
	 * This method sorts states by their COVID-19 fatality rate.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @param covidFR: COVID-19 case fatality rate of each state.
	 * @return N/A
	 */
	public static void sortCovidCFR(State[] s, double[] covidFR)
	{
		for (int outer = 0; outer < covidFR.length - 1; outer++ )
		{
			int lowest = outer;
			
			for(int inner = outer + 1; inner < covidFR.length; inner++)
			{
				if(covidFR[inner] < covidFR[lowest])
				{
					lowest = inner;
				}//End If				
			}//End Inner Loop	
			
			if(lowest != outer)
			{
				State tempS = s[lowest];
				s[lowest] = s[outer];
				s[outer] = tempS;
				
				Double tempD = covidFR[lowest];
				covidFR[lowest] = covidFR[outer];
				covidFR[outer] = tempD;												
	
			}//End If			
		}//End Outer Loop		
		
		System.out.println("\nStates have been sorted by CFR.");
	}//End Method
	
	
	/**
	 * This method sorts states by the median household income.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @return N/A
	 */
	public static void sortMHI(State[] s)
	{
		int inner;
		for(int outer = 1; outer < 50; outer++)
		{
			State tempN = s[outer];	
			
			inner = outer - 1;
			
			while(inner >= 0 && s[inner].getMHI() > s[outer].getMHI())
			{
				s[inner+1] = s[inner];
				s[inner+1].setMHI(s[inner].getMHI());			
				inner--;
			}//End While Loop
			
			s[inner+1] = tempN;
		}//End For Loop
		
		System.out.println("\nStates have been sorted by MHI.");
	}//End Method
	
	
	/**
	 * This method searches for the state requested by the user, and prints a report if state is found. 
	 * 
	 * @param searchKey: user input for state.
	 * @param nSort: checks to see if states have been sorted by name.
	 * @param s: state object calling the fields of each state.
	 * @param covidFR: COVID-19 case fatality rate of each state.
	 * @param caseRate: COVID-19 case rate of each state.
	 * @param deathRate: COVID-19 death rate of each state.
	 * @return N/A
	 */
	public static void stateFinder(String searchKey, boolean nSort, State[] s, double[] covidFR, double[] caseRate, double[] deathRate)
	{
		int arrSize = covidFR.length;
		int num = 0;
		
		if(nSort == true)
		{
			int lowBound = 0;
			int upBound = arrSize - 1;
			int mid;
			
			System.out.println("Using Binary Search...\n");
			
			while (lowBound <= upBound)
			{
				mid  = (lowBound + upBound)/2;
				if(s[mid].getName().equals(searchKey))
				{	
					num = mid;
					break;
				}//End If
				else if (s[mid].getName().compareTo(searchKey) > 0)
					upBound = mid - 1;
				else
					lowBound = mid + 1;
			}//End While Loop					
		}//End Outer If
		else
		{		
			int j = 0;
			
			System.out.println("Using Sequential Search...\n");
			
			while(j < arrSize)
			{
				if(s[j].getName().equals(searchKey))
					break;				
				j++;
			}//End While Loop				
			
			num = (j == 50) ? 0 : j;
		}//End Outer Else	
		
		if(!(s[num].getName().equals(searchKey)))
		{	
			System.out.printf("Sorry, but '" + searchKey + "' was not found. \n");
		}//End If
		else
		{
			String space = " ";
			System.out.printf("State Search Succesful\n"
					+ "Name: %10s%s\n" 
					+ "MHI: %11s%d\n"  
					+ "VCR: %11s%.1f\n" 
					+ "CFR: %11s%.6f\n" 
					+ "Case Rate: %5s%.2f\n" 
					+ "Death Rate: %4s%.2f\n"
					, space, s[num].getName(), space, (int)s[num].getMHI(), space, s[num].getVCR(), space, covidFR[num], space, caseRate[num], space, deathRate[num]);
		}//End Else
	}//End Method	
	
	/**
	 * This method displays the Spearman's Rho correlation between covid19 case rate & median household income, 
	 * covid19 case rate & violent crime rate, covid19 death rate & median household income, 
	 * and covid19 death rate & violent crime rate.
	 * 
	 * @param s: state object calling the fields of each state.
	 * @param caseRate: COVID-19 case rate of each state.
	 * @param deathRate: COVID-19 death rate of each state.
	 * @return N/A 
	 */
	public static void spearmanRho(State[] s, double[] caseRate, double[] deathRate)
	{
		int pSample = 100000;
		int arrSize = caseRate.length;
		int inner1, inner2, inner3, inner4;
		double rho1 = 0.0000, rho2 = 0.0, rho3 = 0.0, rho4 = 0.0;
		double[] tempMHI = new double[arrSize];
		double[] tempVCR = new double[arrSize];
		double[] medHI = new double[arrSize];
		double[] cRate = new double[arrSize];
		double[] dRate = new double[arrSize];
		double[] vRate = new double[arrSize];
		
		for(int j = 0; j < arrSize; j++)
		{
			tempMHI[j] = s[j].getMHI();
			medHI[j] = s[j].getMHI();
			tempVCR[j] = s[j].getVCR();
			vRate[j] = s[j].getVCR();
			cRate[j] = caseRate[j];
			dRate[j] = deathRate[j];
			
		}//End For Loop
		
		for(int outer = 1; outer < arrSize; outer++)
		{
			double tempInc = medHI[outer];
			double tempCsRt = cRate[outer];
			double tempDthRt = dRate[outer];			
			double tempCrime = vRate[outer];
			
			inner1 = inner2 = inner3 =  inner4 = outer - 1; 
			
			while (inner1 >= 0 && cRate[inner1] > tempCsRt)
			{
				cRate[inner1 + 1] = cRate[inner1];				
				inner1--;
			}//End While Loop		
			while (inner2 >= 0 && dRate[inner2] > tempDthRt)
			{
				dRate[inner2 + 1] = dRate[inner2];
				inner2--;
			}//End While Loop
			while (inner3 >= 0 && medHI[inner3] > tempInc)
			{
				medHI[inner3 + 1] = medHI[inner3];
				inner3--;
			}//End While Loop		
			while (inner4 >= 0 && vRate[inner4] > tempCrime)
			{
				vRate[inner4 + 1] = vRate[inner4];
				inner4--;
			}//End While Loop
			
			cRate[inner1 + 1] = tempCsRt;
			dRate[inner2 + 1] = tempDthRt;
			medHI[inner3 + 1] = tempInc;
			vRate[inner4 + 1] = tempCrime;
		}//End For Loop
		
		binaryRanking(caseRate, cRate);
		binaryRanking(deathRate, dRate);
		binaryRanking(tempMHI, medHI);
		binaryRanking(tempVCR, vRate);
		
		for(int i = 0; i < arrSize; i++)
		{
			rho1 += Math.pow(((caseRate[i]) - tempMHI[i]),2);			
			rho2 += Math.pow(((caseRate[i]) - tempVCR[i]),2);			
			rho3 += Math.pow(((deathRate[i]) - tempMHI[i]),2);			
			rho4 += Math.pow(((deathRate[i]) - tempVCR[i]),2);
		}//End For Loop
		
		double p = (arrSize * ((Math.pow(arrSize, 2) - 1)));
		
		rho1 = 1 - (6 * rho1 / p);
		rho2 = 1 - (6 * rho2 / p);
		rho3 = 1 - (6 * rho3 / p);
		rho4 = 1 - (6 * rho4 / p);
		
		System.out.printf("\n--------------------------------------------------\n"
				+ "|\t\t|\tMHI\t |\tVCR\t |\n"
				+ "--------------------------------------------------\n"
				+ "|   Case Rate   |      %.4f\t |      %.4f\t |\n"
				+ "--------------------------------------------------\n"
				+ "|   Death Rate  |      %.4f\t |      %.4f\t |\n"
				+ "--------------------------------------------------\n", rho1, rho2, rho3, rho4);
	}//End Method
	
	
	/**
	 * This method searches an an array of each states rating for several fields, 
	 * using two copied arrays, one unsorted and one sorted by smallest to largest value, 
	 * to which the method will then have the unsorted array look through the sorted for 
	 * its corresponding rating number and reassign the value in each index of the unsorted array 
	 * with the index number used to find the corresponding rating number
	 *  
	 * @param rate: takes in the ratings of states' fields
	 * @param sortedRate: state ratings listed in ascending order
	 */
	public static void binaryRanking(double[] rate, double[] sortedRate)
	{
		int arrSize = rate.length;
		
		for(int i = 0; i < arrSize; i++)
		{
			int lowBound = 0;
			int upBound = arrSize - 1;
			int mid;
			
			while (lowBound <= upBound)
			{
				mid = (lowBound + upBound)/2;
				if(sortedRate[mid] == rate[i])
				{
					rate[i] = mid+1;
					break;
				}
				else if (sortedRate[mid] > rate[i])
					upBound = mid - 1;
				else
					lowBound = mid + 1;
			}//End Inner Loop	
		}//End Outer Loop
	}//End Method	
}//End Class
