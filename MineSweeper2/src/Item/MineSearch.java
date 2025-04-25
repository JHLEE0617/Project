package Item;

import Presenter.Core;
import static javax.swing.JOptionPane.*;

public class MineSearch implements GameItem {
    private int cost = 0; // Instance variable for cost
    private static final String name = "힌트";
    private static final int MAX_COUNT = 3; // Maximum count for this item

    @Override
    public void use(Core core) {
        if (cost <= 0) {
            return; // Cannot use if cost is 0
        }
        int[] mineLocation = core.getModel().getRandomUnrevealedMineLocation();
        if (mineLocation != null) {
            core.getView().showMineHint(mineLocation[0], mineLocation[1]);
        } else {
            showMessageDialog(null, "남은 지뢰가 없습니다!");
        }
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
        if (cost > 0) {
            cost--; // Decrease count
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