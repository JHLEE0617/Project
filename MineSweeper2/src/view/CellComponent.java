package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Presenter.MineSweeperMouseListener;
import mine.ModelManager;

public class CellComponent {

	private JButton[][] cellButtons;

	public CellComponent() {

	}

	public void initCellComponent() { // 모델 메니저 x,y 사이즈 맞춰서 버튼 배열 초기화
		cellButtons = new JButton[ModelManager.CellYSize][ModelManager.CellXSize];
		initButtons();
	}
	
	
	 private void initButtons() { // 모델 메니저 x,y 사이즈 맞춰서 버튼 배열 초기화
	      for (int y = 0; y < ModelManager.CellYSize; y++) {
	         for (int x = 0; x < ModelManager.CellXSize; x++) {
	            cellButtons[y][x] = new JButton();
	            cellButtons[y][x].setPreferredSize(new Dimension(50, 50));
	            cellButtons[y][x].setName("(" + x + "," + y + ")");
	            cellButtons[y][x].setBackground(new Color(255, 255, 224));
	            
	         }
	      }
	   }
	 
	 public void showMineHint(int x, int y) {
		 Image hintImage = new ImageIcon("resources/use_hint.png").getImage();
		 Image resizedHintImage = hintImage.getScaledInstance(cellButtons[y][x].getWidth(), cellButtons[y][x].getHeight(), Image.SCALE_SMOOTH);
		 ImageIcon hintImageIcon = new ImageIcon(resizedHintImage);	
		 cellButtons[y][x].setIcon(hintImageIcon);
	     cellButtons[y][x].setBackground(new Color(135, 206, 235));
	     //cellButtons[y][x].setText("Hint"); //!
	 }
	 
	
	public void addMineSweeperMouseListener(MineSweeperMouseListener mineSweeperMouseListener) {
		//셀들에 마우스리스너 할당
		for (int y = 0; y < ModelManager.CellYSize; y++) {
			for (int x = 0; x < ModelManager.CellXSize; x++) {
				cellButtons[y][x].addMouseListener(mineSweeperMouseListener);
			}
		}
	}

	public void resetCellView() {
		//처음 셀들이 덮여있는 상태 초기화
		for (int y = 0; y < ModelManager.CellYSize; y++) {
			for (int x = 0; x < ModelManager.CellXSize; x++) {
				cellButtons[y][x].setText("");
				cellButtons[y][x].setForeground(Color.black);
				cellButtons[y][x].setBackground(null);
			}
		}
	}

	public JPanel getCellView() {
		JPanel cellView = new JPanel();

		cellView.setLayout(new GridLayout(ModelManager.CellYSize, ModelManager.CellXSize));

		for (int y = 0; y < ModelManager.CellYSize; y++) {
			for (int x = 0; x < ModelManager.CellXSize; x++)
				cellView.add(cellButtons[y][x]);
		}

		return cellView;
	}

	public void showMine(int x, int y) {
	    // 이미지 로드
	    ImageIcon mineIcon = new ImageIcon("resources/스크린샷 2024-11-25 124818.png");

	    // 버튼 크기에 맞게 이미지 크기 조정
	    Image image = mineIcon.getImage();
	    Image scaledImage = image.getScaledInstance(cellButtons[y][x].getWidth(), cellButtons[y][x].getHeight(), Image.SCALE_SMOOTH);
	    
	    // 버튼 배경으로 이미지 설정
	    cellButtons[y][x].setIcon(new ImageIcon(scaledImage));
	    
	    // 텍스트 지우기 및 기타 설정
	    
	    cellButtons[y][x].setBorderPainted(false); // 버튼 테두리 제거 (원하는 경우)
	}

	public void showProperty(int x, int y, int property) {
		//지뢰 제외한 셀 오픈 시 화면 구현
		cellButtons[y][x].setFont(new Font("Serif", Font.BOLD, 30));
		cellButtons[y][x].setText(String.valueOf(property));
		cellButtons[y][x].setForeground(Color.WHITE); // 하얀색으로 설정
	}
	

	public void setCellBackgound(int x, int y, Color color) {
		// 지뢰 터질때 빨간색 만들 때 씁니다
		cellButtons[y][x].setBackground(color);
	}

	public void markChecked(int x, int y) {
		// 체크 표시 화면 구현
		cellButtons[y][x].setIcon(null);
		cellButtons[y][x].setFont(new Font("Serif", Font.BOLD, 15));
		cellButtons[y][x].setText("🚩");
		cellButtons[y][x].setForeground(Color.red);
	}

	public void markQuestion(int x, int y) {
		// 물음표 표시 화면 구현
		cellButtons[y][x].setFont(new Font("Serif", Font.BOLD, 30));
		cellButtons[y][x].setText("?");
		cellButtons[y][x].setForeground(Color.green);
	}

	public void markCoverd(int x, int y) {
		// 체크, 물음표에서 원래상태로 돌릴 떄 씁니다
		cellButtons[y][x].setText("");
		cellButtons[y][x].setForeground(Color.black);
	}

}
