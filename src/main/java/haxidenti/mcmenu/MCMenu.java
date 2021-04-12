package haxidenti.mcmenu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MCMenu implements Menu {

    static HashMap<String, Menu> menus = new HashMap<>(32);

    private List<MCMenuItem> items;
    private String title;

    public MCMenu() {
        this.items = new ArrayList<>();
        this.title = "Menu";
    }

    @Override
    public MCMenu addItem(String caption, Runnable action) {
        if (items.size() >= 9) {
            throw new IllegalArgumentException("Max items in menu: 9");
        }
        items.add(new MCMenuItem(caption, action));
        return this;
    }

    @Override
    public MCMenu setTitle(String title) {
        if (title == null) {
            title = "Menu";
        }
        this.title = title;
        return this;
    }

    @Override
    public void showForPlayer(Player player) {
        showMenuForPlayer(player, this);
    }

    static void showMenuForPlayer(Player player, Menu menu) {
        menus.put(player.getName(), menu);

        player.sendMessage(ChatColor.RED + "===( " + ChatColor.YELLOW + menu.getTitle() + ChatColor.RED + " )===");

        int cnt = 1;
        List<MCMenuItem> items = menu.getItems();
        for (MCMenuItem item : items) {
            player.sendMessage("" + ChatColor.RED + cnt + ". " + ChatColor.YELLOW + item.name);
            cnt += 1;
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<MCMenuItem> getItems() {
        return items;
    }
}
