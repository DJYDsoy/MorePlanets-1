package stevekung.mods.moreplanets.core;

import java.io.File;
import java.util.Arrays;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import stevekung.mods.moreplanets.client.command.ClientCommandHandlerMP;
import stevekung.mods.moreplanets.client.command.CommandChangeLog;
import stevekung.mods.moreplanets.core.config.ConfigManagerMP;
import stevekung.mods.moreplanets.core.event.ClientEventHandler;
import stevekung.mods.moreplanets.core.event.EntityEventHandler;
import stevekung.mods.moreplanets.core.event.GeneralEventHandler;
import stevekung.mods.moreplanets.core.event.WorldTickEventHandler;
import stevekung.mods.moreplanets.core.handler.GuiHandlerMP;
import stevekung.mods.moreplanets.init.*;
import stevekung.mods.moreplanets.items.capsule.ItemCapsule;
import stevekung.mods.moreplanets.network.PacketSimpleMP;
import stevekung.mods.moreplanets.proxy.ServerProxyMP;
import stevekung.mods.moreplanets.recipe.CraftingManagerMP;
import stevekung.mods.moreplanets.util.CreativeTabsMP;
import stevekung.mods.moreplanets.util.FuelHandlerMP;
import stevekung.mods.moreplanets.util.ReflectionUtils;
import stevekung.mods.moreplanets.util.VersionChecker;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;

@Mod(modid = MorePlanetsCore.MOD_ID, name = MorePlanetsCore.NAME, version = MorePlanetsCore.VERSION, dependencies = MorePlanetsCore.DEPENDENCIES, guiFactory = MorePlanetsCore.GUI_FACTORY)
public class MorePlanetsCore
{
    public static final String NAME = "More Planets";
    public static final String MOD_ID = "moreplanets";
    public static final int MAJOR_VERSION = 2;
    public static final int MINOR_VERSION = 0;
    public static final int BUILD_VERSION = 5;
    public static final String VERSION = MorePlanetsCore.MAJOR_VERSION + "." + MorePlanetsCore.MINOR_VERSION + "." + MorePlanetsCore.BUILD_VERSION;
    public static final String GUI_FACTORY = "stevekung.mods.moreplanets.core.config.ConfigGuiFactoryMP";
    public static final String CLIENT_CLASS = "stevekung.mods.moreplanets.proxy.ClientProxyMP";
    public static final String SERVER_CLASS = "stevekung.mods.moreplanets.proxy.ServerProxyMP";
    public static final String FORGE_VERSION = "after:Forge@[11.15.1.1722,);";
    public static final String DEPENDENCIES = "required-after:GalacticraftCore; required-after:GalacticraftPlanets; required-after:Micdoodlecore; " + MorePlanetsCore.FORGE_VERSION;
    public static final String MC_VERSION = (String) FMLInjectionData.data()[4];
    private static boolean DEOBFUSCATED;

    @SidedProxy(clientSide = MorePlanetsCore.CLIENT_CLASS, serverSide = MorePlanetsCore.SERVER_CLASS)
    public static ServerProxyMP PROXY;

    @Instance(MorePlanetsCore.MOD_ID)
    public static MorePlanetsCore INSTANCE;

    public static boolean[] STATUS_CHECK = { false, false, false };

    public static CreativeTabsMP BLOCK_TAB;
    public static CreativeTabsMP ITEM_TAB;

    static
    {
        try
        {
            MorePlanetsCore.DEOBFUSCATED = Launch.classLoader.getClassBytes("net.minecraft.world.World") != null;
        }
        catch (Exception e) {}
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigManagerMP.init(new File(event.getModConfigurationDirectory(), "MorePlanets.cfg"));
        ReflectionUtils.setFinal("instance", new ClientCommandHandlerMP(), ClientCommandHandler.class, ClientCommandHandler.instance);
        MorePlanetsCore.initModInfo(event.getModMetadata());
        MorePlanetsCore.BLOCK_TAB = new CreativeTabsMP("MorePlanetsBlocks");
        MorePlanetsCore.ITEM_TAB = new CreativeTabsMP("MorePlanetsItems");

        MPBlocks.init();
        MPItems.init();
        MPEntities.init();
        MPPlanets.init();
        MPPotions.init();
        MPOthers.init();
        MorePlanetsCore.PROXY.registerPreRendering();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MPTileEntities.init();
        MorePlanetsCore.PROXY.registerInitRendering();
        GalacticraftCore.packetPipeline.addDiscriminator(ConfigManagerMP.idNetworkHandler, PacketSimpleMP.class);
        MorePlanetsCore.BLOCK_TAB.setDisplayItemStack(new ItemStack(MPBlocks.ROCKET_CRUSHER));
        MorePlanetsCore.ITEM_TAB.setDisplayItemStack(new ItemStack(MPItems.SPACE_WARPER_CORE));

        if (CommonRegisterHelper.isClient())
        {
            CommonRegisterHelper.postRegisteredSortBlock();
            CommonRegisterHelper.postRegisteredSortItem();
            CommonRegisterHelper.registerForgeEvent(new ClientEventHandler());
            ClientCommandHandler.instance.registerCommand(new CommandChangeLog());
        }

        CommonRegisterHelper.registerForgeEvent(this);
        CommonRegisterHelper.registerForgeEvent(new EntityEventHandler());
        CommonRegisterHelper.registerForgeEvent(new GeneralEventHandler());
        CommonRegisterHelper.registerForgeEvent(new WorldTickEventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        VersionChecker.startCheck();
        MorePlanetsCore.PROXY.registerRendering();
        CommonRegisterHelper.registerGUIHandler(this, new GuiHandlerMP());
        CommonRegisterHelper.registerFuelHandler(new FuelHandlerMP());
        CraftingManagerMP.init();
        MPSchematics.init();
        ItemCapsule.init = true;
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        WorldTickEventHandler.startedDimensionData = null;
    }

    @EventHandler
    public void onMissingMapping(FMLMissingMappingsEvent event)
    {
        for (MissingMapping mapping : event.getAll())
        {
            //BlockItemRemapper.remapBlock(mapping, "jelly_block_test", FronosBlocks.JELLY_BLOCK);
        }
    }

    public static boolean isObfuscatedEnvironment()
    {
        return MorePlanetsCore.DEOBFUSCATED;
    }

    private static void initModInfo(ModMetadata info)
    {
        info.autogenerated = false;
        info.modId = MorePlanetsCore.MOD_ID;
        info.name = MorePlanetsCore.NAME;
        info.version = MorePlanetsCore.VERSION;
        info.description = "An add-on exploration with custom planets for Galacticraft!";
        info.url = "https://minecraft.curseforge.com/projects/galacticraft-add-on-more-planets";
        info.credits = "All credits to Galacticraft Sources/API and some people who helped.";
        info.authorList = Arrays.asList("SteveKunG");
    }
}