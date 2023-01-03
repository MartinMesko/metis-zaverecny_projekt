package UI.base;

import UI.Application;
import UI.UIElements.Menu;

public abstract class MenuPageBase extends PageBase {

    private Menu Menu;

    public MenuPageBase(String title, Application app) {
        super(title, app);
        this.setMenu(new Menu());
    }

    @Override
    public void display() {
//        Console.Clear();
        super.display();
    }

    protected final Menu getMenu() {
        return Menu;
    }

    protected final void setMenu(Menu value) {
        Menu = value;
    }

}

