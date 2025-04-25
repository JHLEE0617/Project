package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Presenter.Core;
import Presenter.GameSizeMenuButtonListener;

public class GameSizeMenuComponent {

    private JLabel levelLabel;
    private JButton makeButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton rankingButton; // 랭킹 버튼 추가
    private int gameWidth;
    private int gameHeight;
    private Image backgroundImage;

    public GameSizeMenuComponent(GameSizeMenuButtonListener gameSizeMenuButtonListener) {
        // 배경 이미지 로드
        backgroundImage = new ImageIcon("resources/8629de05-0125-4706-abcf-815d8f978af3.png").getImage();

        levelLabel = new JLabel("난이도");
        makeButton = new JButton("시작");
        makeButton.addActionListener(gameSizeMenuButtonListener);

        easyButton = new JButton("초급");
        easyButton.addActionListener(e -> setSize(9, 9, "초급"));

        mediumButton = new JButton("중급");
        mediumButton.addActionListener(e -> setSize(14, 12, "중급"));

        hardButton = new JButton("고급");
        hardButton.addActionListener(e -> setSize(20, 14, "고급"));

        // 랭킹 버튼 초기화
        rankingButton = new JButton("랭킹");
        rankingButton.setBackground(new Color(100, 149, 237)); // 연한 파란색
        rankingButton.setForeground(Color.WHITE); // 텍스트 색상
        rankingButton.addActionListener(gameSizeMenuButtonListener); // 클릭 시 랭킹 표시 메서드 호출
    }

    private void setSize(int width, int height, String level) {
        levelLabel.setText("난이도: " + level);
        MenuComponent.setDifficultyLevel(level);
        this.gameWidth = width;
        this.gameHeight = height;
    }

    private void showRanking() {
//        RankingComponent rankingPage = new RankingComponent();
//        JPanel rankingPanel = rankingPage.getRankingPanel();
//
//        // 기존 패널을 랭킹 패널로 교체하는 로직 구현
//        Core.getView().getMainFrame().setContentPane(rankingPanel); // 예시: 메인 프레임에 패널 교체
//        Core.getInstance().getMainFrame().revalidate();
//        Core.getInstance().getMainFrame().repaint();
//
//        // 뒤로가기 버튼 이벤트 추가
//        rankingPage.getBackButton().addActionListener(e -> {
//            Core.getInstance().getMainFrame().setContentPane(getGameSizeMenuPanel());
//            Core.getInstance().getMainFrame().revalidate();
//            Core.getInstance().getMainFrame().repaint();
//        });
    }

    public JPanel getGameSizeMenuPanel() {
        JPanel gameSizeMenuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
            }
        };

        gameSizeMenuPanel.setPreferredSize(new Dimension(700, 500));
        gameSizeMenuPanel.setLayout(null); // 절대 위치를 위해 null 레이아웃 사용

        easyButton.setBackground(new Color(173, 216, 230));
        easyButton.setForeground(Color.BLACK);

        mediumButton.setBackground(new Color(255, 255, 224));
        mediumButton.setForeground(Color.BLACK);

        hardButton.setBackground(new Color(255, 182, 193));
        hardButton.setForeground(Color.BLACK);

        makeButton.setBackground(new Color(144, 238, 144));
        makeButton.setForeground(Color.BLACK);

        // 버튼 크기 설정
        int buttonWidth = 100;
        int buttonHeight = 30;
        int xOffset = 350; // 중앙 정렬용
        int yOffset = 127; // 버튼 그룹 위치 조정

        // 중급 버튼 위치 (중앙에 배치)
        mediumButton.setBounds((gameSizeMenuPanel.getWidth() - buttonWidth) / 2 + xOffset, 150 + yOffset, buttonWidth, buttonHeight);

        // 초급 버튼 위치 (중급 버튼의 왼쪽)
        easyButton.setBounds(mediumButton.getX() - buttonWidth - 10, 150 + yOffset, buttonWidth, buttonHeight); // 중급 버튼 왼쪽에 배치

        // 고급 버튼 위치 (중급 버튼의 오른쪽)
        hardButton.setBounds(mediumButton.getX() + buttonWidth + 10, 150 + yOffset, buttonWidth, buttonHeight); // 중급 버튼 오른쪽에 배치

        // 난이도 레이블 위치 (중앙에 배치)
        //levelLabel.setBounds((gameSizeMenuPanel.getWidth() - 200) / 2 + xOffset, 100 + yOffset, 200, 30);

        // 시작 버튼 위치 (중급 버튼 아래)
        makeButton.setBounds((gameSizeMenuPanel.getWidth() - buttonWidth) / 2 + xOffset -75, 255 + yOffset, buttonWidth, buttonHeight); // 중앙에 배치
        // 랭킹 버튼 위치
        rankingButton.setBounds((gameSizeMenuPanel.getWidth() - buttonWidth) / 2 + xOffset+75, 255 + yOffset, buttonWidth, buttonHeight);

        // 패널에 버튼 추가
        gameSizeMenuPanel.add(easyButton);
        gameSizeMenuPanel.add(mediumButton);
        gameSizeMenuPanel.add(hardButton);
        gameSizeMenuPanel.add(levelLabel);
        gameSizeMenuPanel.add(makeButton);
        gameSizeMenuPanel.add(rankingButton); // 랭킹 버튼 추가

        return gameSizeMenuPanel;
    }

    public int getGameWidthSize() {
        return gameWidth;
    }

    public int getGameHeightSize() {
        return gameHeight;
    }

    public void clearText() {
        //restart에서 초기화용도
        levelLabel.setText("난이도: 초급");
        gameWidth = 9;
         gameHeight = 9;
     }

}