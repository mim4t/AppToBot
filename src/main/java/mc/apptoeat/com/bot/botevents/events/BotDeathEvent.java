package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.bots.Bot;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class BotDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Bot bot;

    public BotDeathEvent(Bot bot) {
        this.bot = bot;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
