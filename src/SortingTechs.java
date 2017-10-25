import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Arshdeep Singh
 * @author Todd Robbins
 * Simulation of sorting techniques HOMEWORK
 * 
 */
public class SortingTechs {

	private static Scanner scan;

	public static void main(String[] args) throws IOException{
		
		/*
		 * NOTE: csv files have the following column in this order:
		 * 
		 * Data Type: |Year| Week(of year) | Week(End Date) | REGION | State | City | Pneumonia/Influenza Deaths | All Deaths(Total) | age<1 (deaths) |age 1-24|age 25-44|age 45-64|age 65+|
		 * Index:     | 0  |      1        |       2        |    3   |    4  |    5 |             6              |        7          |      8         |    9   |    10   |    11   |   12  |  
		 *
		 */
		
		System.out.println("select one of the following options:"); 
		System.out.println("1: src/BostonDeaths.csv"); 
		System.out.println("2: src/TacomaDeaths.csv"); 
		System.out.println("3: src/SortinglandDeaths.csv"); 
		System.out.println("4: src/ReverselandDeaths.csv"); 
		System.out.println("5: src/WashDeath.csv"); 
		System.out.println("Enter numeric Value:"); 
		
		String csvFile = ""; 
		scan = new Scanner(System.in); 
		int size = 0; 
		int selection = scan.nextInt(); 
		if (selection == 1) {
			csvFile = "src/BostonDeaths.csv";
			size = 522; 
		} else if (selection == 2) {
			csvFile = "src/TacomaDeaths.csv";
			size = 522; 
		} else if (selection == 3) {
			csvFile = "src/SortinglandDeaths.csv";
			size = 2088; 
		} else if (selection == 4) {
			csvFile = "src/ReverselandDeaths.csv";
			size = 2088; 
		} else if (selection == 5) {
			csvFile = "src/WashDeath.csv";
			size = 8558; 
		} else {
			System.out.println("Not allowed program terminate");
			System.exit(0);
		}

		DeathsPerWeek[] deathNumbers = new DeathsPerWeek[size]; // <- change type to deaths per week
		
		/*
		 * read csv data into array
		 */
		readIn(csvFile, deathNumbers); //<- read in all rows
		

		//TODO call each of the 4 sorting method 4 times and pass in a copy of one of the array objects each time
//		firstSortingMethod(deathNumbers.clone(), size); // <- change as needed
//		secondSortingMethod(deathNumbers.clone(), size); // <- change as needed
//		thirdSortingMethod(deathNumbers.clone(), size); // <- change as needed
//		fourthSortingMethod(deathNumbers.clone(), size); // <- change as needed
//		fifthSortingMethod(deathNumbers.clone(), size); // <- change as needed
		System.out.println();
		
	
	}
	
	/**
	 * read in the csv file and store the values in our DeathsPerWeek Object.
	 * @param theCSVFile string value that tells the program where the csv file is stored.
	 * @param deathList
	 */
	private static void readIn(String theCSVFile, DeathsPerWeek[] deathList) {
        int DeathCOUNT = 0;
        
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(theCSVFile));
            String[] deathInfo;

            while ((line = br.readLine()) != null) {
            		
                deathInfo = line.split(cvsSplitBy); // use comma as separator
                DeathsPerWeek deathObject = new DeathsPerWeek(Integer.parseInt(deathInfo[0]), Integer.parseInt(deathInfo[1]), 
                												deathInfo[2], deathInfo[3], deathInfo[4], deathInfo[5],
                												intParseNullCheck(deathInfo[6]), intParseNullCheck(deathInfo[7]),
                												intParseNullCheck(deathInfo[8]), intParseNullCheck(deathInfo[9]),
                												intParseNullCheck(deathInfo[10]), intParseNullCheck(deathInfo[11]),
                												intParseNullCheck(deathInfo[12]));
                
                
                
                //System.out.println("All Boston Deaths [week= " + deathInfo[2] + " , deaths=" + deathInfo[7] + "]");
                //deathList[DeathCOUNT] = Integer.parseInt(deathInfo[7]);
                deathList[DeathCOUNT] = deathObject;  
                
                DeathCOUNT ++; //increase count for building array 1 piece at a time
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
	
	/**
	 * prints the array to the console.
	 * @param array
	 * @return
	 */
	private static String arrayPrinter(int[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s += array[i] + " ";
		}
		return s;
	}
	
	/**
	 * Check if the data column is empty. 
	 * Doing this because Integer.parseInt() on a null value would cause an error.
	 * @param num is the value in one of the columns
	 * @return if column is null or an empty string then return 0, else return the value in the column.
	 */
	private static int intParseNullCheck(String num) {
		if (num == null || num.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(num);
		}
	}
	
	/**
	 * FIRST METHOD OF SORTING
	 * 
	 * implements Insertion sort 
	 * start timer sort the first array end timer. save that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void firstSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Insertion Sort----------");
		//System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); //start timer
		
		//call a helper method to do the sorting here. OR just do it here
		int a = 1;
		while (a < theArraySize) {
			int b = a;
			while ((b > 0) && (theArray[b-1] > theArray[b])) {
				int holder = theArray[b-1];
				theArray[b-1] = theArray[b];
				theArray[b] = holder;
				b--; //move b back down one position in the array so the previous spot can be checked 
			}
			a++; //increment a so that it points one farther down the array.
			//System.out.println(arrayPrinter(theArray));
			
		}
		
		long endTimer = System.currentTimeMillis(); //end timer
		long finalTime = endTimer - startTimer;
		
		System.out.println("It took " + finalTime + " ms to finish first sorting algorithm.");
		//System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}
	/**
	 * SECOND METHOD OF SORTING
	 * 
	 * implements selection sort 
	 * start timer sort the first array end timer. save that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void secondSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Selection Sort----------");
		//System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); //start timer
		
		for (int a = 0; a < theArraySize - 1; a++)
        {
            int index = a;
            for (int b = a + 1; b < theArraySize; b++)
                if (theArray[b] < theArray[index]) 
                    index = b;
      
            int smaller = theArray[index];  
            theArray[index] = theArray[a];
            theArray[a] = smaller;
        }
		
		long endTimer = System.currentTimeMillis(); //end timer
		long finalTime = endTimer - startTimer;
		
		System.out.println("It took " + finalTime + " ms to finish 2nd sorting algorithm.");
		//System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}
	
	/**
	 * THIRD METHOD OF SORTING
	 * 
	 * implements bubble sort 
	 * start timer sort the first array end timer. save that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void thirdSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Bubble Sort----------");
		//System.out.println("---------------------sorting:   " + arrayPrinter(theArray));

		long startTimer = System.currentTimeMillis(); // start timer

		for (int a = 0; a < theArraySize; a++) {
			for (int b = a + 1; b < theArraySize; b++) {
				if (theArray[a] > theArray[b]) {
					int holder = theArray[a];
					theArray[a] = theArray[b];
					theArray[b] = holder;
				}
			}
		}

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish third sorting algorithm.");
		//System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}
		
	/**
	 * FOURTH METHOD OF SORTING
	 * 
	 * 
	 * implements merge sort 
	 * start timer sort the first array end timer. save that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void fourthSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Merge Sort----------");
		//System.out.println("---------------------sorting:   " + arrayPrinter(theArray));

		long startTimer = System.currentTimeMillis(); // start timer
		
		int a = 0; // starting index
		int b = theArraySize - 1; // ending index [0-99] 99 = 100(size) - 1
		
		divideConquer(theArray, a, b);

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish fourth sorting algorithm.");
		//System.out.println("---------------------sorted:   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}
	
	/**
	 * FOURTH METHOD OF SORTING
	 * 
	 * 
	 * the first part of merge sort, divides up the method n/2 -> n/4 ...
	 * then merges it together (Conquers)
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void divideConquer(int[] theArray, int a, int b) {
        if (a < b) {
            
            int divide = (a+b)/2;

            divideConquer(theArray, a, divide);
            divideConquer(theArray, divide+1, b);

            conquer(theArray, a, divide, b);
        }
	}
	
	/**
	 * FOURTH METHOD OF SORTING
	 * 
	 * 
	 * merges the divided up arrays into a sorted one
	 * 
	 * @param theArray
	 * @param leftArray
	 * @param rightArray
	 */
	private static void conquer(int[] theArray, int a, int divide, int b) {
		/*
		 * size of sub arrays (stored for use later)
		 */
		int left = divide - a + 1;
		int right = b - divide;

		int leftArray[] = new int[left]; // left side 
		int rightArray[] = new int[right]; // right side

		/*
		 * populate left side 
		 */
		for (int i = 0; i < left; ++i) {
			leftArray[i] = theArray[a + i];
		}

		/*
		 * populate right side 
		 */
		for (int j = 0; j < right; ++j) {
			rightArray[j] = theArray[divide + 1 + j];
		}

		int i = 0;
		int j = 0;
		int k = a; // initial index 

		while (i < left && j < right) {
			if (leftArray[i] <= rightArray[j]) {
				theArray[k] = leftArray[i];
				i++;
			} else {
				theArray[k] = rightArray[j];
				j++;
			}
			k++;
		}

		/* 
		 * add all remaining elements (leftArray) 
		 */
		while (i < left) {
			theArray[k] = leftArray[i];
			i++;
			k++;
		}

		/* 
		 * add all remaining elements (rightArray) 
		 */
		while (j < right) {
			theArray[k] = rightArray[j];
			j++;
			k++;
		}
	}

	/**
	 * FIFTH METHOD OF SORTING
	 * 
	 * 
	 * implements quick sort start timer sort the first array end timer. save
	 * that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void fifthSortingMethod(int[] theArray, int theArraySize) {
		System.out.println("------Quick Sort----------");
		//System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); // start timer
		int a = 0; // starting index
		int b = theArraySize - 1; // ending index [0-99] 99 = 100(size) - 1

		quickSort(theArray, a, b);

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish fifth sorting algorithm.");
		//System.out.println("---------------------sorted:   " + arrayPrinter(theArray));
		System.out.println("-------------------------------------------");
	}	
	
	/**
	 * 
	 * keeps swapping i and j around the pivot (partition) until sorted
	 * 
	 * 
	 * @param theArray
	 * @param a
	 * @param b
	 */
	private static void quickSort(int[] theArray, int i, int j) {
		/*
		 * changed int i = a; int j = b; for personal preference
		 */

		if (i < j) {

			int pivot = partition(theArray, i, j); // part the red sea (so to speak)

			quickSort(theArray, i, pivot - 1);
			quickSort(theArray, pivot + 1, j);
		}
	}

	/**
	 * 
	 * Places the pivot in its correct position.
	 * 
	 * @param theArray
	 * @param a
	 * @param b
	 * @return
	 */
	private static int partition(int theArray[], int a, int b) {
		int pivot = theArray[b];
		int i = (a - 1);
		for (int j = a; j < b; j++) {
			if (theArray[j] <= pivot) {
				i++;

				int holder = theArray[i];
				theArray[i] = theArray[j];
				theArray[j] = holder;
			}
		}
		int holder = theArray[i + 1];
		theArray[i + 1] = theArray[b];
		theArray[b] = holder;

		return i + 1;
	}
}
