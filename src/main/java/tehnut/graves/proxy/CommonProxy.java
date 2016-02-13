package tehnut.graves.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tehnut.graves.ConfigHandler;
import tehnut.graves.SimpleGraves;
import tehnut.graves.block.BlockGrave;
import tehnut.graves.block.ItemBlockGrave;
import tehnut.graves.handler.EventHandler;
import tehnut.graves.tile.TileGrave;

import java.io.File;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(new File(event.getModConfigurationDirectory(), SimpleGraves.MODID + ".cfg"));

        SimpleGraves.blockGrave = new BlockGrave();
        GameRegistry.registerBlock(SimpleGraves.blockGrave, ItemBlockGrave.class);
        GameRegistry.registerTileEntity(TileGrave.class, SimpleGraves.MODID + ":TileGrave");

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
