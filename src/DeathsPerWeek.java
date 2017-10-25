/**
 * @author Arshdeep Singh
 * @author Todd Robbins
 * model object for csv rows. 
 * 
 */
public class DeathsPerWeek {
	private int year, week, diseaseDeath, DeathTotal, deathBaby,
	deathYouth, deathMiddle, deathOld;
	private String weekDate, region, state, city; 

	/**
	 * 
	 */
	public DeathsPerWeek() {
		// TODO Auto-generated constructor stub
	}

	public DeathsPerWeek(int year, int week, String weekDate, String region,
			String state, String city, int diseaseDeath, int DeathTotal,
			int deathBaby, int deathYouth, int deathMiddle, int deathOld) {
	}

}
