package mc.apptoeat.com.bot.support.papi;

import mc.apptoeat.com.bot.support.PluginSupport;

public class PlaceHolderApiSupport extends PluginSupport {

    public PlaceHolderApiSupport(String name) {
        super(name);
    }

    @Override
    public PluginSupport enable() {
        new BotPingPlaceHolder().register();
        new HealthPlaceHolder().register();
        new BotStatsPlaceHolders().register();
        return super.enable();
    }
}
