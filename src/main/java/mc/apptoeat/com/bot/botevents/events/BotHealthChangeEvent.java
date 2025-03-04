package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;

@Getter
public class BotHealthChangeEvent extends Event {

    private final double after;
    private final double before;

    public BotHealthChangeEvent(Bot bot, double after, double before) {
        super(bot);
        this.after = after;
        this.before = before;
    }
}
