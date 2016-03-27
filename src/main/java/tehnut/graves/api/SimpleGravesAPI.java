package tehnut.graves.api;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class SimpleGravesAPI {

    private static Map<String, IGraveSaveable> saveables = new HashMap<String, IGraveSaveable>();

    public static void registerSaveable(String id, IGraveSaveable saveable) {
        if (!saveables.containsKey(id))
            saveables.put(id, saveable);
    }

    public static Map<String, IGraveSaveable> getSaveables() {
        return ImmutableMap.copyOf(saveables);
    }
}
