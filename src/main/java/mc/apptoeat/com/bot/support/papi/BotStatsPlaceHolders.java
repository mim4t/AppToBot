package mc.apptoeat.com.bot.support.papi;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.fights.Fight;
import ga.strikepractice.fights.botduel.BotDuel;
import ga.strikepractice.fights.duel.Duel;
import ga.strikepractice.npc.CitizensNPC;
import ga.strikepractice.placeholders.PlaceholderContext;
import ga.strikepractice.utils.PlayerUtil;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Function;

public class BotStatsPlaceHolders extends PlaceholderExpansion {
    @Override
    public @NotNull
    String getIdentifier() {
        return "botstats";
    }

    @Override
    public @NotNull String getAuthor() {
        return "crafticat";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable
    String onPlaceholderRequest(Player player2, @NotNull String params) {
        if (player2 == null) return "";

        Bot bot = main.getInstance().getBotManager().getNpcFromTargetView(player2);

        Player player = player2;
        if (bot != null) {
            player = bot.getMovements().getTargetLocate().getTargetEntity();
        }

        if(params.equals("yourhits")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
            int hits = data.getHitCounter();

            return hits + "";
        }
        if (params.equals("hits")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
            int counter = data.getBotHitCounter();

            if (bot == null) {
                Fight fight = StrikePractice.getAPI().getFight(player);
                counter = defaultHits(player, fight);
            }

            return counter + "";
        }
        if (params.equals("hitsdifference")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
            int hits = data.getHitCounter();
            int botHits = data.getBotHitCounter();
            int diff = (hits - botHits);
            String string = Color.code("&a(+%value%)");
            if (diff < 0) string = Color.code("&c(%value%)");

            return string.replace("%value%",diff + "");
        }
        if (params.equals("yourhitsdifferencespec")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
            int hits = data.getHitCounter();
            int botHits = data.getBotHitCounter();
            int diff = (hits - botHits);
            String string = Color.code("&a(+%value%)");
            if (diff < 0) string = Color.code("&c(%value%)");

            return (diff != 0) ? string.replace("%value%",diff + "") : "";
        }
        if (params.equals("hitsdifferencespec")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
            int hits = data.getHitCounter();
            int botHits = data.getBotHitCounter();
            int diff = (hits - botHits);

            String string = "";

            if (diff != 0) {
                if (diff > 0) {
                    string = Color.code("&c(-" + diff + ")");
                } else {
                    string = Color.code("&a(+" + diff + ")").replace("-", "");
                }
            }

            return string;
        }
        if (params.equals("yourcombo")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);

            int combo = data.getCombo();
            int botCombo = data.getBotCombo();

            if (combo < 2 && botCombo < 2) {
                return "1st to 100!";
            } else if (combo > botCombo) {
                return Color.code("&a") + combo + " Combo";
            } else {
                return Color.code("&c") + botCombo + " Combo";
            }
        }
        if (params.equals("combo")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);

            int combo = data.getCombo();
            int botCombo = data.getBotCombo();

            if (combo < 2 && botCombo < 2) {
                return "1st to 100!";
            } else if (combo > botCombo) {
                return Color.code("&c") + combo + " Combo";
            } else {
                return Color.code("&a") + botCombo + " Combo";
            }
        }

        if (params.equals("yourcombo_spec")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);

            int combo = data.getCombo();
            int botCombo = data.getBotCombo();

            if (combo < 2 && botCombo < 2) {
                return "No Combo";
            } else if (combo > botCombo) {
                return Color.code("&a") + combo + " Combo";
            } else {
                return Color.code("&c") + botCombo + " Combo";
            }
        }
        if (params.equals("combo_spec")) {
            DataPlayer data = main.getInstance().getDataManager().getPlayer(player);

            int combo = data.getCombo();
            int botCombo = data.getBotCombo();

            if (combo < 2 && botCombo < 2) {
                return "No Combo";
            } else if (combo > botCombo) {
                return Color.code("&c") + combo + " Combo";
            } else {
                return Color.code("&a") + botCombo + " Combo";
            }
        }

        return "";
    }

    public int defaultHits(Player player,Fight fight) {
        Function<Fight, String> opponentName = c -> {
            if (fight instanceof Duel) {
                Duel duel = (Duel) c;
                String opponent = duel.getP1();
                if (opponent.equals(player.getName())) {
                    opponent = duel.getP2();
                }
                return opponent;
            } else if (c instanceof BotDuel) {
                return ((BotDuel) c).getBotName();
            }
            return null;
        };

        String op = opponentName.apply(fight);
        Player p = PlayerUtil.getPlayer(op);
        if (fight == null) return -1;
        if (p != null) {
            return fight.getStatistics(p).getHits();
        }
        if (fight instanceof BotDuel) {
            Bot npc = ((BotDuel) fight).getP2();
            if (npc != null && npc.getNpc().getBukkitEntity() != null) {
                UUID uuid = npc.getNpc().getBukkitEntity().getUniqueId();
                return fight.getStatistics(uuid).getHits();
            }
        }
        return -1;
    }
}