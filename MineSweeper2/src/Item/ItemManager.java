package Item;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    private Map<String, GameItem> items;

    public ItemManager() {
        items = new HashMap<>();
        items.put("힌트", new MineSearch());
        items.put("추가", new TimePlus());
        items.put("부활", new Revive());
    }

    public GameItem getItem(String itemName) {
        return items.get(itemName);
    }
}