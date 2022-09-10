import java.util.Arrays;

import javax.swing.GrayFilter;


public class SodukuSolver{
    private static final int total_row_column = 9;
    public static void main(String[] args) throws Exception {
        
        //Using 2-D Integer array or list of arrays to represent a 9X9
        //soduku board

        int [][] board =
        {
            {0,0,0,0,0,0,0,6,0},
            {0,0,0,3,8,0,1,9,0},
            {0,0,7,5,0,0,3,4,2},
            {8,9,0,0,0,0,4,0,0},
            {0,0,6,0,0,0,8,0,1},
            {3,4,0,0,0,0,0,0,0},
            {0,0,0,4,6,7,0,0,0},
            {0,0,0,0,0,5,0,3,0},
            {0,7,0,0,0,1,0,0,0},




        };
        showBoard(board);

        if (boardSolver(board))
        {
            System.out.println();
            System.out.println("Solved!");
        }
        else
        {
            System.out.println("No Solution");
        }
            System.out.println();
            showBoard(board);

    }

    private static void showBoard(int [][] board)
    {
        for (int i = 0; i < total_row_column; i++)
        {
            if (i % 3 == 0 && i != 0)
            {
                System.out.println("----------");
            }
            for (int j = 0; j < total_row_column; j ++)
            {
                if (j % 3 == 0 && j != 0)
                {
                    System.out.print("|");
                }
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    //If number already exists in the row
    private static boolean numberInRow_check(int[][] board, int numbercheck, int row)

    {
        for (int i = 0; i < total_row_column; i++)
        {
            // Here row is fixed, column is moving from 0-8
            if(board[row][i] == numbercheck)
            {
                //If the number we are looking for is found
                return true;
            }
        }
        //If number we are looking for is not found in the for loop
        return false;
    }

    //If number already exists in column
    private static boolean numberIncolumn_check(int[][] board, int numbercheck, int column)

    {
        
        for(int i = 0; i < total_row_column; i++)
        {
            //Here column is fixed and row is moving from 0-8
            if (board[i][column] == numbercheck)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean numberIn3GRID_check(int[][] board, int numbercheck, int row, int column)

    {
        // First we need to get coordinate of the local 3X3 box so we find the coordinate of the top left box in the 3X3 and traverse through it after
        
        // All boxes are equally divided into 3 rows and 3 columns
        // top row in any 3X3 = row argument - remaining distance to traverse the row of the 3X3
        // same way: bottom row would be = row argument + remaining distance to traverse the row of the 3X3
        // same logic for the column
        int localBoxRow = row - row % 3; // starting counting from 1 for both
        int localBoxColumn = column - column % 3;

        //Iterating through all rows of any 3X3
            //Adding 3 from our top row to traverse the rows of the whole  3X3
        for (int i = localBoxRow; i < localBoxRow + 3; i++)
        {   
            // Same traversing for the 3 columns starting from the top left coordinate in each 3X3
            for (int j = localBoxColumn; j < localBoxColumn + 3; j ++)
            {
                if (board[i][j] == numbercheck)
                {
                    return true;
                }
            }
            
        }
        //If number is not found after going through the 3X3
        return false;
        
    }

    //Now we have methods to check if our number is in row,column and 3x3 box.
    // Making a method that checks all 3 methods above to see if a specific position is valid to input a number
    // If not(number present in row, && not (number present in column) && not(number present in box) is all true then this method returns true
    //hence that coordinate is valid

    public static boolean coordinateValid(int[][] board, int numberInSpot, int row_coordinate, int column_coordinate)
    {
        return !numberInRow_check(board, numberInSpot, row_coordinate) && !numberIncolumn_check(board, numberInSpot, column_coordinate)
        && !numberIn3GRID_check(board, numberInSpot, row_coordinate, column_coordinate);

    
    }

    //Traverse row by row, for each empty square, look at number 1 to 9 and check whether each is valid in that space. Then this process repeats again.
    // What happens after we go through 1-9 in an empty square and none of them are valid. Then we go back to our previous number that we set.
    //Since that previous number results in us not being able to solve the board, we clear the previous number's space and keep trying the other numbers from 1-9
    //excluding the number that we deleted.

    //The process above repeats until all 0's(blanks) are filled with 1 to 9.

    private static boolean boardSolver(int[][] board)
    {
        //Nested for loop to traverse the entire grid. we can use i,j = GRID_SIZE, since board is 9X9
        //i = row, j = column
        for (int i = 0; i < total_row_column; i++)
        {
            for (int j = 0; j < total_row_column; j++)
            {
                if (board[i][j] == 0)
                {
                    //Trying numbers 1 through 9 that will be valid(not present in row,column and 3X3) in that place
                    for(int trialNumber = 1; trialNumber < total_row_column + 1; trialNumber++)
                    {
                        // if that coordinate is valid in that empty spot, replace 0 with that number.
                        if(coordinateValid(board, trialNumber, i, j))
                        {
                            board[i][j] = trialNumber;

                            //If a valid placement was found for our trialNumber, then we should restart traversing board from
                            //beginning of first row, first column. This time, it will skip our PREVIOUS trialNumber and find the next 0 to fill.
                            //In conclusion: restarts the boardSolver() method once again for a new trialNumber. 

                            // if recursive call succeeds means trialNumber was valid and the board started traversing from first row, first column
                            //looking for new blank space. Eventually this will replace all 0's and we will have a valid solution
                            //so return true in each recursive call eventually filling the whole board out
                           if (boardSolver(board))
                           {
                            return true;
                           }
                           else
                           {
                            //trialNumber was valid( not present in row, column and 3x3) so we placed it there. Next time boardSolver is called.
                            // it cannot place any number from 1-9 in the new blank spot so, it changes that previous trialNumber to 0 and then
                            // goes to try diffrent trialNumber values for the old trialNumber spot.
                            board[i][j] = 0;
                           }

                        }   

                
                    }
                    // What if we go through numbers 1-9 and none of them are valid(already present in row, column and 3X3) in that spot.
                    //Hence board is not solvable
                    return false;
                }
            }
        }
        // The board was solved.
        return true;
    }











}
