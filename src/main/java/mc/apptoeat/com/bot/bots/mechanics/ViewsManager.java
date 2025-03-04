package mc.apptoeat.com.bot.bots.mechanics;

import lombok.Getter;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Getter
public class ViewsManager extends Manager {
    private final List<Player> players;

    public ViewsManager(Bot bot, List<Player> players) {
        super(bot);
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        getBot().getPacketsManager().sendCreatesPackets(player, 0);
        getBot().getInventoryManager().updateArmorPacket(0);
        getBot().getInventoryManager().updateArmorPacket(1);
        getBot().getInventoryManager().updateArmorPacket(2);
        getBot().getInventoryManager().updateArmorPacket(3);
        getBot().getInventoryManager().updateItemPacket(bot.getInventoryManager().getItemInMainHand());
        addPlayerToList(player);

        player.showPlayer(bot.getNpc().getBukkitEntity());
    }

    public void removePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            getBot().getPacketsManager().sendRemovePackets(player);
            player.hidePlayer(bot.getNpc().getBukkitEntity());
        }
    }


}
