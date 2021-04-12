package haxidenti.mcmenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only simple players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("menu")) {
            Menu mcMenu = MCMenu.menus.get(sender.getName());
            if (mcMenu == null) {
                sender.sendMessage(ChatColor.RED + "No menus for you!");
                return true;
            }
            MCMenu.showMenuForPlayer(player, mcMenu);
            return true;
        }
        return false;
    }

    @EventHandler
    public void x(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        MCPageMenu menu = new MCPageMenu();

        for (int i = 0; i < 25; i++) {
            final int c = i;

            menu.addItem( "Say " + i, () -> player.chat("C: " + c));
        }

        menu.setTitle("C say's");

        menu.showForPlayer(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage();
        if (message.length() != 1) {
            return;
        }

        if (!"0123456789".contains(message)) {
            return;
        }

        Menu menu = MCMenu.menus.get(player.getName());

        if (menu == null) {
            player.sendMessage(ChatColor.RED + "No menus for you!");
            event.setCancelled(true);
            return;
        }

        try {
            int n = Integer.parseInt(message);
            MCMenuItem item = menu.getItem(n);
            if (item == null || item.action == null) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, item.action);
            MCMenu.menus.remove(player.getName());
            event.setCancelled(true);
        } catch (NumberFormatException e) {
            return;
        }

    }
}