package haxidenti.mcmenu;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MCPageMenu implements Menu {
    private int page = 0;
    private int maxPage = 0;

    private List<MCMenuItem> items;
    private String title;

    public MCPageMenu() {
        items = new ArrayList<>();
    }


    @Override
    public Menu addItem(String caption, Runnable action) {
        items.add(new MCMenuItem(caption, action));
        maxPage = (int) Math.floor((double) items.size() / 7);
        return this;
    }

    @Override
    public Menu setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void showForPlayer(Player player) {
        List<MCMenuItem> items = getPageItems(page);

        MCMenu menu = new MCMenu();
        menu.setTitle(title + "[" + (page+1) + "/" + (maxPage+1) + "]");
        items.forEach(i -> menu.addItem(i.name, i.action));

        if (page >= 1) {
            menu.addItem("<< BACK", () -> {
                this.page -= 1;
                this.showForPlayer(player);
            });
        } else {
            menu.addItem(" ...", null);
        }

        if (page < maxPage) {
            menu.addItem("NEXT >>", () -> {
                this.page += 1;
                this.showForPlayer(player);
            });
        } else {
            menu.addItem(" ...", null);
        }

        menu.showForPlayer(player);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<MCMenuItem> getItems() {
        return items;
    }

    private List<MCMenuItem> getPageItems(int page) {
        int startEl = page * 7;
        int endEl = startEl + 7;
        List<MCMenuItem> items = new ArrayList<>();

        for (int i = startEl; i < endEl; i++) {
            MCMenuItem item = getItem(i + 1);
            if (item != null) {
                items.add(item);
            } else {
                items.add(new MCMenuItem(" ... ", null));
            }
        }

        return items;
    }
}
