package mc.apptoeat.com.bot;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.EventManager;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.BotManager;
import mc.apptoeat.com.bot.data.DataManager;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.data.presets.PresetManager;
import mc.apptoeat.com.bot.features.FeatureManager;
import mc.apptoeat.com.bot.support.SupportManager;
import mc.apptoeat.com.bot.thread.ThreadSystem;
import mc.apptoeat.com.bot.utils.*;
import mc.apptoeat.com.bot.utils.config.ConfigManager;
import mc.apptoeat.com.bot.utils.db.Mongo;
import mc.apptoeat.com.bot.utils.db.MongoHandler;
import mc.apptoeat.com.bot.utils.gui.imple.BotGui;
import mc.apptoeat.com.bot.utils.settings.DifficultiesSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class main extends JavaPlugin {

    @Getter
    private static main instance;

    @Getter
    DifficultiesSettings difficultiesSettings;

    @Getter
    private BotManager botManager;

    @Getter
    private DataManager dataManager;

    @Getter
    private EventManager eventManager;

    @Getter
    private FeatureManager featureManager;

    @Getter
    private String pluginName;

    @Getter
    private PresetManager presetManager;

    @Getter
    private BukkitEvents bukkitEvents;

    @Getter
    private SupportManager supportManager;

    @Getter
    private ThreadSystem threadSystem;

    @Getter
    private Mongo mongo = null;

    @Getter
    private MongoHandler mongoHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        pluginName = "OptimizedBotsV2";
        new ConfigManager(); //Loading the config manager
        saveDefaultConfig();
        loadSQL();
        botManager = new BotManager();
        eventManager = new EventManager();
        supportManager = new SupportManager();
        featureManager = new FeatureManager();
        presetManager = new PresetManager();
        bukkitEvents = new BukkitEvents();
        difficultiesSettings = new DifficultiesSettings();
        threadSystem = new ThreadSystem();
        botGuiRegister();

        registerListeners();

        Bukkit.getLogger().info(Color.code("&b&LLoading "+ pluginName +" &7by crafticat."));
    }

    public void loadSQL() {
        mongo = new Mongo();
        if (mongo.isEnabled()) {
//            try {
//                sqlManager.connect();
//                Bukkit.getLogger().info(Color.code("&aDatabase is connected"));
//            } catch (ClassNotFoundException | SQLException ignore) {
//                Bukkit.getLogger().info(Color.code("&cDatabase is not connected"));
//            }
            mongoHandler = new MongoHandler();
        }
    }

    public void botGuiRegister() {
        Command command = new Command("botgui","Bots setting gui","/botgui",null);

        command.setOnCommand((player, temp) -> {
            if (player.hasPermission("botgui.use")) {
                DataPlayer data = main.getInstance().dataManager.getPlayer(player);
                BotGui gui = data.getGui();
                if (gui.isLoaded()) {
                    gui.getMainGui().openGui();
                } else {
                    player.sendMessage(Color.code("&cError: The menu has not loaded, please try again later."));
                }
            } else {
                player.sendMessage(Color.code("&cYou need &3VIP &crank to use this."));
                player.sendMessage(Color.code("&cStore link: &estore.esta.land"));
            }
        });
    }

    public void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new PacketListener(),this);
        Bukkit.getServer().getPluginManager().registerEvents(dataManager = new DataManager(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for (Bot bot : getBotManager().getBots()) {
            bot.silentKill();
        }

        if (mongo != null) mongo.shutdown();

        instance = null;
    }

    public void callEvent(Event event) {
        for (BotEvent events : main.getInstance().getEventManager().getEvents()) {
            events.call(event);
        }
    }
}
