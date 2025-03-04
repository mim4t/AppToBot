package mc.apptoeat.com.bot.utils.objects;

import mc.apptoeat.com.bot.botevents.Event;

import java.io.IOException;

public interface EventRunnable {
    public abstract void run(Event event);
}
