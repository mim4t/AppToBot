package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import org.bukkit.inventory.ItemStack;

//Also being called when finished drinking.
@Getter
public class FinishEatingEvent extends Event {

    private final ItemStack item;

    public FinishEatingEvent(Bot bot,ItemStack item) {
        super(bot);
        this.item = item;
    }
}
