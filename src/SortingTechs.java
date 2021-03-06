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
		System.out.println("Enter one of the displayed numeric Value:"); 
		
		/**
		 * allow user to select data to be sorted
		 */
		String csvFile = ""; 
		scan = new Scanner(System.in); 
		int size = 0; 
		while(!scan.hasNextInt()) {
		    scan.next();
		}
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
		
		
		/**
		 * allow user to change data size within range 
		 * (needed for report)
		 */
		int temp;
		boolean good = false;
		do
		{
			System.out.println("enter the amount of data to be sorted"); 
			System.out.println("must be in range of 1 to "+size+":"); 
			temp = scan.nextInt();
			if(temp > 1 && temp <=  size) {
				good = true;
			}
			else
				System.out.println("value is not valid");
		} while (!good);
		size = temp;
		

		DeathsPerWeek[] deathNumbers = new DeathsPerWeek[size]; // <- change type to deaths per week
		
		/*
		 * read csv data into array
		 */
		readIn(csvFile, deathNumbers, size); //<- read in all rows
		

		/*
		 * all methods sort based off of total deaths "deathTotal"
		 * 1
		 */
		firstSortingMethod(deathNumbers.clone(), size);
		secondSortingMethod(deathNumbers.clone(), size);
		thirdSortingMethod(deathNumbers.clone(), size); 
		fourthSortingMethod(deathNumbers.clone(), size); 
		fifthSortingMethod(deathNumbers.clone(), size);
		System.out.println();
		
	
	}
	
	/**
	 * read in the csv file and store the values in our DeathsPerWeek Object.
	 * @param theCSVFile string value that tells the program where the csv file is stored.
	 * @param deathList
	 * @param size
	 */
	private static void readIn(String theCSVFile, DeathsPerWeek[] deathList, int size) {
        int DeathCOUNT = 0;
        
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(theCSVFile));
            String[] deathInfo;

            while (((line = br.readLine()) != null) && (DeathCOUNT < size)) { //stop when we have the desired data size
            		
                deathInfo = line.split(cvsSplitBy); // use comma as separator
                DeathsPerWeek deathObject = new DeathsPerWeek(Integer.parseInt(deathInfo[0]), Integer.parseInt(deathInfo[1]), 
                												deathInfo[2], deathInfo[3], deathInfo[4], deathInfo[5],
                												intParseNullCheck(deathInfo[6]), intParseNullCheck(deathInfo[7]),
                												intParseNullCheck(deathInfo[8]), intParseNullCheck(deathInfo[9]),
                												intParseNullCheck(deathInfo[10]), intParseNullCheck(deathInfo[11]),
                												intParseNullCheck(deathInfo[12]));

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
	private static String arrayPrinter(DeathsPerWeek[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s += array[i].getDeathTotal() + " ";
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
	 * FIRST METHOD OF SORTINGa
	 * 
	 * implements Insertion sort 
	 * start timer sort the first array end timer. save that time into a file.
	 * 
	 * @param theArray
	 * @param theArraySize
	 */
	private static void firstSortingMethod(DeathsPerWeek[] theArray, int theArraySize) {
		System.out.println("------Insertion Sort----------");
	//	System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); //start timer
		
		//call a helper method to do the sorting here. OR just do it here
		int a = 1;
		while (a < theArraySize) {
			int b = a;
			while ((b > 0) && (theArray[b-1].getDeathTotal() > theArray[b].getDeathTotal())) {
				DeathsPerWeek holder = theArray[b-1];
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
	//	System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
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
	private static void secondSortingMethod(DeathsPerWeek[] theArray, int theArraySize) {
		System.out.println("------Selection Sort----------");
//		System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); //start timer
		
		for (int a = 0; a < theArraySize - 1; a++)
        {
            int index = a;
            for (int b = a + 1; b < theArraySize; b++)
                if (theArray[b].getDeathTotal() < theArray[index].getDeathTotal()) 
                    index = b;
      
            DeathsPerWeek smaller = theArray[index];  
            theArray[index] = theArray[a];
            theArray[a] = smaller;
        }
		
		long endTimer = System.currentTimeMillis(); //end timer
		long finalTime = endTimer - startTimer;
		
		System.out.println("It took " + finalTime + " ms to finish 2nd sorting algorithm.");
//		System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
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
	private static void thirdSortingMethod(DeathsPerWeek[] theArray, int theArraySize) {
		System.out.println("------Bubble Sort----------");
//		System.out.println("---------------------sorting:   " + arrayPrinter(theArray));

		long startTimer = System.currentTimeMillis(); // start timer

		for (int a = 0; a < theArraySize; a++) {
			for (int b = a + 1; b < theArraySize; b++) {
				if (theArray[a].getDeathTotal() > theArray[b].getDeathTotal()) {
					DeathsPerWeek holder = theArray[a];
					theArray[a] = theArray[b];
					theArray[b] = holder;
				}
			}
		}

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish third sorting algorithm.");
//		System.out.println("---------------------sorted :   " + arrayPrinter(theArray));
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
	private static void fourthSortingMethod(DeathsPerWeek[] theArray, int theArraySize) {
		System.out.println("------Merge Sort----------");
//		System.out.println("---------------------sorting:   " + arrayPrinter(theArray));

		long startTimer = System.currentTimeMillis(); // start timer
		
		int a = 0; // starting index
		int b = theArraySize - 1; // ending index [0-99] 99 = 100(size) - 1
		
		/*
		 * sorting done in helper method for recursion 
		 */
		divideConquer(theArray, a, b);

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish fourth sorting algorithm.");
//		System.out.println("---------------------sorted:   " + arrayPrinter(theArray));
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
	private static void divideConquer(DeathsPerWeek[] theArray, int a, int b) {
        if (a < b) {
            
            int divide = (a+b)/2;

            divideConquer(theArray, a, divide);
            divideConquer(theArray, divide+1, b);

            conquer(theArray, a, b, divide);
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
	private static void conquer(DeathsPerWeek[] theArray, int a, int b, int divide) {
		
		/*
		 * create left array
		 */
		DeathsPerWeek leftArray[] = new DeathsPerWeek[divide - a + 1]; // left side 
		for (int i = 0; i < leftArray.length; i++) {
			leftArray[i] = theArray[a + i];
		}
		
		/*
		 * create right array
		 */
		DeathsPerWeek rightArray[] = new DeathsPerWeek[b - divide]; // right side
		for (int j = 0; j < rightArray.length; j++) {
			rightArray[j] = theArray[divide + 1 + j];
		}
		
		int i = 0;
		int j = 0;
		int k = a; // indexes for sorting  
		/*
		 * compare the arrays values and merge in sorted order
		 */
		while (i < leftArray.length && j < rightArray.length) {
			if (leftArray[i].getDeathTotal() <= rightArray[j].getDeathTotal()) {
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
		while (i < leftArray.length) {
			theArray[k] = leftArray[i];
			i++;
			k++;
		}

		/* 
		 * add all remaining elements (rightArray) 
		 */
		while (j < rightArray.length) {
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
	private static void fifthSortingMethod(DeathsPerWeek[] theArray, int theArraySize) {
		System.out.println("------Quick Sort----------");
//		System.out.println("---------------------sorting:   " + arrayPrinter(theArray));
		
		long startTimer = System.currentTimeMillis(); // start timer
		int a = 0; // starting index
		int b = theArraySize - 1; // ending index [0-99] 99 = 100(size) - 1
		
		/*
		 * sorting done in helper method for recursion 
		 */
		quickSort(theArray, a, b);

		long endTimer = System.currentTimeMillis(); // end timer
		long finalTime = endTimer - startTimer;

		System.out.println("It took " + finalTime + " ms to finish fifth sorting algorithm.");
//		System.out.println("---------------------sorted:   " + arrayPrinter(theArray));
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
	private static void quickSort(DeathsPerWeek[] theArray, int i, int j) {
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
	private static int partition(DeathsPerWeek[] theArray, int a, int b) {
		// left as a and b here to save variables names i,j
		DeathsPerWeek pivot = theArray[b];
		int i = (a - 1); //start at one position back 
		int j = a; 
		while (j < b) {
			if (theArray[j].getDeathTotal() <= pivot.getDeathTotal()) {
				i++;

				DeathsPerWeek holder = theArray[i];
				theArray[i] = theArray[j];
				theArray[j] = holder;
			}
			j++;
		}
		DeathsPerWeek holder = theArray[i + 1];
		theArray[i + 1] = theArray[b];
		theArray[b] = holder;

		return i + 1;
	}
}
