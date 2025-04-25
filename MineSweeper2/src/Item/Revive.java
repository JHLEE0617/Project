package Item;

import Presenter.Core;

public class Revive implements GameItem {
    private int cost = 0; // Instance variable for cost
    private static final String name = "부활";
    private static final int MAX_COUNT = 1;
    
    @Override
    public void use(Core core) {
        if (cost > 0) {
            core.setReviveAvailable(true);
            decreaseCount();
        }
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void decreaseCount() {
        if (this.cost > 0) {
            this.cost--;
        }
    }

    @Override
    public void setCost(int cost) {
        this.cost = Math.max(0, Math.min(cost, MAX_COUNT));
    }

    @Override
    public void initCost() {
        this.cost = 0; // Reset cost to zero at start of game or reset.
    }
}