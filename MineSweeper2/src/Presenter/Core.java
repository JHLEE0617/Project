package Presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Item.GameItem;
import Item.ItemManager;
import Item.MineSearch;
import Item.TimePlus;
import RankPage.Rank;
import mine.CellState;
import mine.ModelManager;
import view.*;

import static javax.swing.JOptionPane.*;

public class Core {

	private ModelManager model;
	private ViewManager view;
	private CellComponent cellComponent;
	private ItemManager itemManager;
	private MenuComponent menuComponent;
	private RankingComponent RaningComponent;
	private boolean reviveAvailable = false;
	private boolean reviveUsed = false;
	public static final int Mined = 9;
	private boolean isTimeOver = false;

	public Core() {
		model = new ModelManager();
		//수정 - 랭킹 리스너 추가
//		this.view = new ViewManager(new GameSizeMenuButtonListener(this), new ItemMenuButtonListener(this),  this);
		this.view = new ViewManager(new GameSizeMenuButtonListener(this), new ItemMenuButtonListener(this), new RankingMenuButtonListener(this),  this);
		cellComponent = new CellComponent(); // CellComponent 초기화
		cellComponent.initCellComponent(); // 버튼 초기화
		view.setMenuComponent(new MenuComponent(this));
		itemManager = new ItemManager();
		menuComponent = new MenuComponent(this); // Core 인스턴스 초기화
		init();
	}

	public void setReviveAvailable(boolean available) {
        this.reviveAvailable = available;
    }

	public ModelManager getModel() {
		return model;
	}

	public ViewManager getView() {
		return view;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}
	
	public void setTimeOver(boolean timeOver) {
	    this.isTimeOver = timeOver;
	}

	public void init() {
		view.showGameSizeMenu();
	}

	public void newGame() {
		model.NewGame();
		model.resetRevealedMines();
		view.showGameSizeMenu();
		view.resetView();
		reviveAvailable = false;
	    reviveUsed = false;
	    
	    // 부활 아이템 상태 초기화
	    GameItem reviveItem = itemManager.getItem("부활");
	    reviveItem.setCost(0);
	    
	    // 다른 아이템들도 초기화
	    itemManager.getItem("힌트").initCost();
	    itemManager.getItem("추가").initCost();
	    
	    view.getItemComponent().showItemCost();
	}

	public void checkAroundCells(int x, int y) {
		/*좌클릭 우클릭 동시에 클릭하는 경우에 지뢰 위치에 정확히 체크가 되어있는 경우
		3x3으로 셀 확인하는 기능입니다.*/
		if (model.getCellProperty(x, y) == getCheckedCellCount(x, y)) {
			checkSingleCell(x - 1, y - 1);
			checkSingleCell(x - 1, y);
			checkSingleCell(x - 1, y + 1);
			checkSingleCell(x, y - 1);
			checkSingleCell(x, y + 1);
			checkSingleCell(x + 1, y - 1);
			checkSingleCell(x + 1, y);
			checkSingleCell(x + 1, y + 1);
		}
	}

	private int getCheckedCellCount(int x, int y) {
		int checkedCellCount = 0;
		// 셀에 있는 숫자와 주변의 체크 숫자의 일치여부 확인에 사용
		if (isChecked(x + 1, y + 1))
			checkedCellCount++;
		if (isChecked(x, y + 1))
			checkedCellCount++;
		if (isChecked(x - 1, y + 1))
			checkedCellCount++;
		if (isChecked(x + 1, y))
			checkedCellCount++;
		if (isChecked(x - 1, y))
			checkedCellCount++;
		if (isChecked(x + 1, y - 1))
			checkedCellCount++;
		if (isChecked(x, y - 1))
			checkedCellCount++;
		if (isChecked(x - 1, y - 1))
			checkedCellCount++;

		return checkedCellCount;
	}

	private boolean isChecked(int x, int y) {
		//위 메서드에서 체크 수 확인때 사용합니다.
		boolean checked = false;
		if (x >= 0 && y >= 0 && x < ModelManager.CellXSize && y < ModelManager.CellYSize) {
			if (model.getCellState(x, y) == CellState.Checked)
				checked = true;
		}
		return checked;
	}

	public void checkSingleCell(int x, int y) { // 설정 범위 내 셀에 대하여 해당 셀 클릭시 상태 확인
		if (x >= 0 && y >= 0 && x < ModelManager.CellXSize && y < ModelManager.CellYSize) {
			CellState state = model.getCellState(x, y);
			if (state == CellState.Covered) {
				int property = model.getCellProperty(x, y);
				if (property == Mined) {
					gameOver(false); // 지뢰 클릭 시 게임 오버
				} else {
					openCell(x, y); // 셀 열기
					// 빈 셀일 경우 주변 셀도 열기
					if (property == 0) {
						checkSingleCell(x + 1, y + 1);
						checkSingleCell(x + 1, y);
						checkSingleCell(x + 1, y - 1);
						checkSingleCell(x, y + 1);
						checkSingleCell(x, y - 1);
						checkSingleCell(x - 1, y + 1);
						checkSingleCell(x - 1, y);
						checkSingleCell(x - 1, y - 1);
					}
				}
			} else if (state == CellState.Opened) {
				return; // 이미 열린 셀인 경우
			}

			// 승리 조건 확인
			if (isVictory()) {
				int score = view.getMenuComponent().getScore(); // 점수 계산
				view.showResult("WIN"); // 승리 메시지와 점수 표시
				Rank.addScore(MenuComponent.getDifficultyLevel(),score);
				view.getMenuComponent().stopTimer();
			}
		}
	}

	/*
	 * public void useItem(String itemName) { GameItem item =
	 * itemManager.getItem(itemName); if (item != null) { item.use(this); } }
	 */

	public void useItem(String itemName) {
	    GameItem item = itemManager.getItem(itemName);
	    if (item != null && item.getCost() > 0) {
	        item.use(this);
	        view.getItemComponent().showItemCost();
	        view.getItemComponent().showRemainingItemCost();
	    } else {
	        view.showMessage("아이템을 사용할 수 없습니다.");
	    }
	}
	
	private void updateItemDisplay() { 
	       view.getItemComponent().showItemCost(); 
	       view.getItemComponent().showRemainingItemCost(); 
	   }

	private boolean isVictory() {
		for (int x = 0; x < ModelManager.CellXSize; x++) {
			for (int y = 0; y < ModelManager.CellYSize; y++) {
				if (model.getCellProperty(x, y) != Core.Mined && model.getCellState(x, y) != CellState.Opened) {
					return false; // 지뢰가 아닌 셀중에 하나라도 열려있지 않으면 false
				}
			}
		}
		return true; // 모든 지뢰가 아닌 셀이 열렸다면 승리
	}

	private void openCell(int x, int y) {
		model.setCellState(x, y, CellState.Opened); //0이 아닌 경우 해당 셀만 오픈
		view.openCell(x, y, model.getCellProperty(x, y));
	}
	
	public void useRevive(int x, int y) {
	    reviveAvailable = false;
	    model.revealMine(x, y);
	    view.showMessage("부활 아이템을 사용했습니다!");
	    view.openCell(x, y, Core.Mined);
	}

	public void gameOver(boolean isTimeOver) {
		this.isTimeOver = isTimeOver;
		if (!isTimeOver && reviveAvailable && !reviveUsed) {
	        reviveUsed = true;
	        view.showMessage("부활 아이템이 사용되었습니다!");
	        getItemManager().getItem("부활").setCost(-1); // 부활 아이템 사용
	        view.getItemComponent().showItemCost(); 
	    } else {
	    	// 지뢰를 클릭하면 게임 오버
	    	for (int y = 0; y < ModelManager.CellYSize; y++) {
				for (int x = 0; x < ModelManager.CellXSize; x++) {
					if (model.getCellProperty(x, y) == Core.Mined) {
						view.openCell(x, y, model.getCellProperty(x, y));
					}
				}
			}
	    	view.showResult(isTimeOver ? "TIME OVER" : "LOSE");
			//view.showResult("LOSE"); // 패배 시 0점과 함께 표시
			view.getMenuComponent().stopTimer();
	    }
	}

	public void changeCheckedState(int buttonPosX, int buttonPosY) {
		//체크 표시하는 메서드입니다. 커버상태인 셀 누를 때 상태 체크로 바꾼 후 뷰메니저에 전달
		if (model.getCellState(buttonPosX, buttonPosY) == CellState.Covered) {
			model.setCellState(buttonPosX, buttonPosY, CellState.Checked);
			view.showCellState(buttonPosX, buttonPosY, CellState.Checked);
			// 깃발 추가 시 남은 지뢰 수 감소
			model.decreaseMineCount();
			view.getMenuComponent().updateMineCount(model.getMineCount()); // MenuComponent에 접근
		} else if (model.getCellState(buttonPosX, buttonPosY) == CellState.Checked) {
			model.setCellState(buttonPosX, buttonPosY, CellState.Covered);
			view.showCellState(buttonPosX, buttonPosY, CellState.Covered);
			// 깃발 제거 시 남은 지뢰 수 증가
			model.increaseMineCount();
			view.getMenuComponent().updateMineCount(model.getMineCount()); // MenuComponent에 접근
		} else {
			return;
		}

		if (isVictory()) {
			view.showResult("WIN");
			Rank.addScore(MenuComponent.getDifficultyLevel(), menuComponent.getScore());
		}
	}

	public void changeQuestionState(int buttonPosX, int buttonPosY) {
		//체크에서 물음표로 이하 동일
		if (model.getCellState(buttonPosX, buttonPosY) == CellState.Covered) {
			model.setCellState(buttonPosX, buttonPosY, CellState.Question);
			view.showCellState(buttonPosX, buttonPosY, CellState.Question);
		} else if (model.getCellState(buttonPosX, buttonPosY) == CellState.Question) {
			model.setCellState(buttonPosX, buttonPosY, CellState.Covered);
			view.showCellState(buttonPosX, buttonPosY, CellState.Covered);
		} else
			return;
	}
	
	public boolean checkCost(String itemName, int cost) {
		if (itemName.equals("부활")) {
			return cost <= 1; // 부활 아이템은 최대 1개
		} else {
			return 0 <= cost && cost <= 3; // 다른 아이템들은 최대 3개
		}
	}

	public void clickedRankingMenu(){
		view.showRanking();
		view.updateItemCount();
	}

	public void clickedItemMenu() {
		view.showItem();
		getItemManager().getItem("힌트").initCost();
		getItemManager().getItem("추가").initCost();
		getItemManager().getItem("부활").initCost(); 
		view.getItemComponent().showItemCost();
		view.getItemComponent().showRemainingItemCost();

		MineSearch.setRemaining(0);
		TimePlus.setRemaining(0);
		view.updateItemCount();

		if (view.getItemComponent().getItemMouseListenerCnt() == 1) { // 리스너 중복 추가 방지
			view.addItemMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					JButton itemButton = (JButton) e.getSource();
					String buttonText = itemButton.getText();

					if (buttonText.equals("힌트") || buttonText.equals("추가") || buttonText.equals("부활")) {
						int buttonCost = getItemManager().getItem(buttonText).getCost();
						if (checkCost(buttonText, buttonCost)) {
							int result = (e.getButton() == MouseEvent.BUTTON1) ? 1 : // 좌클릭 +1
									(e.getButton() == MouseEvent.BUTTON3) ? -1 : 0; // 우클릭 -1
							if (result != 0) {
								if (buttonText.equals("부활")) {
								    int currentCost = getItemManager().getItem(buttonText).getCost();
								    if (result > 0 && currentCost == 0) {
								        getItemManager().getItem(buttonText).setCost(1);
								    } else if (result < 0 && currentCost > 0) {
								        getItemManager().getItem(buttonText).setCost(0);
								    }
								} else {
									getItemManager().getItem(buttonText).setCost(result);
								}
								int cost = getItemManager().getItem(buttonText).getCost();
								changeCost(cost, buttonText);
								view.getItemComponent().showItemCost();
								view.getItemComponent().showRemainingItemCost();
							}
						}
					}
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
			});
		}
	}

	public boolean checkCost(int cost) {
		if (0 <= cost && cost <= 3) {
			return true;
		}
		return false;
	}

	public void changeCost(int cost, String buttonText) {
	    GameItem item = getItemManager().getItem(buttonText);
	    item.setCost(cost);

	    int totalCost = getItemManager().getItem("힌트").getCost() +
	                    getItemManager().getItem("추가").getCost() +
	                    getItemManager().getItem("부활").getCost();

	    if (totalCost > 3) {
	        int excess = totalCost - 3;
	        item.setCost(item.getCost() - excess);
	    }

	    view.getItemComponent().showItemCost();
	    view.getItemComponent().showRemainingItemCost();
	}

	public void clickedMakeGame() {
		// 게임 시작하는 코드로 뷰메니저에서 설정된 x,y 가지고 와서 크기 할당한 이후 초기설정 실행합니다.
		ModelManager.CellXSize = view.getGameWidthSize();
		ModelManager.CellYSize = view.getGameHeightSize();

		model.setMineCount(ModelManager.CellYSize * ModelManager.CellXSize / 7);

		model.initNewGame();
		view.showGame(); // 게임 화면 표시
		view.getMenuComponent().resetUpperView(); // 타이머 및 UI 초기화
		view.addMineSweeperMouseListener(new MineSweeperMouseListener(this));
		if (view.getItemListenerCnt() == 0) { // 리스너 중복 추가 방지
			view.addItemListeners(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					useItem(e.getActionCommand());
				}
			});
		}
		if (getItemManager().getItem("부활").getCost() > 0) {
	        setReviveAvailable(true);
	        reviveUsed = false;
	        view.showMessage("부활 아이템이 활성화되었습니다!");
	    }
	}

	public CellComponent getCellComponent() {
		return cellComponent;
	}

	/*
	public void setDifficultyLevel(String level) {
	    menuComponent.setDifficultyLevel(level); // MenuComponent의 난이도 설정 메서드 호출
	}*/
	
	
	
	public static void main(String[] args) {
		new Core();
	}

}
