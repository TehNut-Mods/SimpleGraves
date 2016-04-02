package tehnut.graves.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tehnut.graves.ConfigHandler;
import tehnut.graves.SimpleGraves;
import tehnut.graves.api.IGraveSaveable;
import tehnut.graves.api.SimpleGravesAPI;
import tehnut.graves.block.BlockGrave;
import tehnut.graves.tile.TileGrave;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving().getEntityWorld().getGameRules().getBoolean("keepInventory"))
            return;

        if (event.getEntityLiving()instanceof EntityPlayer && !(event.getEntityLiving() instanceof FakePlayer)) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            World world = player.worldObj;
            BlockPos pos = player.getPosition();
            int saveOffset = 0;

            if (world.isRemote)
                return;

            BlockPos newPos = pos;

            if (ConfigHandler.placeOnGround)
                while (newPos.down().getY() > 0 && world.getBlockState(newPos.down()).getMaterial().isReplaceable())
                    newPos = newPos.down();

            while (newPos.getY() < 0)
                newPos = newPos.up();

            boolean testFlag = false;

            // Attempt to place the Grave at the chosen location.
            if (world.getBlockState(newPos).getBlock().isReplaceable(world, newPos) || world.getBlockState(newPos).getMaterial().isReplaceable()) {
                world.setBlockState(newPos, SimpleGraves.blockGrave.getStateFromMeta(world.rand.nextInt(BlockGrave.GraveType.values().length)));
                testFlag = true;
            }

            // Fallback 1: Forces grave into the location. Not preferred. Only allowed if enabled in config.
            if (!testFlag && ConfigHandler.forceGrave) {
                world.setBlockState(newPos, SimpleGraves.blockGrave.getStateFromMeta(world.rand.nextInt(BlockGrave.GraveType.values().length)));
                testFlag = true;
            }

            // Fallback 2: Checks for valid positions in a 5x5x5 radius. If the block is valid in any way, even if airborne, place the grave.
            if (!testFlag) {
                initial:
                for (int xOff = -5; xOff < 5; xOff++) {
                    for (int yOff = -5; yOff < 5; yOff++) {
                        for (int zOff = -5; zOff < 5; zOff++) {
                            BlockPos fallbackCheck = newPos.add(xOff, yOff, zOff);
                            if (fallbackCheck.getY() < 1)
                                fallbackCheck = new BlockPos(fallbackCheck.getX(), 1, fallbackCheck.getZ());
                            if (world.getBlockState(fallbackCheck).getBlock().isReplaceable(world, fallbackCheck) || world.getBlockState(fallbackCheck).getMaterial().isReplaceable()) {
                                world.setBlockState(fallbackCheck, SimpleGraves.blockGrave.getStateFromMeta(world.rand.nextInt(BlockGrave.GraveType.values().length)));
                                newPos = fallbackCheck;
                                break initial;
                            }
                        }
                    }
                }
            }

            TileEntity tile = world.getTileEntity(newPos);
            if (tile != null && tile instanceof TileGrave) {
                TileGrave grave = (TileGrave) tile;
                grave.setPlayerName(player.getDisplayNameString());
                for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++)
                    grave.getHandler().setStackInSlot(slot, player.inventory.getStackInSlot(slot));

                saveOffset += player.inventory.getSizeInventory();

                for (IGraveSaveable saveable : SimpleGravesAPI.getSaveables().values()) {
                    if (saveable.shouldHandle(player))
                        saveOffset += saveable.handleInventory(grave, player, saveOffset);
                    else
                        saveable.dropItems(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDrops(PlayerDropsEvent event) {
        if (event.getEntityLiving().getEntityWorld().getGameRules().getBoolean("keepInventory"))
            return;

        event.setCanceled(true);
    }
}
