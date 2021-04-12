package haxidenti.mcmenu;

public class MCMenuItem {
    public String name;
    public Runnable action;

    public MCMenuItem(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }
}
