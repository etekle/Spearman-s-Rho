/**
 * Description: This class collects values of each field 
 * and allows for the retrieval of said values, 
 * or to interchange values at any index.
 *
 * @author Eyob Tekle
 * @version 2/3/2021
 */
public class State 
{
	private String name;
	private String capitol;
	private String region;
	private int population;
	private int covid_19_Cases;
	private int covid_19_Deaths;
	private double medHouseIncome;
	private double violentCrimeRate;	
	
	//Constructor
	public State(String name, String capitol, String region, int popul, int c19Cases, int c19Dths, double medHsInc , double vCrimeRt)
	{
		this.name = name;
		this.capitol = capitol;
		this.region = region;
		this.population = popul;
		this.covid_19_Cases = c19Cases;
		this.covid_19_Deaths = c19Dths;
		this.medHouseIncome = medHsInc;
		this.violentCrimeRate = vCrimeRt;
	}
	
	//Setters
	/**
	 * Sets a state name
	 * 
	 * @param name for the state
	 * @return N/A
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets a state capitol
	 * 
	 * @param capitol name of a state
	 * @return N/A
	 */
	public void setCapitol(String capitol) {
		this.capitol = capitol;
	}

	/**
	 * Sets a state region
	 * 
	 * @param region name in a state
	 * @return N/A
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Sets state population size
	 * 
	 * @param population the population to set
	 * @return N/A
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * Sets number of covid19 cases
	 * 
	 * @param number of reported covid_19_Cases
	 * @return N/A
	 */
	public void setC19_Cases(int covid_19_Cases) {
		this.covid_19_Cases = covid_19_Cases;
	}

	/**
	 * Sets number of covid19 deaths
	 * 
	 * @param number of reported covid_19_Deaths
	 * @return N/A
	 */
	public void setC19_Deaths(int covid_19_Deaths) {
		this.covid_19_Deaths = covid_19_Deaths;
	}

	/**
	 * Sets median house income
	 * 
	 * @param Amount of median household Income
	 * @return N/A
	 */
	public void setMHI(double medianHouseIncome) {
		this.medHouseIncome = medianHouseIncome;
	}

	/**
	 * Sets violent crime rate
	 * 
	 * @param violentCrimeRate the violentCrimeRate to set
	 * @return N/A
	 */
	public void setVCR(double violentCrimeRate) {
		this.violentCrimeRate = violentCrimeRate;
	}
	
	
	//Getters
	/**
	 * Retrieves state name
	 * 
	 * @param N/A
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retrieves state capitol
	 * 
	 * @param N/A
	 * @return the capitol
	 */
	public String getCapitol() {
		return capitol;
	}

	/**
	 * Retrieves state region
	 * 
	 * @param N/A
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Retrieves state population
	 * 
	 * @param N/A
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * Retrieves number of reported covid19 cases
	 * 
	 * @param N/A
	 * @return the covid_19_Cases
	 */
	public int getC19_Cases() {
		return covid_19_Cases;
	}

	/**
	 * Retrieves number of reported covid19 cases
	 * 
	 * @param N/A
	 * @return the covid_19_Deaths
	 */
	public int getC19_Deaths() {
		return covid_19_Deaths;
	}

	/**
	 * Retrieves state median Household Income
	 * 
	 * @param N/A
	 * @return the medianHouseIncome
	 */
	public double getMHI() {
		return medHouseIncome;
	}

	/**
	 * Retrieves state violent crime rate
	 * 
	 * @param N/A
	 * @return the violentCrimeRate
	 */
	public double getVCR() {
		return violentCrimeRate;
	}

	/**
	 * Overrides the toString function to print out a state objects value
	 * for a given index
	 * 
	 * @param N/A
	 * @return String value
	 */
	@Override
	public String toString() {
		
		return "State Name: " + name + "\nCapitol: " + capitol + "\nRegion: " + region + "\nPopulation: " + population
				+ "\nCOVID-19 Cases: " + covid_19_Cases + "\nCOVID-19 Deaths: " + covid_19_Deaths + "\nMedian Household Income: "
				+ medHouseIncome + "\nViolent Crime Rate: " + violentCrimeRate;
	}//End Method
}//End of Class

