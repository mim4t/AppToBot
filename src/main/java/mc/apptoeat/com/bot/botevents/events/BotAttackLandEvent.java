package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import org.bukkit.entity.Player;

@Getter
public class BotAttackLandEvent extends Event {
    private final Player player;

    public BotAttackLandEvent(Bot bot, Player player) {
        super(bot);
        this.player = player;
    }
}
