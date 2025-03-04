package mc.apptoeat.com.bot.utils.db;

import com.mongodb.client.model.Filters;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingObject;
import mc.apptoeat.com.bot.data.presets.PresetType;
import mc.apptoeat.com.bot.main;
import org.bson.Document;
import org.bukkit.Bukkit;

import javax.print.Doc;
import java.util.UUID;

public class MongoUtil {
    public static Mongo mongo() {
        return main.getInstance().getMongo();
    }
    public static MongoHandler mongoHandler() {
        return main.getInstance().getMongoHandler();
    }

    public static Object getSetting(UUID uuid, Setting settingCategory, SettingObject settingObject, SettingType settingType) {
        Document playerDoc = mongo().getPlayers().find(Filters.eq("uuid", uuid.toString())).first();
        if (playerDoc != null) {
            Document settingsDoc = (Document) playerDoc.get("settings");
            Document settingsCategoryDoc = (Document) settingsDoc.get(settingCategory.toDatabaseString());
            Document settingsObjectDoc = (Document) settingsCategoryDoc.get(settingObject.toDatabaseString());
            switch (settingType) {
                case VALUE:
                    return settingsObjectDoc.get("value");
                case RANDOMIZER:
                    return settingsObjectDoc.get("randomizer");
                case MIN:
                    return settingsObjectDoc.get("min");
                case MAX:
                    return settingsObjectDoc.get("max");
            }
        }
        return null;
    }

    public static Object getSetting(String difficulty, Setting settingCategory, SettingObject settingObject, SettingType settingType) {
        Document difficultyDoc = mongo().getDifficulties().find(Filters.eq("difficulty", difficulty)).first();
        if (difficultyDoc != null) {
            Document settingsDoc = (Document) difficultyDoc.get("settings");
            Document settingsCategoryDoc = (Document) settingsDoc.get(settingCategory.toDatabaseString());
            Document settingsObjectDoc = (Document) settingsCategoryDoc.get(settingObject.toDatabaseString());
            switch (settingType) {
                case VALUE:
                    return settingsObjectDoc.get("value");
                case RANDOMIZER:
                    return settingsObjectDoc.get("randomizer");
                case MIN:
                    return settingsObjectDoc.get("min");
                case MAX:
                    return settingsObjectDoc.get("max");
            }
        }
        return null;
    }

    public static Object getSetting(PresetType presetType, String presetName, Setting settingCategory, SettingObject settingObject, SettingType settingType) {
        Document difficultyDoc = mongoHandler().getPresetCollection(presetType).find(Filters.eq("presetName", presetName)).first();
        if (difficultyDoc != null) {
            Document settingsDoc = (Document) difficultyDoc.get("settings");
            Document settingsCategoryDoc = (Document) settingsDoc.get(settingCategory.toDatabaseString());
            Document settingsObjectDoc = (Document) settingsCategoryDoc.get(settingObject.toDatabaseString());
            switch (settingType) {
                case VALUE:
                    return settingsObjectDoc.get("value");
                case RANDOMIZER:
                    return settingsObjectDoc.get("randomizer");
                case MIN:
                    return settingsObjectDoc.get("min");
                case MAX:
                    return settingsObjectDoc.get("max");
            }
        }
        return null;
    }

    public static void updateSetting(UUID uuid, Setting settingCategory, SettingObject settingObject, SettingType settingType, Object obj) {
        switch (settingType) {
            case VALUE:
                String path = "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".value";
                //Bukkit.broadcastMessage("Path: " + path);
                mongoHandler().updateValue(uuid, path, obj);
                settingObject.setObject(obj);
                //Bukkit.broadcastMessage("Object to set: " + obj);
                break;
            case RANDOMIZER:
                mongoHandler().updateValue(uuid, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".randomizer", obj);
                break;
            case MIN:
                mongoHandler().updateValue(uuid, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".min", obj);
                break;
            case MAX:
                mongoHandler().updateValue(uuid, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".max", obj);
                break;
            default:
                //Bukkit.broadcastMessage("Switch problem");
                break;
        }
    }

    public static void updateSetting(String difficulty, Setting settingCategory, SettingObject settingObject, SettingType settingType, Object obj) {
        switch (settingType) {
            case VALUE:
                mongoHandler().updateValue(difficulty, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".value", obj);
                break;
            case RANDOMIZER:
                mongoHandler().updateValue(difficulty, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".randomizer", obj);
                break;
            case MIN:
                mongoHandler().updateValue(difficulty, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".min", obj);
                break;
            case MAX:
                mongoHandler().updateValue(difficulty, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".max", obj);
                break;
        }
    }

    public static void updateSetting(PresetType presetType, String presetName, Setting settingCategory, SettingObject settingObject, SettingType settingType, Object obj) {
        switch (settingType) {
            case VALUE:
                mongoHandler().updateValue(presetType, presetName, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".value", obj);
                break;
            case RANDOMIZER:
                mongoHandler().updateValue(presetType, presetName, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".randomizer", obj);
                break;
            case MIN:
                mongoHandler().updateValue(presetType, presetName, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".min", obj);
                break;
            case MAX:
                mongoHandler().updateValue(presetType, presetName, "settings." + settingCategory.toDatabaseString() + "." + settingObject.toDatabaseString() + ".max", obj);
                break;
        }
    }

    public static boolean doesBotDocExist(UUID uuid) {
        Document playerDoc = mongo().getPlayers().find(Filters.eq("uuid", uuid.toString())).first();
        return playerDoc != null;
    }

    public static boolean doesBotDocExist(String difficulty) {
        Document playerDoc = mongo().getPlayers().find(Filters.eq("difficulty", difficulty)).first();
        return playerDoc != null;
    }

    public static boolean doesBotDocExist(PresetType presetType, String presetName) {
        Document playerDoc = mongoHandler().getPresetCollection(presetType).find(Filters.eq("presetName", presetName)).first();
        return playerDoc != null;
    }
}
