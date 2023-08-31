import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SudokuAll {

    static int gridSize = 9;
     static int count = 1;
    public static boolean isValidSudokuGrid(int[][] grid) {
        // Check each row, column, and subgrid
  
        for (int length = 0; length < gridSize; ++length) {
            boolean[] used = new boolean[gridSize + 1];
            for (int row = 0; row < gridSize; ++row) {
                int num = grid[length][row];
                if (num != 0 && (num < 1 || num > gridSize || used[num])) {
                    System.out.println("INVALID INPUT GRID");
                    System.out.println(num + " IS REPEATED MORE THAN ONCE IN " + (length + 1) + "th ROW");
                    return false; 
                }
                used[num] = true;
            }

            used = new boolean[gridSize + 1];

            for (int col = 0; col < gridSize; ++col) {
                int num = grid[col][length];
                if (num != 0 && (num < 1 || num > gridSize || used[num])) {
                    System.out.println("INVALID INPUT GRID");
                    System.out.println(num + " IS REPEATED MORE THAN ONCE IN " + (length + 1) + "th COLUMN");
                    return false;  
                }
                used[num] = true;
            }
        }

        int subgridSize = (int) Math.sqrt(gridSize);
        for (int startRow = 0; startRow < gridSize; startRow += subgridSize) {
            for (int startCol = 0; startCol < gridSize; startCol += subgridSize) {
                boolean []used = new boolean[gridSize + 1];
                for (int row = startRow; row < startRow + subgridSize; ++row) {
                    for (int col = startCol; col < startCol + subgridSize; ++col) {
                        int num = grid[row][col];
                        if (num != 0 && used[num]) {
                            System.out.println("INVALID INPUT GRID");
                            System.out.println(num + " IS REPEATED MORE THAN ONCE IN SUB GRID ");
                            return false;
                        }
                        used[num] = true;
                    }
                }
            }
        }

        return true; // All checks passed, grid is valid
    }

    public static void display(int[][] grid) {
        System.out.println("THE " + count +"TH SOLUTION FOR INPUT GRID");
        count++;
        for (int row = 0; row < gridSize; ++row) {
            for (int col = 0; col < gridSize; ++col) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isSafe(int row, int col, int[][] grid, int value) {
        for (int length = 0; length < gridSize; ++length) {
            if (grid[row][length] == value) return false;
            if (grid[length][col] == value) return false;
        }
        for (int i = row - row % 3; i < (row - row % 3) + 3; ++i) {
            for (int j = col - col % 3; j < (col - col % 3) + 3; ++j) {
                if (grid[i][j] == value) return false;
            }
        }
        return true;
    }
    public static void solveAll(int[][] grid, int row, int col) {
        if (row == gridSize) {
            display(grid);
            return;
        }
    
        int nextRow = row;
        int nextCol = col + 1;
        if (nextCol == gridSize) {
            nextRow++;
            nextCol = 0;
        }
    
        if (grid[row][col] != 0) {
            solveAll(grid, nextRow, nextCol);
            return;
        }
    
        for (int num = 1; num <= gridSize; num++) {
            if (isSafe(row, col, grid, num)) {
                grid[row][col] = num;
                solveAll(grid, nextRow, nextCol);
                grid[row][col] = 0;
            }
        }
    }
 
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            int[][] grid = new int[gridSize][gridSize];
            for (int row = 0; row < gridSize; ++row) {
                for (int col = 0; col < gridSize; ++col) {
                    grid[row][col] = scanner.nextInt();
                    if((grid[row][col] != 0) && (grid[row][col] < 1 || grid[row][col] > gridSize)) { 
                        System.out.println("INVALID INPUT GRID");
                        System.out.println(grid[row][col] + " is not in valid range 1 to " + gridSize);
                        System.exit(0);
                    }
                }
            }

            if (isValidSudokuGrid(grid)) {
                solveAll(grid, 0, 0);
          
            } 

            scanner.close();
        } catch(InputMismatchException e) {
            System.out.println("INVALID INPUT GRID");
            System.out.println("THE INPUT GRID CONTAINED A CHARACTER VALUES");
        } catch(NoSuchElementException e) {
            System.out.println("INVALID INPUT GRID"); 
            System.out.println("INSUFFICIENT NUMBER OF CELLS IN INPUT GRID");
        }
    }
}
  
/* GRID WITH MULTIPLE SOLUTIONS
9 0 6 0 7 0 4 0 3
0 0 0 4 0 0 2 0 0
0 7 0 0 2 3 0 1 0
5 0 0 0 0 0 1 0 0
0 4 0 2 0 8 0 6 0
0 0 3 0 0 0 0 0 5
0 3 0 7 0 0 0 5 0
0 0 7 0 0 5 0 0 0
4 0 5 0 1 0 7 0 8

 */


 