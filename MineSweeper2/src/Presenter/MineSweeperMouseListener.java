package Presenter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class MineSweeperMouseListener implements MouseListener {
   // 마우스 클릭 반응 클래스
   private Core core;
   private boolean leftClicked = false;
   private boolean rightClicked = false;

   public MineSweeperMouseListener(Core core) {
      this.core = core;
   }

   
   public void mousePressed(MouseEvent e) {
      JButton button = (JButton) e.getSource();
      
      if (button.getName().equals("restart")) {
         core.clickedMakeGame();
      } else if (button.getName().equals("back")) {
         core.newGame();
      } else {
         int[] cellPosition = getCellPosition(e);

         int cellPosX = cellPosition[0];
         int cellPosY = cellPosition[1];

         if (e.getButton() == MouseEvent.BUTTON1) { // 왼쪽 버튼 클릭
            leftClicked = true;
            core.checkSingleCell(cellPosX, cellPosY);

            // 클릭한 셀의 색깔 변경
            core.getCellComponent().setCellBackgound(cellPosX, cellPosY, Color.LIGHT_GRAY); // 예시로 회색
         }

         if (e.getButton() == MouseEvent.BUTTON3) { // 오른쪽 버튼 클릭
            rightClicked = true;
            core.changeCheckedState(cellPosX, cellPosY);
         }

         if (e.getButton() == MouseEvent.BUTTON2) { // 휠클릭 (물음표 표시
            core.changeQuestionState(cellPosX, cellPosY);
         }

         if (leftClicked && rightClicked) { // 좌클릭 우클릭 동시 (주변 체크 기능)
            core.checkAroundCells(cellPosX, cellPosY);
         }
      }
   }

   public void mouseReleased(MouseEvent e) {
      if (e.getButton() == 1) {
         leftClicked = false;
      }
      if (e.getButton() == 3) {
         rightClicked = false;
      }
   }

   private int[] getCellPosition(MouseEvent e) {
      // 클릭한 셀 위치 가져오는 메서드입니다. (2,3)이면 x=2,y=3
      JButton button = (JButton) e.getSource();

      String buttonName = button.getName();

      buttonName = buttonName.replaceAll("^[(]|[)]$", "");

      String[] buttonPositionString = buttonName.split(",");

      int buttonPosX = Integer.parseInt(buttonPositionString[0]);
      int buttonPosY = Integer.parseInt(buttonPositionString[1]);

      int[] buttonPosition = { buttonPosX, buttonPosY };

      return buttonPosition;
   }

   public void mouseEntered(MouseEvent arg0) {
   }

   public void mouseExited(MouseEvent arg0) {
   }

   public void mouseClicked(MouseEvent e) {

   }

}
