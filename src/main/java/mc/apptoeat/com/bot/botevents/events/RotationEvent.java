package mc.apptoeat.com.bot.botevents.events;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;

@Getter
@Setter
public class RotationEvent extends Event {

    private float newYaw;
    private float newPitch;

    private float oldYaw;
    private float oldPitch;

    public RotationEvent(Bot bot, float newYaw,float newPitch,float oldYaw, float oldPitch) {
        super(bot);
        this.newYaw = newYaw;
        this.newPitch = newPitch;
        this.oldPitch = oldPitch;
        this.oldYaw = oldYaw;
    }
}
