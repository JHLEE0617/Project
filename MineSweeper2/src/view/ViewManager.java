package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Presenter.Core;
import Presenter.GameSizeMenuButtonListener;
import Presenter.ItemMenuButtonListener;
import Presenter.MineSweeperMouseListener;
import Presenter.RankingMenuButtonListener;
import mine.CellState;
import mine.ModelManager;

public class ViewManager {

    private JFrame mineSweeperFrame;
    private CellComponent cellComponent;
    private MenuComponent menuComponent;
    private Core core;
    private ResultComponent resultComponent;
    private GameSizeMenuComponent gameSizeMenuComponent;
    private JButton mineSearchButton;
    private JButton timePlusButton;
    private JButton reviveButton;
    private ItemComponent itemComponent;
    private JButton homeButton; // 홈 버튼 추가
    private JButton rankingButton; // 랭킹 버튼 추가
    private RankingComponent          rankingComponent;
    private RankingMenuButtonListener rankingMenuButtonListener;

    public ViewManager(GameSizeMenuButtonListener gameSizeMenuButtonListener, ItemMenuButtonListener itemMenuButtonListener,RankingMenuButtonListener rankingMenuButtonListener, Core core) {
        mineSweeperFrame = new JFrame("지뢰찾기");
        cellComponent = new CellComponent();
        gameSizeMenuComponent = new GameSizeMenuComponent(gameSizeMenuButtonListener);
        resultComponent = new ResultComponent();
        mineSweeperFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.core = core;
        this.menuComponent = new MenuComponent(core);
        mineSearchButton = new JButton("힌트");
        timePlusButton = new JButton("추가");
        reviveButton = new JButton("부활");
        setItemButtons();
        homeButton = new JButton("홈"); // 홈 버튼 초기화
        rankingButton = new JButton("랭킹판"); // 랭킹 버튼 초기화
        itemComponent = new ItemComponent(itemMenuButtonListener, core);

        // 버튼에 리스너 추가
        homeButton.addActionListener(new NavigatorActionListener());
        rankingButton.addActionListener(new NavigatorActionListener());
        
        rankingComponent = new RankingComponent(rankingMenuButtonListener);
        this.rankingMenuButtonListener = rankingMenuButtonListener;
        
    }
    
    private void setItemButtons() {
    	Image hintImage = new ImageIcon("resources/hint.png").getImage();
		Image timeImage = new ImageIcon("resources/time.png").getImage();
		Image heartImage = new ImageIcon("resources/heart.png").getImage();
		
		Image resizedHintImage = hintImage.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		Image resizedTimeImage = timeImage.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		Image resizedHeartImage = heartImage.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		
		ImageIcon hintImageIcon = new ImageIcon(resizedHintImage);
		ImageIcon timeImageIcon = new ImageIcon(resizedTimeImage);
		ImageIcon heartImageIcon = new ImageIcon(resizedHeartImage);
		
		mineSearchButton.setIcon(hintImageIcon);
		mineSearchButton.setBackground(new Color(135, 206, 235));
		mineSearchButton.setFont(new Font("Dialog", Font.BOLD, 15));
		
		timePlusButton.setIcon(timeImageIcon);
		timePlusButton.setFont(new Font("Dialog", Font.BOLD, 15));
		timePlusButton.setBackground(new Color(245, 220, 183));
		
		reviveButton.setIcon(heartImageIcon);
		reviveButton.setFont(new Font("Dialog", Font.BOLD, 15));
		reviveButton.setBackground(new Color(255, 192, 203));
    }

    public void updateItemCount() {
    	itemComponent.showItemCost();
    	itemComponent.showRemainingItemCost();
        mineSweeperFrame.revalidate();
        mineSweeperFrame.repaint();
    }

    public void showGameSizeMenu() {
        if (ModelManager.CellYSize != 0 && ModelManager.CellXSize != 0) {
            mineSweeperFrame.getContentPane().removeAll();
        }
        gameSizeMenuComponent.clearText();
        mineSweeperFrame.add(gameSizeMenuComponent.getGameSizeMenuPanel(), BorderLayout.CENTER);
        mineSweeperFrame.pack();
        mineSweeperFrame.setVisible(true);
    }

    public void showGame() {
        initCellComponent();
        mineSweeperFrame.getContentPane().removeAll();
        mineSweeperFrame.add(menuComponent.getUpperView(), BorderLayout.NORTH);
        mineSweeperFrame.add(cellComponent.getCellView(), BorderLayout.CENTER);
        updateItemCount();

        // 하단 패널 구성
        JPanel itemButtonPanel = new JPanel();
        itemButtonPanel.setLayout(new GridLayout(1, 5, 35, 2));
        itemButtonPanel.add(new JLabel(""));
        itemButtonPanel.add(mineSearchButton);
        itemButtonPanel.add(timePlusButton);
        itemButtonPanel.add(reviveButton);
        itemButtonPanel.add(new JLabel(""));

        JPanel itemCountPanel = new JPanel();
        itemCountPanel.setLayout(new GridLayout(1, 5, 35, 2)); 
        itemCountPanel.add(new JLabel(""));
        itemCountPanel.add(itemComponent.getMineSearchCostLabel());
        itemCountPanel.add(itemComponent.getTimePlusCostLabel());
        itemCountPanel.add(itemComponent.getReviveCostLabel());
        itemCountPanel.add(new JLabel(""));

        JPanel navigatorPanel = new JPanel();
        navigatorPanel.setLayout(new GridLayout(1, 2));
        navigatorPanel.add(homeButton);
        navigatorPanel.add(rankingButton);
        
        updateItemCount();

        // 하단 패널 구성
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(itemButtonPanel, BorderLayout.NORTH);
        bottomPanel.add(itemCountPanel, BorderLayout.CENTER);
        bottomPanel.add(navigatorPanel, BorderLayout.SOUTH); // 내비게이터 패널 추가

        // 하단 패널을 프레임에 추가
        mineSweeperFrame.add(bottomPanel, BorderLayout.SOUTH);

        mineSweeperFrame.pack(); // 레이아웃 업데이트
        mineSweeperFrame.setVisible(true); // 화면 표시
    }

    public void showItem() {
        mineSweeperFrame.getContentPane().removeAll();
        mineSweeperFrame.add(itemComponent.getItemPanel(), BorderLayout.CENTER);
        mineSweeperFrame.pack();
        mineSweeperFrame.setVisible(true);
    }

    public void showMineHint(int x, int y) {
        cellComponent.showMineHint(x, y);
    }

    private class NavigatorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button == homeButton) {
                core.newGame(); 
            } else if (button == rankingButton) {
                System.out.println("랭킹판 버튼 클릭");
                core.clickedRankingMenu();
            }
        }
    }

    public void showRanking(){
        this.rankingComponent = new RankingComponent(rankingMenuButtonListener);
        mineSweeperFrame.getContentPane().removeAll();
        mineSweeperFrame.add(rankingComponent.getRankingPanel(), BorderLayout.CENTER);
        mineSweeperFrame.pack();
        mineSweeperFrame.setVisible(true);
    }
    
    
    public void addItemListeners(ActionListener listener) {
        ActionListener wrappedListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = e.getActionCommand();
                core.useItem(itemName);
                updateItemCount();
            }
        };

        mineSearchButton.addActionListener(wrappedListener);
        timePlusButton.addActionListener(wrappedListener);
        reviveButton.addActionListener(wrappedListener);
    }

    public int getItemListenerCnt() {  //리스너 중복 추가 방지에 사용
        return mineSearchButton.getActionListeners().length;
    }

    public MenuComponent getMenuComponent() {
        return menuComponent;
    }

    public void resetView() {
        cellComponent.resetCellView();
        menuComponent.resetUpperView();
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(mineSweeperFrame, message);
    }

    public void showResult(String Result) {
        if (Result.equals("WIN"))
            resultComponent.showResult(menuComponent.getScore());
        else
            resultComponent.showResult(0);
    }

    public void addMineSweeperMouseListener(MineSweeperMouseListener mineSweeperMouseListener) {
        cellComponent.addMineSweeperMouseListener(mineSweeperMouseListener);
        menuComponent.addMineSweeperMouseListener(mineSweeperMouseListener);
    }

    public void addItemMouseListener(MouseListener itemMouseListener) {
        itemComponent.addItemMouseListener(itemMouseListener);
    }

    public int getItemMouseListenerCnt() { // 리스너 중복 추가 방지에 사용
        return itemComponent.getItemMouseListenerCnt();
    }

    public void openCell(int x, int y, int cellProperty) {
        int property = cellProperty;

        if (property == 0)
            cellComponent.setCellBackgound(x, y, new Color(0, 51, 102));
        else if (property == Core.Mined) {
            cellComponent.showMine(x, y);
        } else {
            cellComponent.showProperty(x, y, property);
            cellComponent.setCellBackgound(x, y, new Color(0, 51, 102));
        }
    }

    public void showCellState(int x, int y, CellState cellState) {
        if (cellState == CellState.Checked)
            cellComponent.markChecked(x, y);
        else if (cellState == CellState.Question)
            cellComponent.markQuestion(x, y);
        else if (cellState == CellState.Covered)
            cellComponent.markCoverd(x, y);
    }

    public int getGameWidthSize() {
        return gameSizeMenuComponent.getGameWidthSize();
    }

    public int getGameHeightSize() {
        return gameSizeMenuComponent.getGameHeightSize();
    }

    private void initCellComponent() {
        cellComponent.initCellComponent();
    }

    public void setMenuComponent(MenuComponent menuComponent) {
        this.menuComponent = menuComponent; 
    }

    public ItemComponent getItemComponent() {
        return itemComponent;
    }
}
