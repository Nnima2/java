package views;
import java.util.Scanner;
/*
Explanation:
- This is a view class for the ExitMenu.
- We will just use it to end the program.
 */

public class ExitMenu implements AppMenu{
    @Override
    public void check(Scanner scanner) {
        return;
    }
}
