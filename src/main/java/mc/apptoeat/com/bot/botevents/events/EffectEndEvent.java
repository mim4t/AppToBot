package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import org.bukkit.potion.PotionEffectType;

@Getter
public class EffectEndEvent extends Event {

    private final PotionEffectType type;

    public EffectEndEvent(Bot bot,PotionEffectType type) {
        super(bot);
        this.type = type;
    }
}
