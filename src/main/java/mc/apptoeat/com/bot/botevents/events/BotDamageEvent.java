package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import net.minecraft.server.v1_8_R3.DamageSource;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;



public class BotDamageEvent extends Event {
    DamageSource source;
    @Setter
    @Getter
    boolean canceled;
    
    public BotDamageEvent(Bot bot) {
        super(bot);
    }


    public BotDamageEvent(Bot bot,DamageSource source) {
        super(bot);
        AtomicInteger a = new AtomicInteger();
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }

}
