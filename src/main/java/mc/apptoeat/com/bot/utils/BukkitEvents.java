package mc.apptoeat.com.bot.utils;

import lombok.Getter;
import mc.apptoeat.com.bot.main;

@Getter
public class BukkitEvents {

    private final boolean projectileHitEvent = FileManager.getOrDefault(main.getInstance(),"bukkitevents.projectileHitEvent",true);
}
