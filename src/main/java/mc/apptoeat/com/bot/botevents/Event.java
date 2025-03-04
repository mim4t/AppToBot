package mc.apptoeat.com.bot.botevents;

import lombok.Getter;
import mc.apptoeat.com.bot.bots.Bot;

public class Event {

    public final Bot bot;

    public Event(Bot bot) {
        this.bot = bot;
    }
}
