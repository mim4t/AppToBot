package mc.apptoeat.com.bot.data;

import lombok.Getter;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.db.MongoUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DataManager implements Listener {

    private final Map<Player, DataPlayer> dataSet;

    public DataManager() {
        dataSet = new HashMap<>();
    }

    @EventHandler
    public void add(PlayerJoinEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                main.getInstance().getMongoHandler().createBotDoc(e.getPlayer().getUniqueId());
            }
        }.runTaskAsynchronously(main.getInstance());

        DataPlayer player = new DataPlayer(e.getPlayer());
        dataSet.put(e.getPlayer(), player);

        main.getInstance().getBotManager().getBots().forEach(bot -> {
            e.getPlayer().hidePlayer(bot.getNpc().getBukkitEntity());
        });
    }

    @EventHandler
    public void remove(PlayerQuitEvent e) {
        getPlayer(e.getPlayer()).quit();
        dataSet.remove(e.getPlayer());
    }

    public DataPlayer getPlayer(Player player) {
        return dataSet.get(player);
    }
}
