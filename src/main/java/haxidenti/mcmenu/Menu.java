package haxidenti.mcmenu;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public interface Menu {
    Menu addItem(String caption, Runnable action);

    Menu setTitle(String title);

    void showForPlayer(Player player);

    String getTitle();

    default List<MCMenuItem> getItems() {
        return Collections.emptyList();
    }

    default MCMenuItem getItem(int n) {
        if (n < 1) return null;
        List<MCMenuItem> items = getItems();
        if (n > items.size()) return null;
        return items.get(n - 1);
    }
}
