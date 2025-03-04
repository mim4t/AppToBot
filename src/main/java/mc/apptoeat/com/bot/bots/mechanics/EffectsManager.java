package mc.apptoeat.com.bot.bots.mechanics;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.events.EffectEndEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.main;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class EffectsManager extends Manager {

    private final Map<PotionEffectType,Integer> effects;
    private final Bot bot;

    public EffectsManager(Bot bot) {
        super(bot);
        effects = new HashMap<>();
        this.bot = bot;
    }

    public void updateEffects() {
        bot.getNpc().effects.clear();
        effects.keySet().forEach(effect -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    bot.getNpc().addEffect(new MobEffect(effect.getId(),100,effects.get(effect),true,true));

                }
            }.runTask(main.getInstance());
        });
    }

    public void addEffect(PotionEffectType type, Integer level) {
        effects.put(type, level);
        addEffectEvent(type, level,true);
    }

    public void removeEffect(PotionEffectType type) {
        effects.put(type,0);
        bot.getMovements().callEventAsync(new EffectEndEvent(bot,type));
        updateEffects();
    }

    public void addEffect(PotionEffectType type,Integer level,int time,boolean update) {
        effects.put(type,level);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!bot.isAlive()) return;
                removeEffect(type);
            }
        }.runTaskLater(main.getInstance(), time / 20);
        addEffectEvent(type,level,update);
    }

    public int getLevel(PotionEffectType type) {
        if (!effects.containsKey(type)) return 0;
        return effects.get(type);
    }

    public void addEffects(Collection<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            addEffect(effect.getType(),effect.getAmplifier() + 1, effect.getDuration() * 20,false);
        }
        updateEffects();
    }

    public boolean hasEffects(Collection<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            if (!this.effects.containsKey(effect.getType())) return false;
            if (this.effects.get(effect.getType()) == 0) return false;
        }

        return true;
    }

    public void addEffectEvent(PotionEffectType type,int level,boolean update) {
        if (type == PotionEffectType.ABSORPTION) {
            int extraHealth = 4 * level;
            bot.getHealthManager().setAbsHearts(extraHealth);
        }
        if (update)updateEffects();
    }
}
