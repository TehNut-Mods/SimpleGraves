package tehnut.graves.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.graves.ConfigHandler;
import tehnut.graves.SimpleGraves;
import tehnut.graves.block.BlockGrave;
import tehnut.graves.compat.CompatBaubles;
import tehnut.graves.tile.TileGrave;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entityLiving.getEntityWorld().getGameRules().getBoolean("keepInventory"))
            return;

        if (event.entityLiving instanceof EntityPlayer && !(event.entityLiving instanceof FakePlayer)) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            World world = player.worldObj;
            BlockPos pos = player.getPosition();
            int saveOffset = 0;

            if (world.isRemote)
                return;

            BlockPos newPos = pos;

            if (ConfigHandler.placeOnGround)
                while (newPos.down().getY() > 0 && world.getBlockState(newPos.down()).getBlock().getMaterial().isReplaceable())
                    newPos = newPos.down();

            while (newPos.getY() < 0)
                newPos = newPos.up();

            if (world.getBlockState(newPos).getBlock().isReplaceable(world, newPos) || world.getBlockState(newPos).getBlock().getMaterial().isReplaceable())
                world.setBlockState(newPos, SimpleGraves.blockGrave.getStateFromMeta(world.rand.nextInt(BlockGrave.GraveType.values().length)));

            TileEntity tile = world.getTileEntity(newPos);
            if (tile != null && tile instanceof TileGrave) {
                TileGrave grave = (TileGrave) tile;
                grave.setPlayerName(player.getDisplayNameString());
                for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++)
                    grave.getHandler().setStackInSlot(slot, player.inventory.getStackInSlot(slot));

                saveOffset += player.inventory.getSizeInventory();

                if (Loader.isModLoaded("Baubles")) {
                    if (ConfigHandler.saveBaubles)
                        saveOffset = CompatBaubles.handleBaubles(grave, player, saveOffset);
                    else
                        CompatBaubles.dropBaubles(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDrops(PlayerDropsEvent event) {
        if (event.entityLiving.getEntityWorld().getGameRules().getBoolean("keepInventory"))
            return;

        event.setCanceled(true);
    }
}
