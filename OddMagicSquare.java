import java.util.Scanner;

/**
 * A class to create magic squares.
 * 
 * @author Matthew Staehely
 * @version CSC 143 Winter
 *
 */

public class OddMagicSquare {
	private int[][] square; // declares the 2D array which will be used to build magic squares.
	
	/**
	 * Creates a magic square given the correct input.
	 * 
	 * @param n the number of rows and columns in the square. The 'magic number' will be equal to n(n*n +1)/2.
	 * @throws IllegalArgumentException if n is not greater than 0 or is even.	 
	 */
	
	public OddMagicSquare(int n){
		if(n % 2 != 1 || n < 0){
			throw new IllegalArgumentException();
		}
		this.square = new int[n][n];
		this.square[(n/2)][0] = 1; // Fills middle left-most square with starting value, 1.		
		int row = n/2, col = 0; // Place holder variables used to track 'position' of next number.
		
		/*
		 * This loop builds the magic square according to the following rules:
		 * Starts at the middle left-most with '1'.
		 * The next square filled will be one down and one left. If this would leave the square, it
		 * will instead wrap around to the top and/or right side.
		 * If a filled square is encountered, the next square filled is instead directly to the right
		 * of the previous square.
		 */
		for(int i = 2; i <= n*n; i++){
			if(++row > this.square.length-1){
				row = 0;
			}
			if(--col < 0){
				col = this.square[0].length-1;
			}
			if(this.square[row][col] > 0){ // Checks for a filled square.
				
				// These return the place-holders to the previous square.
				if(--row < 0){
					row = this.square.length-1;
				}
				if(++col > this.square.length-1){
					col = 0;
				}
				col++; // Moves one to the right.
			}
			this.square[row][col] = i;
		}
	}
	
	/**
	 * Represents the magic square as a String.
	 * 
	 * @return the String representation of the object.
	 */
	
	public String toString(){
		String s = "";
		for(int i = 0; i < this.square.length; i++){
			s += this.square[i][0]; // fence post.
			for(int j = 1; j < this.square[i].length; j++){
				s += "\t" + this.square[i][j];		// tabs for nicer format.	
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Static method which checks a given integer array to see if it is a magic square.
	 * 
	 * @param array a 2D integer array 
	 * @return true if the object is a magic square.
	 */
	
	public static boolean isMagicSquare(int[][] array){
	    int check_number = 0, vert = 0, horiz = 0, diagl = 0, diagr = 0;
	    
	    // None of these qualify as magic squares. ORDER IS IMPORTANT.
	    if(array == null || array.length == 0 || array[0].length == 0){
	    	return false;	    	
	    }
	    
	    // First checks to see if 2D array is even a square.
	    for(int i = 0; i < array[0].length; i++){
	        if(!(array[i].length == array.length)){
	            return false;
	        }
	    }
	    
	    // Finds the magic number.
	    check_number += (array.length*(array.length*array.length+1)/2);
	    
	    // Checks both diagonals
	    for(int i = 0; i < array.length; i++){
	        diagl += array[i][i];
	        diagr += array[i][array.length-1 - i];
	    }
	    if(!(diagl == check_number) || !(diagr == check_number)){
	        return false;
	    }
	    
	    // Checks all verticals and horizontals. Builds both at once. Once a full row
	    // has been traversed, should equal magic number if object is a magic square.
	    for(int i = 0; i < array.length; i++){
	        for(int j = 0; j < array[0].length; j++){
	            horiz += array[i][j];
	            vert += array[j][i];
	        }
	        if(!(horiz == check_number) || !(vert == check_number)){
	            return false;
	        }
	        horiz = 0;
	        vert= 0;
	    }
	    
	    // If none of the checks have failed, ends up here and returns true.
	    return true;
	}
	/**
	 * Main method provides user interface and input validation.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		int n = -1; // initializes a non-zero value for loop.
		String s;
		System.out.println("Magic Square Builder");
		while(n != 0){			// Primary UI, prompts user for valid input.
			System.out.print("Please enter a positive odd integer (0 to exit): ");
			if(reader.hasNextInt()){ //basic input validation check.
				n = reader.nextInt();
				if(n > 0 && (n % 2 == 1)){ // User input valid, executes constructor.
					OddMagicSquare oms = new OddMagicSquare(n);
					System.out.println("\n" + oms);
				} else if (n < 0){ // Positive precondition not met.
					System.out.println("Number must be positive.\n");
				} else if (n != 0){ // Odd precondition not met.
					System.out.println("Number must be odd.\n");
				}
			} else { // Integer precondition not met.
				System.out.println("Input must be an integer!\n");
				reader.next();
			}
		}
		System.out.println("Good bye.");
		reader.close();
	}
}
