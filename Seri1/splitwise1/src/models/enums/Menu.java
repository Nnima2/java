package models.enums;

import java.util.Scanner;
import views.*;

/*
Explanation:
- In your code, you have some menus that are constants, and we move between them.
- a good way to handle this is to use enums to define them and use them in your code.
 */
public enum Menu {
    SignUpMenu(new SignUpMenu()),
    LoginMenu(new LoginMenu()),
    ProfileMenu(new ProfileMenu()),
    Dashboard(new Dashboard()),
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
