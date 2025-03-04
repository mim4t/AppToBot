package mc.apptoeat.com.bot.data.presets;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.battlekit.BattleKit;
import ga.strikepractice.fights.botduel.BotDuel;
import ga.strikepractice.npc.CitizensNPC;
import lombok.Getter;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.Command;
import mc.apptoeat.com.bot.utils.gui.imple.SettingGui;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PresetManager {

    private final List<DataPresets> dataList;
    private final DataPresets unranked;
    private final DataPresets ranked;
    private final DataPresets tournament;

    public PresetManager() {
        dataList = new ArrayList<>();
        unranked = registerData("unranked", PresetType.UNRANKED);
        ranked = registerData("ranked", PresetType.RANKED);
        tournament = registerData("tournament", PresetType.TOURNAMENT);
    }

    public DataPresets registerData(String name, PresetType type) {
        DataPresets p = new DataPresets(name, type);
        createCMD(p, name);
        return p;
    }

    public void createCMD(DataPresets data, String name) {
        //Admin command
        Command adminCMD = new Command(data.getName() + "admin","Presets admin gui",data.getName() + "admin",null);

        adminCMD.setOnCommand((player, temp) -> {
            if (!player.hasPermission("bot.staff")) {
                player.sendMessage(Color.code("&cYou do not have permission to execute this command."));
                return;
            }
            data.getGuis().get(0).minOpen(player);
        });

        Command command = new Command("secret" + data.getName(),"queue","secret" +data.getName(),null);

        command.setOnCommand((player, temp) -> {
            if (command.getArgs().length < 1) {
                System.out.println(Color.code("&4DEBUG: False calling"));
                return;
            }
            System.out.println(Color.code(""));
            String kit = command.getArgs()[0];
            BotDuel duel = new BotDuel(StrikePractice.getInstance(), player.getName(), BattleKit.getKit(kit));
            if (name.equals("ranked")) duel.setDifficulty(CitizensNPC.Difficulty.RANKED);
            if (name.equals("unranked")) duel.setDifficulty(CitizensNPC.Difficulty.UNRANKED);

            duel.setKit(BattleKit.getKit(kit));
            if (duel.canStart()) {
                duel.start();
            }
        });
    }

    public DataPresets getDataPresets(PresetType type) {
        switch (type) {
            case UNRANKED:
                return unranked;
            case RANKED:
                return ranked;
            case TOURNAMENT:
                return tournament;
        }
        return null;
    }

    public SettingManager getPresetSetting(PresetType type, String name) {
        List<SettingGui> settingGuis = getDataPresets(type).getPresets();
        for (SettingGui settingGui : settingGuis) {
            if (settingGui.getManager().getName().equals(name)) {
                return settingGui.getManager();
            }
        }
        return null;
    }
}
