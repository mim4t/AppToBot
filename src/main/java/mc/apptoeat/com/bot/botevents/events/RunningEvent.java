package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;

@Getter
@Setter
public class RunningEvent extends Event {

    private boolean forceEnd;
    private int ticks;

    public RunningEvent(Bot bot,int ticks) {
        super(bot);
        this.ticks = ticks;
    }
}
