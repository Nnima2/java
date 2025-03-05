import java.util.ArrayList;
import java.util.Scanner;

public class c0 {

    static boolean canfind = false;
    public static void CanFindWord(String word, int charsFound, char[][] board,
            int boardSize, int y, int x/* last char locatoin */) {
        if (charsFound == word.length()) {
            canfind = true;
            return;
        } else if (charsFound == 0) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == word.charAt(0)) {
                        char temp = board[i][j];
                        board[i][j] = '#';
                        CanFindWord(word, 1, board, boardSize, i, j);
                        board[i][j] = temp;
                    }
                }
            }
        } else {
            for (int i = Math.max(y - 1, 0); i <= Math.min(y + 1, boardSize - 1); i++) {
                for (int j = Math.max(x - 1, 0); j <= Math.min(x + 1, boardSize - 1); j++) {
                    if (board[i][j] == word.charAt(charsFound)) {
                        char temp = board[i][j];
                        board[i][j] = '#';
                        CanFindWord(word, charsFound + 1, board, boardSize, i, j);
                        board[i][j] = temp;
                    }
                }
            }
        }
        return;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int boardSize = input.nextInt();
        char[][] board = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            String row = input.next();
            for (int j = 0; j < boardSize; j++) {
                board[i][j]= row.charAt(j);
            }
        }

        int queriesCount = input.nextInt();
        for (int i = 0; i < queriesCount; i++) {
            String word = input.next();
            CanFindWord(word, 0, board, boardSize, 0, 0);
            if (canfind)
                System.out.println("FOUND");
            else
                System.out.println("NOT FOUND");
            canfind = false;
        }
        input.close();
    }
}