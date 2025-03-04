package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;

@Getter
public class BotArmorBreakEvent extends Event {
    private final int armorSlot;
    
    public BotArmorBreakEvent(Bot bot, int armorSlot) {
        super(bot);
        this.armorSlot = armorSlot;
    }
}
