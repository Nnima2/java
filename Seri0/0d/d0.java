import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;

public class d0 {

    // check if we can add n meter water to this cell
    public static boolean CanWaterEscape(Cell[][] board, int boardY, int boardX, boolean[][] visited, int y, int x,
            int waterHeight) {
        if (y == 1 && waterHeight > board[0][x].height ||
                y == boardY - 2 && waterHeight > board[boardY - 1][x].height ||
                x == boardX - 2 && waterHeight > board[y][boardX - 1].height ||
                x == 1 && waterHeight > board[y][0].height) {// water is near the edge and can flow
            return true;
        }

        if (visited[y][x]) {
            return false;
        }
        visited[y][x] = true;

        if (waterHeight > board[y - 1][x].height) {// UP
            if (CanWaterEscape(board, boardY, boardX,visited, y - 1, x, waterHeight)) {
                return true;
            }
        }
        if (waterHeight > board[y + 1][x].height) {// DOWN
            if (CanWaterEscape(board, boardY, boardX,visited, y + 1, x, waterHeight)) {
                return true;
            }
        }
        if (waterHeight > board[y][x - 1].height) {// LEFT
            if (CanWaterEscape(board, boardY, boardX,visited, y, x - 1, waterHeight)) {
                return true;
            }
        }
        if (waterHeight > board[y][x + 1].height) {// RIGHT
            if (CanWaterEscape(board, boardY, boardX,visited, y, x + 1, waterHeight)) {
                return true;
            }
        }

        return false;
    }

    public static void FlowWater(Cell[][] board, int boardY, int boardX,int y, int x, int waterHeight) {

        if(board[y][x].visited){
            return;
        }
        board[y][x].waterCapacity = waterHeight - board[y][x].height;
        board[y][x].visited = true;


        if (1 < y && waterHeight > board[y - 1][x].height) {// UPd
            FlowWater(board, boardY, boardX, y - 1, x, waterHeight);
        }
        if (y < boardY - 2 && waterHeight > board[y + 1][x].height) {// DOWN
            FlowWater(board, boardY, boardX, y + 1, x, waterHeight);
        }
        if (1 < x && waterHeight > board[y][x - 1].height) {// LEFT
            FlowWater(board, boardY, boardX, y, x - 1, waterHeight);
        }
        if (x < boardX - 2 && waterHeight > board[y][x + 1].height) {// RIGHT
            FlowWater(board, boardY, boardX, y, x + 1, waterHeight);
        }
        return;
    }

    public static int TrappedRainWater(Cell[][] board, int boardY, int boardX) {
        for (int i = 1; i < boardY - 1; i++) {
            for (int j = 1; j < boardX - 1; j++) {

                if (board[i][j].visited){
                    continue;
                }


                boolean [][] visited = new boolean[boardY][boardX];
                int waterAdded = 1;
                while (!CanWaterEscape(board, boardY, boardX,visited, i, j, board[i][j].height + waterAdded)) {
                    board[i][j].waterCapacity = waterAdded;
                    waterAdded++;
                    for (boolean[] row : visited) {
                        Arrays.fill(row, false);
                    }                    
                }

                if(board[i][j].waterCapacity>0)
                {
                    FlowWater(board, boardY, boardX, i, j, board[i][j].height + board[i][j].waterCapacity);
                }
            }
        }

        int waterSum = 0;
        for (int i = 0; i < boardY; i++) {
            for (int j = 0; j < boardX; j++) {
                waterSum += board[i][j].waterCapacity;
                //System.out.print(board[i][j].waterCapacity + " ");
            }
            System.out.println();
        }

        return waterSum;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int boardY = input.nextInt();
        int boardX = input.nextInt();
        Cell[][] board = new Cell[boardY][boardX];
        for (int i = 0; i < boardY; i++) {
            for (int j = 0; j < boardX; j++) {
                board[i][j] = new Cell();
                board[i][j].height = input.nextInt();
            }
        }

        System.out.println(TrappedRainWater(board, boardY, boardX));

        input.close();
    }

}

class Cell {
    public Cell() {
        visited = false;
        height = 0;// just for defualt
        waterCapacity = 0;
    }

    public boolean visited;
    public int height;
    public int waterCapacity;
}
