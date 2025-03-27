package views;
import models.enums.Menu;
import java.util.Scanner;
import models.App;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }
}
