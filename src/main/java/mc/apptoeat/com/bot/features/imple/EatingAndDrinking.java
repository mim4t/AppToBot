package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.*;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EatingAndDrinking extends Feature {

    private int runningTicks = 40;

    @Override
    public void listen(Event event) {
        Bot bot = event.bot;

        // Trigger eating logic shortly after spawning
        if (event instanceof SpawnEvent) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runEat(bot);
                }
            }.runTaskLater(main.getInstance(), 1);
        }

        boolean lowHealth = false;
        if (event instanceof BotHealthChangeEvent) {
            BotHealthChangeEvent e = (BotHealthChangeEvent) event;
            if (e.getAfter() <= bot.getSettingManager().getEatingSettings().getEatingGappleHealth().getIntValue()) {
                lowHealth = true;
            }
        }

        if (event instanceof FinishEatingEvent || event instanceof EffectEndEvent || event instanceof BotRequiredToEatEvent || lowHealth) {
            boolean able = ableToEat(bot.getSettingManager().getEatingSettings().getStartEatingDistance().getDoubleValue(), bot)
                    || bot.getFleeManager().getTicks() > bot.getSettingManager().getEatingSettings().getStartEatingTimer().getIntValue();

            // Condition to gapple
            boolean needsHealth = bot.getHealthManager().getHealth() + bot.getHealthManager().getAbsHearts()
                    < bot.getSettingManager().getEatingSettings().getEatingGappleHealth().getIntValue()
                    && bot.getInventoryManager().hasItem(322).size() > 0;

            // Condition to eat steak for saturation (steak = 364)
            boolean needsSaturation = bot.getHealthManager().getSaturation() < 3 && bot.getInventoryManager().hasItem(364).size() > 0;

            if (needsHealth) {
                // Prefer splash potion first
                if (!getPotions(bot).isEmpty()) {
                    main.getInstance().getFeatureManager().getHealthPotionThrowing().splash(bot, bot.getFleeManager().getTicks());
                    return;
                }

                // Fallback to gapple if able
                if (able) {
                    if (!bot.getInventoryManager().isEating()) eatGap(bot);
                } else {
                    bot.getInventoryManager().setRequiredToEat(true);
                }
            } else if (needsSaturation) {
                // Eat steak if saturation is low
                if (able) {
                    if (!bot.getInventoryManager().isEating()) eatSteak(bot);
                } else {
                    bot.getInventoryManager().setRequiredToEat(true);
                }
            } else {
                bot.getInventoryManager().setRequiredToEat(false);
            }
        }

        // Handle drinking potions
        if (event instanceof FinishEatingEvent || event instanceof EffectEndEvent || event instanceof BotRequiredToEatEvent) {
            boolean able = ableToEat(bot.getSettingManager().getEatingSettings().getStartEatingDistance().getDoubleValue(), bot)
                    || bot.getFleeManager().getTicks() > bot.getSettingManager().getEatingSettings().getStartEatingTimer().getIntValue();

            if (needsToDrink(bot)) {
                if (able) {
                    if (!bot.getInventoryManager().isEating()) runEat(bot);
                } else {
                    bot.getInventoryManager().setRequiredToEat(true);
                }
            }
        }

        // If eating is cancelled, retry later
        if (event instanceof EatingCancelEvent) {
            bot.getInventoryManager().setRequiredToEat(true);
        }
    }

    // Determines if the bot should drink a potion (checks for unused effects)
    public boolean needsToDrink(Bot bot) {
        List<ItemStack> list = bot.getInventoryManager().hasItem(373);
        AtomicBoolean result = new AtomicBoolean(false);

        list.forEach(item -> {
            Collection<PotionEffect> effects = Potion.fromItemStack(item).getEffects();
            if (Potion.fromItemStack(item).isSplash()) return;
            if (bot.getEffectsManager().hasEffects(effects)) return;
            result.set(true);
        });

        return result.get();
    }

    // Generic drinking logic (non-splash potions)
    public void runEat(Bot bot) {
        bot.getInventoryManager().allowEating();
        List<ItemStack> list = bot.getInventoryManager().hasItem(373);
        list.forEach(itemStack -> bot.getInventoryManager().eat(itemStack));
    }

    // Eat golden apple if not recently eaten
    public static void eatGap(Bot bot) {
        if (System.currentTimeMillis() - bot.getInventoryManager().getGappleDelay() < 2000) return;
        bot.getInventoryManager().allowEating();
        List<ItemStack> list = bot.getInventoryManager().hasItem(322);
        list.forEach(itemStack -> bot.getInventoryManager().eat(itemStack));
        bot.getInventoryManager().setGappleDelay(System.currentTimeMillis());
    }

    // Eat steak for saturation (ID 364)
    public static void eatSteak(Bot bot) {
        bot.getInventoryManager().allowEating();
        List<ItemStack> steak = bot.getInventoryManager().hasItem(364);
        steak.forEach(itemStack -> bot.getInventoryManager().eat(itemStack));
    }

    // Force the bot to jump if eating while under threat
    @Override
    public void tickEvent(Bot bot) {
        boolean able = ableToEat(bot.getSettingManager().getEatingSettings().getCancelEatingDistance().getDoubleValue(), bot);
        if (!able && bot.getInventoryManager().isEating()) bot.getMovements().setJump(true);
    }

    // Check if bot is far enough from enemy to eat safely
    public boolean ableToEat(double dist, Bot bot) {
        if (!bot.getMovements().getTargetLocate().getTargetEntity().getWorld().equals(bot.getMovements().getLocation().getWorld()))
            return true;
        return bot.getMovements().getTargetLocate().getTargetEntity().getLocation().distance(bot.getMovements().getLocation()) > dist;
    }

    // Get all splash healing potions in inventory
    public List<ItemStack> getPotions(Bot bot) {
        List<ItemStack> potions = bot.getInventoryManager().hasItem(373);
        List<ItemStack> result = new ArrayList<>();

        for (ItemStack item : potions) {
            if (Potion.fromItemStack(item).isSplash()
                    && Potion.fromItemStack(item).getType().equals(PotionType.INSTANT_HEAL)) {
                result.add(item);
            }
        }

        return result;
    }
}
