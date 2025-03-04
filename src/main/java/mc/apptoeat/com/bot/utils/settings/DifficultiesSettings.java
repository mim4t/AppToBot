package mc.apptoeat.com.bot.utils.settings;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.data.presets.PresetType;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.Command;
import mc.apptoeat.com.bot.utils.db.MongoUtil;
import mc.apptoeat.com.bot.utils.gui.GlobalGui;
import mc.apptoeat.com.bot.utils.gui.imple.SettingGui;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DifficultiesSettings {

    private final Map<String, SettingManager> data;
    private Map<String, SettingGui> guiMap; //Gui data
    private GlobalGui mainGui;

    public DifficultiesSettings() {
        //Utilizes the maps
        data = new HashMap<>();
        guiMap = new HashMap<>();

        //Loads the settings
        loadSetting("EASY","&aEasy");
        loadSetting("NORMAL","&eNormal");
        loadSetting("HARD","&cHard");
        loadSetting("EXPERT","&4Expert");
        loadSetting("NIGHTMARE", "&5Nightmare");
        loadGui();
        loadCommand();
    }

    public SettingManager getData(String setting) {
        if (data.containsKey(setting)) return data.get(setting);
        return null;
    }

    public String getData(SettingManager setting) {
        for (Map.Entry<String, SettingManager> entry : data.entrySet()) {
            if (entry.getValue() == setting) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void loadSetting(String setting,String fancyName) {
        main.getInstance().getMongoHandler().createBotDoc(setting);
        SettingManager manager = SettingManager.loadSettings(setting);
        data.put(setting,manager);

        // Creates a gui
        guiMap.put(fancyName, new SettingGui(manager,null, PresetType.DEFAULT)); // We don't need any profiles in difficulty selector therefore its null
    }

    public void loadGui() {
        mainGui = new GlobalGui("&cBot Difficulty Manager",9);

        mainGui.setGui(() -> {
            mainGui.reset();

            final int[] slot = {0};
            guiMap.forEach((fancyName, gui) -> {
                mainGui.createGuiItem(new ItemStack(Material.NAME_TAG), slot[0],fancyName,(player, temp) -> {
                    gui.getMainGui().minOpen(player);
                });
                slot[0]++;
            });
        });
    }

    public void loadCommand() {
        Command command = new Command("difficulties","Practice 1v1 Difficulty Manager","/difficulties",(p, t) -> {});

        command.setOnCommand((player, temp) -> {
            //Perms check
            if (!player.hasPermission("bot.staff")) {
                player.sendMessage(Color.code("&cYou do not have permission to execute this command."));
                return;
            }

            //Opens the gui
            mainGui.minOpen(player);
        });
    }
}
