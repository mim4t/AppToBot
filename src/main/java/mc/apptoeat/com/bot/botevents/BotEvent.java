package mc.apptoeat.com.bot.botevents;

import mc.apptoeat.com.bot.bots.Bot;

public class BotEvent {

    public void listen(Event event) {}

    public void tickEvent(Bot bot) {}

    public void call(Event event) {
        listen(event);
    }

    public void callTick(Bot bot) {
        tickEvent(bot);
    }
}
