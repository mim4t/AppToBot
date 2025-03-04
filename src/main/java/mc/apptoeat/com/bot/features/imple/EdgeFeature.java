package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EdgeFeature extends Feature {

    //If this feature is enabled & the bot is in the edge of a block he will stop moving.

    @Override
    public void tickEvent(Bot bot) {
//        if (!bot.getMovements().isEdgeDetector()) return;
//        Location steppedBlock = bot.getMovements().getLocation().clone().subtract(new Vector(0,0.95,0));
//        // N = -z, E = +x, W = -x, S = +z SW = -x +z, SE = +z +x, NE = -z +x, NW = -z -x
//        boolean found = false;
//        for (Block block : MathUtils.blocksFromTwoPoints(steppedBlock.clone().subtract(new Vector(0.6,0,0.6)), steppedBlock.clone().add(new Vector(0.6,0,0.6)))) {
//            if (block.getType().equals(Material.AIR)) {
//                found = true;
//            }
//        }

        if (!bot.getMovements().isEdgeDetector()) return;

        Location botLocation = bot.getMovements().getLocation();
        double yaw = botLocation.getYaw();
        double pitch = botLocation.getPitch();
        boolean found = false;

        Vector direction = new Vector(0, -1, 0);
// direction.setY(-Math.sin(Math.toRadians(pitch)));  // remove this line
        double h = Math.cos(Math.toRadians(pitch));
        direction.setX(-h * Math.sin(Math.toRadians(yaw)));
        direction.setZ(h * Math.cos(Math.toRadians(yaw)));

        Location frontBlockLocation = botLocation.clone().add(direction);

        if (frontBlockLocation.getBlock().getType().equals(Material.AIR)) {
            found = true;
        }

        if (bot.getMovements().isEdge() && !found) bot.getMovements().setMove(true);
        if (found && onAir(bot.getMovements().getTargetLocate().getTargetEntity())) bot.getMovements().setMove(false);
        bot.getMovements().setEdge(found);
    }

    public boolean onAir(Player player) {
        return player.getLocation().clone().subtract(new Vector(0,1,0)).getBlock().getType().equals(Material.AIR);
    }
}
