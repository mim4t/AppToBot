package mc.apptoeat.com.bot.support;

import lombok.Getter;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@Getter
public class PluginSupport implements Listener {

    private final String name;
    protected Object api;

    public PluginSupport(String name) {
        this.name = name;
        this.api = null;
        Bukkit.getServer().getPluginManager().registerEvents(this, main.getInstance());
    }

    public PluginSupport enable() {
        Bukkit.getLogger().info(Color.code("&f"+main.getInstance().getPluginName()+" added support for &c&l" + name));
        return this;
    }
}
