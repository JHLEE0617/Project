package Item;

import Presenter.Core;

public class TimePlus implements GameItem {
    private int cost = 0; // Instance variable for cost
    private static final String name = "추가";
    private static final int MAX_COUNT = 3; // Maximum count for this item

    @Override
    public void use(Core core) {
        if (cost <= 0) {
            return; // Cannot use if cost is 0
        }
        core.getView().getMenuComponent().addTime(5);
        decreaseCount(); // Decrease count after using
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
        this.cost = 0; // Reset cost
    }

	public static void setRemaining(int i) {
		// TODO Auto-generated method stub
		
	}
}