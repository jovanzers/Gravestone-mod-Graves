package gravestone;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import gravestone.config.GraveStoneConfig;
import gravestone.core.*;
import gravestone.core.commands.GSCommands;
import gravestone.core.compatibility.GSCompatibility;
import gravestone.core.event.GSEventHandlerNetwork;
import gravestone.core.event.GSEventsHandler;
import gravestone.core.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class ModGraveStone {

    @Instance("GraveStone")
    public static ModGraveStone instance;
    @SidedProxy(clientSide = "gravestone.core.proxy.ClientProxy", serverSide = "gravestone.core.proxy.CommonProxy")
    public static CommonProxy proxy;

    public ModGraveStone() {
        instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GraveStoneConfig.getInstance(event.getModConfigurationDirectory().getAbsolutePath() + "/GraveStoneMod/", "GraveStone.cfg");
        GSStructures.preInit();

        gravestone.core.GSMessageHandler.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        // register death event
        MinecraftForge.EVENT_BUS.register(new GSEventsHandler());
        FMLCommonHandler.instance().bus().register(new GSEventHandlerNetwork());
        proxy.registerHandlers();

        // tabs
        GSTabs.registration();

        // blocks registration
        GSBlock.registration();

        // items registration
        GSItem.registration();

        // reciepes registration
        GSRecipes.registration();

        // tileEntities registration
        GSTileEntity.registration();

        // register structures
        GSStructures.getInstance();

        // register entities
        GSEntity.getInstance();

        GSPotion.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GSGuiHandler());

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GSCompatibility.getInstance().checkMods();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        GSCommands.getInstance(event);
    }
}