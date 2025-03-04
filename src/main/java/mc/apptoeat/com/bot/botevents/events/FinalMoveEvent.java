package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import org.bukkit.Location;

@Getter
@Setter
public class FinalMoveEvent extends Event {

    private Location to;
    private Location from;

    public FinalMoveEvent(Bot bot, Location to, Location from) {
        super(bot);
        this.to = to;
        this.from = from;
    }
}
