package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Presenter.Core;
import Presenter.ItemMenuButtonListener;

public class ItemComponent {
	private Core core;
	private JLabel instruction, instruction2, costLabel, itemLabel;
	private JButton mineSearchButton, timePlusButton, reviveButton;
	private JLabel mineSearchCost, timePlusCost, reviveCost;
    private JButton startButton, backButton;
    
    public ItemComponent(ItemMenuButtonListener itemMenuButtonListener, Core core) {
    	this.core = core;
        initItemComponent(itemMenuButtonListener);
    }
    
	public JPanel getItemPanel() {
		JPanel itemPanel = new JPanel() {
			// 배경 이미지 설정
            private Image backgroundImage = new ImageIcon("resources/8629de05-0125-4706-abcf-815d8f978af3.png").getImage();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
		itemPanel.setPreferredSize(new Dimension(700, 500));
		itemPanel.setLayout(new BorderLayout());
		
		// back 버튼 위치 : 왼쪽 상단
		JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.add(backButton, BorderLayout.WEST);
		westPanel.setOpaque(false); // 투명
		JPanel backButtonPanel = new JPanel(new BorderLayout());
        backButtonPanel.add(westPanel, BorderLayout.NORTH);
        backButtonPanel.setOpaque(false); // 투명
                
        JPanel buttonsPanel = new JPanel(); // 아이템들 위치 중앙으로
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 85, 0));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(getBottomPanel());
        buttonsPanel.setOpaque(false); // 투명
        
        JPanel componentPanel = new JPanel(new BorderLayout());
        componentPanel.add(getTopPanel(), BorderLayout.CENTER);
        componentPanel.add(buttonsPanel, BorderLayout.SOUTH);
        componentPanel.setOpaque(false); // 투명
		
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(componentPanel, BorderLayout.CENTER);
		southPanel.add(new JLabel(" "), BorderLayout.SOUTH); // 하단 공백
		southPanel.setOpaque(false); // 투명
		
		JPanel south2Panel = new JPanel(new BorderLayout());
		south2Panel.add(southPanel, BorderLayout.CENTER);
		south2Panel.add(new JLabel(" "), BorderLayout.SOUTH); // 하단 공백
		south2Panel.setOpaque(false); // 투명
        
        itemPanel.add(backButtonPanel, BorderLayout.NORTH);
        itemPanel.add(south2Panel, BorderLayout.CENTER);
        itemPanel.add(new JLabel(" "), BorderLayout.SOUTH); // 하단 공백
        itemPanel.setOpaque(false); // 배경 이미지 보이도록 투명하게
        return itemPanel;
	}
	
	public void initItemComponent(ItemMenuButtonListener itemMenuButtonListener) {
		initLabels();
		initMineSearch();
		initTimePlus();
		initRevive();
		
		backButton = new JButton();
		backButton.setBackground(new Color(255, 182, 193));
		backButton.setBorderPainted(false);
		backButton.setOpaque(false); // 투명
        backButton.setActionCommand("back");
        backButton.addActionListener(itemMenuButtonListener);
		
        startButton = new JButton("시작");
        startButton.setFont(new Font("Dialog", Font.BOLD, 15));
        startButton.setBackground(new Color(144, 238, 144)); // 연한 초록색
        startButton.setActionCommand("start");
        startButton.addActionListener(itemMenuButtonListener);
        
        setItemImages();
    }
	
	private void initLabels() {
		instruction = new JLabel("아이템 3개를 선택하세요", JLabel.CENTER);
		instruction.setFont(new Font("Dialog", Font.BOLD, 18));
		
		instruction2 = new JLabel("(좌클릭 - 선택 / 우클릭 - 취소)", JLabel.CENTER);
		instruction2.setForeground(new Color(59, 56, 56));
		
		costLabel = new JLabel("선택 가능한 아이템 개수 3개", JLabel.CENTER);
		costLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		costLabel.setForeground(new Color(245, 51, 88));
		
		itemLabel = new JLabel("<html>힌트 : 찾지 못한 지뢰 중 1개를 알려준다.<br>"
				+ "시간 추가 : 남은 시간이 5초 늘어난다.<br>"
				+ "부활 : 지뢰를 밟아도 한 번 부활한다.</html>", JLabel.CENTER);
		itemLabel.setForeground(new Color(82, 82, 82));
	}
	
	private void initMineSearch() {
		mineSearchButton = new JButton("힌트");
		mineSearchButton.setFont(new Font("Dialog", Font.BOLD, 15));
		mineSearchButton.setBackground(new Color(135, 206, 235));
		
		mineSearchCost = new JLabel("개수 : 0", JLabel.CENTER);
		mineSearchCost.setFont(new Font("Dialog", Font.BOLD, 15));
	}
	
	private void initTimePlus() {
		timePlusButton = new JButton("추가");
		timePlusButton.setFont(new Font("Dialog", Font.BOLD, 15));
		timePlusButton.setBackground(new Color(245, 220, 183));
		
		timePlusCost = new JLabel("개수 : 0", JLabel.CENTER);
		timePlusCost.setFont(new Font("Dialog", Font.BOLD, 15));
	}
	
	private void initRevive() {
		reviveButton = new JButton("부활");
		reviveButton.setFont(new Font("Dialog", Font.BOLD, 15));
		reviveButton.setBackground(new Color(255, 192, 203));
		
		reviveCost = new JLabel("개수 : 0" , JLabel.CENTER);
		reviveCost.setFont(new Font("Dialog", Font.BOLD, 15));
	}
	
	private void setItemImages() {
    	Image hintImage = new ImageIcon("resources/hint.png").getImage();
		Image timeImage = new ImageIcon("resources/time.png").getImage();
		Image heartImage = new ImageIcon("resources/heart.png").getImage();
		Image backImage = new ImageIcon("resources/back.png").getImage();
		
		Image resizedHintImage = hintImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		Image resizedTimeImage = timeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		Image resizedHeartImage = heartImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		Image resizedBackImage = backImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		
		ImageIcon hintImageIcon = new ImageIcon(resizedHintImage);
		ImageIcon timeImageIcon = new ImageIcon(resizedTimeImage);
		ImageIcon heartImageIcon = new ImageIcon(resizedHeartImage);
		ImageIcon backImageIcon = new ImageIcon(resizedBackImage);
		
		mineSearchButton.setIcon(hintImageIcon);
		timePlusButton.setIcon(timeImageIcon);
		reviveButton.setIcon(heartImageIcon);
		backButton.setIcon(backImageIcon);
    }
	
	public void showItemCost() {
	    mineSearchCost.setText("개수 : " + core.getItemManager().getItem("힌트").getCost());
	    timePlusCost.setText("개수 : " + core.getItemManager().getItem("추가").getCost());
	    reviveCost.setText("개수 : " + (core.getItemManager().getItem("부활").getCost() > 0 ? "1" : "0"));
	}
	
	public void showRemainingItemCost() {
		int mineSearch = core.getItemManager().getItem("힌트").getCost();
	    int timePlus = core.getItemManager().getItem("추가").getCost();
	    int revive = core.getItemManager().getItem("부활").getCost() > 0 ? 1 : 0;
	    int result = 3 - (mineSearch + timePlus + revive);
	    //System.out.printf("%d %d %d\n", mineSearch, timePlus, revive);
	    costLabel.setText("선택 가능한 아이템 개수 " + result + "개");
	}
	
	private JPanel getTopPanel() {
		JPanel topTextPanel = new JPanel(new GridLayout(3, 1));
        topTextPanel.add(instruction);
        topTextPanel.add(instruction2);
        topTextPanel.add(costLabel);
        
        JLabel emptyLabel = (new JLabel("<html><br><br><br><br><br></html>")); // 상단 공백
        JPanel textPanel = new JPanel();
        textPanel.add(topTextPanel);
        textPanel.add(itemLabel);
        textPanel.setOpaque(true); // 텍스트 박스 보이도록 불투명하게
        
        JPanel textBoxPanel = new JPanel(new BorderLayout());
        textBoxPanel.add(emptyLabel, BorderLayout.NORTH);
        textBoxPanel.add(textPanel, BorderLayout.CENTER);
        textBoxPanel.setOpaque(false); // 상단 공백 안 보이도록 투명하게
        
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(new JLabel(""));  
        topPanel.add(textBoxPanel);
        topPanel.add(new JLabel(""));
        topPanel.setOpaque(false); // 배경 이미지 보이도록 투명하게
        return topPanel;
	}

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 4, 30, 0));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(mineSearchButton);
        bottomPanel.add(timePlusButton);
        bottomPanel.add(reviveButton);
        bottomPanel.add(new JLabel(""));
        
        JPanel minePanel = new JPanel(new BorderLayout());
        mineSearchCost.setOpaque(true); // 텍스트 박스 보이도록 불투명하게
        minePanel.add(new JLabel(" "), BorderLayout.SOUTH);
        minePanel.add(mineSearchCost, BorderLayout.CENTER);
        minePanel.setOpaque(false); // 공백 투명하게
        
        JPanel timePanel = new JPanel(new BorderLayout());
        timePlusCost.setOpaque(true); // 텍스트 박스 보이도록 불투명하게
        timePanel.add(new JLabel(" "), BorderLayout.SOUTH);
        timePanel.add(timePlusCost, BorderLayout.CENTER);
        timePanel.setOpaque(false); // 공백 투명하게
        
        JPanel revivePanel = new JPanel(new BorderLayout());
        reviveCost.setOpaque(true); // 텍스트 박스 보이도록 불투명하게
        revivePanel.add(new JLabel(" "), BorderLayout.SOUTH);
        revivePanel.add(reviveCost, BorderLayout.CENTER);
        revivePanel.setOpaque(false); // 공백 투명하게
        
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(minePanel);
        bottomPanel.add(timePanel);
        bottomPanel.add(revivePanel);
        bottomPanel.add(new JLabel(""));
        
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(startButton);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(new JLabel(""));
         
        bottomPanel.setOpaque(false); // 배경 이미지 보이도록 투명하게
        return bottomPanel;
    }
    
    public JLabel getMineSearchCostLabel() {
        return mineSearchCost;
    }

    public JLabel getTimePlusCostLabel() {
        return timePlusCost;
    }
    
    public JLabel getReviveCostLabel() {
    	return reviveCost;
    }

    public void addItemMouseListener(MouseListener itemMouseListener) {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                String itemName = button.getText();
                int currentCost = core.getItemManager().getItem(itemName).getCost();
                
                if (e.getButton() == MouseEvent.BUTTON1 && currentCost < 3) {
                    core.getItemManager().getItem(itemName).setCost(currentCost + 1);
                } else if (e.getButton() == MouseEvent.BUTTON3 && currentCost > 0) {
                    core.getItemManager().getItem(itemName).setCost(currentCost - 1);
                }
                
                core.changeCost(core.getItemManager().getItem(itemName).getCost(), itemName);
                showItemCost();
                showRemainingItemCost();
            }
        };

        mineSearchButton.addMouseListener(adapter);
        timePlusButton.addMouseListener(adapter);
        reviveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int currentCost = core.getItemManager().getItem("부활").getCost();
                if (e.getButton() == MouseEvent.BUTTON1 && currentCost == 0) {
                    core.getItemManager().getItem("부활").setCost(1);
                } else if (e.getButton() == MouseEvent.BUTTON3 && currentCost > 0) {
                    core.getItemManager().getItem("부활").setCost(0);
                }
                core.changeCost(core.getItemManager().getItem("부활").getCost(), "부활");
                showItemCost();
                showRemainingItemCost();
            }
        });
    }
	
	public int getItemMouseListenerCnt() {
		return mineSearchButton.getMouseListeners().length;
	}
}