import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Arshdeep Singh
 * Simulation of sorting techniques HOMEWORK
 * 
 */
public class SortingTechs {

	public static void main(String[] args) throws IOException{
		
		
		//NOTE csv files have the following column in this order:
		//Year,WEEK,Week Ending Date,REGION,State,City,Pneumonia and Influenza Deaths,All Deaths,<1 year (all cause deaths),1-24 years (all cause deaths),25-44 years,45-64 years (all cause deaths),65+ years (all cause deaths)
		//0    1    2                3      4     5    6                              7          8                          9                             10                                         11
		
		String csvFile = "src/BostonDeaths.csv";
		String csvFile2 = "src/TacomaDeaths.csv";
		
		//TODO read data sets 1,2,3,4 into 4 different array objects 
		int[] bostonAllDeathNumbers = new int[105]; //105 weeks of data.
		int[] tacomaFluDeathNumbers = new int[105]; //105 weeks of data.
		
		//read data into array
		readIn(csvFile, bostonAllDeathNumbers);
		readIn(csvFile2, tacomaFluDeathNumbers);
		
		//System.out.println("last" + bostonAllDeathNumbers[104]); //test to make sure the last value is 161
		//System.out.println("last" + tacomaFluDeathNumbers[104]); //test to make sure the last value is 11
		
		//TODO call each of the 4 sorting method 4 times and pass in a copy of one of the array objects each time
		firstSortingMethod(bostonAllDeathNumbers, 105);
		firstSortingMethod(tacomaFluDeathNumbers, 105);
		

		
	
	}
	
	/**
	 * 
	 * @param theCSVFile string value that tells the program where the csv file is stored.
	 * @param deathList
	 */
	private static void readIn(String theCSVFile, int[] deathList) {
        int bostonAllDeathCOUNT = 0;
        
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(theCSVFile));
            String[] deathInfo;

            while ((line = br.readLine()) != null) {
                deathInfo = line.split(cvsSplitBy); // use comma as separator
                
                //System.out.println("All Boston Deaths [week= " + deathInfo[2] + " , deaths=" + deathInfo[7] + "]");
                deathList[bostonAllDeathCOUNT] = Integer.parseInt(deathInfo[7]);
                bostonAllDeathCOUNT ++; //increase count for building array 1 piece at a time
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	
	private static String arrayPrinter(int[] a) {
		String s = "";
		for (int i = 0; i < a.length; i++) {
			s += a[i] + " ";
		}
		return s;
	}
	
	
	
	
	//FIRST METHOD OF SORTING
	//start timer
	//sort the first array
	//end timer. save that time into a file.
	private static void firstSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Insertion Sort----------");
		System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); //start timer
		
		//call a helper method to do the sorting here. OR just do it here
		int a = 1;
		while (a < theArraySize) {
			int b = a;
			while ((b > 0) && (theArray[a-1] > theArray[b])) {
				int holder = theArray[a-1];
				theArray[a-1] = theArray[b];
				theArray[b] = holder;
				b--; //move b back down one position in the array so the previous spot can be checked 
			}
			a++; //increment a so that it points one farther down the array.
			System.out.println(a);
		}
		
		long endTimer = System.currentTimeMillis(); //end timer
		long finalTime = endTimer - startTimer;
		
		System.out.println("It took " + finalTime + " ms to finish first sorting algorithm.");
		System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}
	
	 
	
	
	
	//SECOND METHOD OF SORTING
	//start timer
	//sort the first array
	//end timer. save that time into a file.
	
	
	//THIRD METHOD OF SORTING
	//start timer
	//sort the first array
	//end timer. save that time into a file.
	
	
	//FOURTH METHOD OF SORTING
	//start timer
	//sort the first array
	//end timer. save that time into a file.
	
	
	//FIFTH METHOD OF SORTING
	//start timer
	//sort the first array
	//end timer. save that time into a file.
	

}
