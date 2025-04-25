package Item;

import Presenter.Core;

public interface GameItem {
	void decreaseCount();
    void use(Core core);
    int getCost();
    String getName();
    void setCost(int cost);
    void initCost();
}