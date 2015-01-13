/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gravestone.core.commands;

import gravestone.core.logger.GSLogger;
import gravestone.structures.GSStructureGenerator;
import gravestone.structures.catacombs.CatacombsGenerator;
import gravestone.structures.graves.SingleGraveGenerator;
import gravestone.structures.memorials.MemorialGenerator;
import gravestone.structures.village.VillageCemeteryGenerator;
import gravestone.structures.village.VillageUndertakerGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class CommandStructuresGenerator extends CommandBase {

    @Override
    public String getName() {
        return "generate";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/" + getName() + " <structure name> <x coordinate> <z coordinate>";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException {
        GSLogger.logInfo("Structure generation command recieved");

        if (args[0].equals("catacombs")) {
            generateStructure(sender.getEntityWorld(), args[1], args[2], CatacombsGenerator.getInstance());
        } else if (args[0].equals("memorial")) {
            generateStructure(sender.getEntityWorld(), args[1], args[2], MemorialGenerator.getInstance());
        } else if (args[0].equals("grave")) {
            generateStructure(sender.getEntityWorld(), args[1], args[2], SingleGraveGenerator.getInstance());
        } else if (args[0].equals("cemetery")) {
            generateStructure(sender.getEntityWorld(), args[1], args[2], VillageCemeteryGenerator.getInstance());
        } else if (args[0].equals("undertaker")) {
            generateStructure(sender.getEntityWorld(), args[1], args[2], VillageUndertakerGenerator.getInstance());
        } else {
            GSLogger.logError("Unknown structure type");
        }

    }

    private static void generateStructure(World world, String xStr, String zStr, GSStructureGenerator structure) {
        try {
            structure.generate(world, world.rand, Integer.parseInt(xStr), Integer.parseInt(zStr), 0, true);
        } catch (NumberFormatException e) {
            GSLogger.logError("Coordinate error");
            e.printStackTrace();
        }
    }
}
