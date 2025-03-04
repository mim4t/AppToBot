package mc.apptoeat.com.bot.support;

import lombok.Getter;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.support.papi.BotPingPlaceHolder;
import mc.apptoeat.com.bot.support.papi.PlaceHolderApiSupport;
import mc.apptoeat.com.bot.support.strikepractice.StrikePracticeSupport;
import mc.apptoeat.com.bot.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class SupportManager implements Listener {

    private final ArrayList<String> messages = new ArrayList<>();
    private final ArrayList<PluginSupport> pluginSupports = new ArrayList<>();

    public SupportManager() {
        addSupport(new StrikePracticeSupport("aiPractice"));
        addSupport(new PlaceHolderApiSupport("PlaceHolderAPI"));
        Bukkit.getServer().getPluginManager().registerEvents(this,main.getInstance());
    }

    @EventHandler
    public void joinMessage(PlayerJoinEvent e) {
        messages.forEach(message -> {
            e.getPlayer().sendMessage(message);
        });
    }

    public void addSupport(PluginSupport plugin) {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugin.getName())) {
            pluginSupports.add(plugin.enable());
        } else {
            Bukkit.getLogger().info(Color.code("&f"+ main.getInstance().getPluginName()+" were unable to add support for &B&l" + plugin.getName()));
        }
    }
}
