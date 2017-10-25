/**
 * @author Arshdeep Singh
 * @author Todd Robbins
 * model object for csv rows. 
 * 
 */
public class DeathsPerWeek {
	private int year, week, diseaseDeath, deathTotal, deathBaby,
	deathYouth, deathPrime, deathMiddle, deathOld;
	private String weekDate, region, state, city; 


	public DeathsPerWeek(int year, int week, String weekDate, String region,
			String state, String city, int diseaseDeath, int deathTotal,
			int deathBaby, int deathYouth, int deathPrime, int deathMiddle, int deathOld) {
		this.year = year;
		this.week = week;
		this.weekDate = weekDate;
		this.region = region;
		this.state = state;
		this.city = city;
		this.diseaseDeath = diseaseDeath;
		this.deathTotal = deathTotal;
		this.deathBaby = deathBaby;
		this.deathYouth = deathYouth;
		this.setDeathPrime(deathPrime);
		this.deathMiddle = deathMiddle;
		this.deathOld = deathOld;
	}
	
	
	public int getDeathTotal() {
		return deathTotal;
	}

	public void setDeathTotal(int deathTotal) {
		this.deathTotal = deathTotal;
	}

	public int getDeathBaby() {
		return deathBaby;
	}

	public void setDeathBaby(int deathBaby) {
		this.deathBaby = deathBaby;
	}

	public int getDeathYouth() {
		return deathYouth;
	}

	public void setDeathYouth(int deathYouth) {
		this.deathYouth = deathYouth;
	}

	public int getDeathMiddle() {
		return deathMiddle;
	}

	public void setDeathMiddle(int deathMiddle) {
		this.deathMiddle = deathMiddle;
	}

	public int getDeathOld() {
		return deathOld;
	}

	public void setDeathOld(int deathOld) {
		this.deathOld = deathOld;
	}

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDiseaseDeath() {
		return diseaseDeath;
	}

	public void setDiseaseDeath(int diseaseDeath) {
		this.diseaseDeath = diseaseDeath;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}


	public int getDeathPrime() {
		return deathPrime;
	}


	public void setDeathPrime(int deathPrime) {
		this.deathPrime = deathPrime;
	}
	
	

}
