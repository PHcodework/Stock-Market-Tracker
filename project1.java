import java.io.File;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class project1 {
		
	//The loadArray method, used to read the information from the text document into 
	//an array of doubles
	//The filename is passed into the loadArray method when it is called in the main method.
		static double[] loadArray(String filename) throws FileNotFoundException {
			File file = new File(filename);
			Scanner numLines = new Scanner(file);
			Scanner scan = new Scanner(file);
			int lines = 0;
			
			while(numLines.hasNextLine()) {
				numLines.nextLine();
				lines++;
			}
			
			double[] filler = new double[] {0,0};
			double[] thisArray = new double[lines];
			
			for(int i = 0; i < lines; i++) {
				thisArray[i] = Double.parseDouble(scan.nextLine());
			}
			
			return thisArray;
		}
		
		//The maxcross method, used to return an array containing the maximum element of
		//the left side of the input array, the maximum element of the right side of the array,
		//and the quotient of the leftsum and the right sum.
		//Note: The structure of the maxcross method was based on the FIND-MAXIMUM-SUBARRAY
		//method pseudocode from the 03p1 Introduction to Divide and Conquer Algorithms lecture slides.
		
		static double[] maxcross(double[] array, int low, int mid, int high) {
			double leftsum = Double.MIN_VALUE, rightsum = Double.MIN_VALUE;
			double maxleft = 0, maxright = 0;
			double sum = 0;
			
			for(int i = mid; i >= low; i--) {
				sum += array[i];
				if(sum > leftsum) {
					leftsum = sum;
					maxleft = i;
				}
			}
			
			sum = 0;
			
			for(int j = mid + 1; j <= high; j++) {
				sum += array[j];
				if(sum > rightsum) {
					rightsum = sum;
					maxright = j;
				}
			}
			
			return new double[] {maxleft, maxright, (leftsum / rightsum)};
		}
		
		//The maxsubarray method, used to recursively calculate and return the subarray containing the 
		//maximum profit of stocks within the file in order to run at the O(nlog(n)) time complexity.
		//Note: The structure of the maxsubarray method was based on the FIND-MAX-CROSSING-SUBARRAY
		//method pseudocode from the 03p1 Introduction to Divide and Conquer Algorithms lecture slides.
		static double[] maxsubarray(double[] array, int low, int high) {
			
			if(high == low) {
				return new double[] {low, high, array[low]};
			}
			
			else {
				int mid = ((low + high)/2);
				
				double[] leftarray = maxsubarray(array, low, mid);
				double[] rightarray = maxsubarray(array, (mid + 1), high);
				double[] crossarray = maxcross(array, low, mid, high);
				
				if(leftarray[2] >= rightarray[2] && leftarray[2] >= crossarray[2]) {
					return leftarray;
				}
				
				else if(rightarray[2] >= leftarray[2] && rightarray[2] >= crossarray[2]) {
					return rightarray;
				}
				
				else {
					return crossarray;
				}
			}
		}
		
		//The main method, used to call the loadArray and solve methods using the
		//argument (text document) provided by the user in the bash call
		public static void main(String[] args) throws FileNotFoundException {
			DecimalFormat df = new DecimalFormat("####0.00");
			String file = new String("");
			if(args.length > 0) {
	            file = args[0];
			}
			double[] days = loadArray(file);
			double[] days2 = maxsubarray(days, 0, days.length-1);
						
			System.out.println("The optimal solution for " + file + " is $" + df.format(days2[2]) + ".");
		}
}
