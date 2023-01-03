package UI.base;

import UI.Application;

public abstract class PageBase {

    private String Title;
    private Application Application;

    public PageBase(String title, Application application) {
        this.setTitle(title);
        this.setApplication(application);
    }

    public void display() {
        System.out.println(getTitle());
    }

    public final String getTitle() {
        return Title;
    }

    public final void setTitle(String value) {
        Title = value;
    }

    public final Application getApplication() {
        return Application;
    }

    public final void setApplication(Application value) {
        Application = value;
    }

}

