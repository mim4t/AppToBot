package mc.apptoeat.com.bot.utils.objects;

import org.bukkit.entity.Player;

public interface PlayerRunnable {
    void run(Player player, int pressedSlot);
}