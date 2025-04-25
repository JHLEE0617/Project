package view;

import Presenter.RankingMenuButtonListener;
import RankPage.Rank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RankingComponent {

    private JPanel rankingPanel;
    private JButton backButton;

    //삭제 - 리스너 필요
//    public RankingComponent() {
//        initRankingPage();
//    }

    public RankingComponent(RankingMenuButtonListener rankingMenuButtonListener){
        initRankingPage(rankingMenuButtonListener);
    }

    //변경  - 리스너 매개변수로 입력
    private void initRankingPage(RankingMenuButtonListener rankingMenuButtonListener) {
        rankingPanel = new JPanel();
        rankingPanel.setLayout(null); // 절대 위치 사용
        rankingPanel.setPreferredSize(new Dimension(700, 500));
        rankingPanel.setBackground(new Color(240, 248, 255)); // 연한 파란색 배경

        JLabel titleLabel = new JLabel("랭킹 페이지");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBounds(250, 20, 200, 50); // 중앙 상단에 제목 배치
        rankingPanel.add(titleLabel);

        // 랭킹 데이터를 표시할 테이블
        String[] columnNames = { "순위","난이도", "점수" };
        Object[][] data = new Object[Rank.returnScoreList().size()][1];

        System.out.println(Rank.returnScoreList());
        int i = 0;
        //수정 : 랭킹 페이지 난이도 추가
        for(Map<String, Object> record : Rank.returnScoreList()){
            data[i] = new Object[] { i + 1,record.get("level").toString(), (int)record.get("score") }; // 순위와 점수를 배열에 추가
            i++;
        }


        JTable rankingTable = new JTable(data, columnNames);
        rankingTable.setFont(new Font("Serif", Font.PLAIN, 14));
        rankingTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        scrollPane.setBounds(100, 100, 500, 200); // 중앙에 테이블 배치
        rankingPanel.add(scrollPane);

        // 뒤로가기 버튼
        backButton = new JButton("뒤로가기");
        backButton.setBounds(300, 350, 100, 30);
        backButton.setBackground(new Color(173, 216, 230)); // 연한 파란색
        backButton.setForeground(Color.BLACK);

        //리스너 추가
        backButton.addActionListener(rankingMenuButtonListener);
        rankingPanel.add(backButton);
    }

    public JPanel getRankingPanel() {
        return rankingPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
