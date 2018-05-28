package framework.util;

import java.util.HashMap;
import java.util.Map;

public class MapTool {
    private MapTool() {}

    public static Map<String, String> toMap(String input) {
        Map<String, String> map = new HashMap<String, String>();
        String[] array = input.split(",");
        for (String str : array) {
            String[] pair = str.trim().split("=");
            if (pair == null) {
                return null;
            }
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            } else if (pair.length == 1)

            {
                map.put(pair[0], null);
            } else {
                return null;
            }
        }
        return map;
    }

}
