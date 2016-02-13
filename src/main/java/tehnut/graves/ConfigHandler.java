package tehnut.graves;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    public static boolean placeOnGround;

    public static boolean saveBaubles;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "General";
        placeOnGround = config.getBoolean("placeOnGround", category, true, "On death, lowers the gravestone to the ground.\nReplaces any block that is normally replaceable. (Air, tallgrass, water, etc)");

        category = "Compatibility";
        saveBaubles = config.getBoolean("saveBaubles", category, true, "Saves Baubles to the gravestone on death.");

        config.save();
    }
}
