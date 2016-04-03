package tehnut.graves.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.graves.SimpleGraves;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(SimpleGraves.blockGrave),
                0,
                new ModelResourceLocation(
                        new ResourceLocation(SimpleGraves.MODID, SimpleGraves.blockGrave.getRegistryName().getResourcePath()),
                        "type=flat"
                )
        );
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
