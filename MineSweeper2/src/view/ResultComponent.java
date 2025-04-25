package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultComponent {
	// 게임 결과창 화면입니다.
	private JFrame resultFrame;
	private JLabel resultSentenceView;
	private JLabel scoreView;

	public ResultComponent() {
		initSentenceView();
		initscoreView();
		initFrame();

	}

	private void initSentenceView() {
		// 승패여부  표시
		resultSentenceView = new JLabel();
		resultSentenceView.setFont(new Font("Serif", Font.BOLD, 40));
	}

	private void initscoreView() {
		// 스코어 표시
		scoreView = new JLabel();
		scoreView.setFont(new Font("Serif", Font.BOLD, 40));
	}

	private void initFrame() {
		// 결과 창
		resultFrame = new JFrame("Result");
		resultFrame.setLocation(200, 200);
		resultFrame.add(getResultPanel());
		resultFrame.setSize(new Dimension(300, 300));
	}

	public void showResult(int score) {
		// 승패 여부 판별
		if (score > 0) {
			resultSentenceView.setText("WIN");
			scoreView.setText("SCORE    " + String.valueOf(score));
		}
		else {
			resultSentenceView.setText("LOSE");
		}
		resultFrame.setVisible(true);
	}

	private JPanel getResultPanel() {
		// 창 내부 요소 세팅
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BorderLayout());
		resultPanel.add(resultSentenceView, BorderLayout.NORTH);
		resultPanel.add(scoreView, BorderLayout.CENTER);
		return resultPanel;
	}

}