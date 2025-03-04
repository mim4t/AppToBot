package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;

@Getter
public class SpawnEvent extends Event {

    public SpawnEvent(Bot bot) {
        super(bot);
    }
}
