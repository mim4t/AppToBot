package mc.apptoeat.com.bot.support.strikepractice;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.bot.support.PluginSupport;

@Getter
@Setter
public class StrikePracticeSupport extends PluginSupport {

    public StrikePracticeSupport(String name) {
        super(name);
    }

    private SPEvents events;
    private SPManager manager;

    @Override
    public PluginSupport enable() {
        api = StrikePractice.getAPI();
        this.manager = new SPManager(this);
        this.events = new SPEvents(this);
        return super.enable();
    }
}
