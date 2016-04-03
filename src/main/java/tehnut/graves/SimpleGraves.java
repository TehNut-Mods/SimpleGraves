package tehnut.graves;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.graves.proxy.CommonProxy;

@Mod(modid = SimpleGraves.MODID, name = SimpleGraves.NAME, version = SimpleGraves.VERSION, dependencies = SimpleGraves.DEPEND)
public class SimpleGraves {

    public static final String MODID = "SimpleGraves";
    public static final String NAME = "SimpleGraves";
    public static final String VERSION = "@VERSION@";
    public static final String DEPEND = "required-after:Forge@[12.16.0.1819,);";

    @Mod.Instance(MODID)
    public static SimpleGraves instance;

    @SidedProxy(clientSide = "tehnut.graves.proxy.ClientProxy", serverSide = "tehnut.graves.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Block blockGrave;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
