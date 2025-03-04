package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotAttackEvent;
import mc.apptoeat.com.bot.botevents.events.BotAttackLandEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.WTapSetting;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class WTapFeature extends Feature {

    @Override
    public void listen(Event event) {
        if (event instanceof BotAttackLandEvent) {
            BotAttackLandEvent e = (BotAttackLandEvent) event;
            Bot bot = e.bot;
            Random r = new Random();
            int random = (r.nextInt(100));
            WTapSetting wTapSetting = bot.getSettingManager().getWTapSetting();

            bot.getMovements().setAttackSprint(false);
            if (!wTapSetting.getEnabled().isBooleanValue()) return;
            if (random > wTapSetting.getChance().getIntValue()) return;

            final long tapLong;
            if (main.getInstance().getDataManager().getPlayer(e.getPlayer()).getBotCombo() > 3) {
                tapLong = wTapSetting.getComboTapLong().getIntValue();
            } else {
                tapLong = wTapSetting.getTapLong().getIntValue();
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!bot.isAlive()) return;


                    if (bot.getSettingManager().getWTapSetting().getBlockingHitting().isBooleanValue()) {
                        //Block hitting handling
                        bot.getPacketsManager().sendBlockHittingPackets(true);
                        bot.getMovements().setUse(true);
                    } else {
                        //W tap handing
                        bot.getMovements().setMove(false);
                    }
                    bot.getMovements().setWtap(true);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!bot.isAlive()) return;

                            if (bot.getSettingManager().getWTapSetting().getBlockingHitting().isBooleanValue()) {
                                //Block hitting handling
                                bot.getPacketsManager().sendBlockHittingPackets(false);
                                bot.getMovements().setUse(false);
                            } else {
                                //W tap handing
                                bot.getMovements().setMove(true);
                            }

                            bot.getMovements().setWtap(false);
                        }
                    }.runTaskLater(main.getInstance(),tapLong);
                }
            }.runTaskLater(main.getInstance(),wTapSetting.getTapStart().getIntValue());
        }
    }
}
