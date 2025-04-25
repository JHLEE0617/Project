package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Presenter.MineSweeperMouseListener;
import Presenter.Core; // Core 클래스를 import

public class MenuComponent {
   // 게임 실행 후 상단 메뉴 화면구현 클래스입니다.
   private JLabel timeView;
   private JLabel mineCountView; // 남은 지뢰 수를 표시할 JLabel 추가
   private JButton restartButton;
   private JButton backButton;
   private JLabel level;
   private Timer timer;
   private Core core;
   //수정 : MenuComponent가 초급, 중급, 고급 설정하는 곳에 있지 않아서 이를 활용하기 위해 static으로 만들어 사용했습니다.
   private static String difficultyLevel; // 난이도 정보를 저장할 변수

   public MenuComponent(Core core) {
      this.core = core; // Core 인스턴스 초기화
      initTimeView();
      initMineCountView(); // 남은 지뢰 수 초기화
      initButton();
      initBcak();

   }


   private void initButton() {
      restartButton = new JButton("재시작");
      restartButton.setName("restart");
      restartButton.setPreferredSize(new Dimension(100, 30));
      restartButton.setBackground(new Color(173, 216, 230)); // 연한 파란색
      restartButton.setForeground(Color.BLACK); // 텍스트 색상
   }

   private void initBcak() {
      backButton = new JButton("홈버튼");
      backButton.setName("back");
      backButton.setPreferredSize(new Dimension(8, 8));
      backButton.setBackground(new Color(173, 216, 230)); // 연한 파란색
      backButton.setForeground(Color.BLACK); // 텍스트 색상
   }

   private void initTimeView() {
      timeView = new JLabel();
      timeView.setFont(new Font("Serif", Font.BOLD, 20));
   }

   private void initMineCountView() {
      mineCountView = new JLabel("지뢰남은 수: " + core.getModel().getMineCount()); // 초기 지뢰 수 표시
      mineCountView.setFont(new Font("Serif", Font.BOLD, 20));
   }

   public JPanel getUpperView() {
      JPanel upperView = new JPanel();
      upperView.setLayout(new GridLayout(1, 5, 0, 0));
      upperView.setBackground( new Color(211, 211, 211));
      upperView.add(timeView);
      upperView.add(new JLabel(""));
      upperView.add(restartButton);
      upperView.add(new JLabel(""));
      upperView.add(mineCountView);

      return upperView;
   }



   public void addTime(int seconds) {
      if (timer != null) {
         timer.cancel();
      }
      int currentTime = Integer.parseInt(timeView.getText());
      startTimer(currentTime + seconds);
   }

   private void startTimer(int startTime) { // 시간 추가 아이템을 위한 새로운 시작 타이머 세팅 메서드
      timer = new Timer();
      TimerTask task = new TimerTask() {
         int time = startTime;

         @Override
         public void run() {
            if (time >= 0) {
               timeView.setText(String.valueOf(time--));
            } else {
               timer.cancel();
               core.gameOver(true);
            }
         }
      };
      timer.schedule(task, 0, 1000);
   }

   public void stopTimer() { // 게임 끝나면 타이머 종료!
      if (timer != null) {
         timer.cancel();
         timer = null;
      }
   }

   public void resetUpperView() {
      if (timer != null) {
         timer.cancel(); // 기존 타이머 중지
      }
      timeView.setText(""); // 시간 초기화
      mineCountView.setText("지뢰남은 수: " + core.getModel().getMineCount());
      startTimer(); // 타이머 시작
   }

   public void startTimer() {
      timer = new Timer();
      TimerTask task = new TimerTask() {
         int time = 1000; // 처음 타이머 초기화

         @Override
         public void run() {
            if (time >= 0) {
               timeView.setText(String.valueOf(time--)); // 시간이 감소
            } else {
               timer.cancel(); // 시간이 0이 되면 타이머 중지
               core.gameOver(true); // 게임 종료 처리 호출
            }
         }
      };
      timer.schedule(task, 0, 1000); // 0초 후에 시작하고 매초마다 실행
   }

   public void updateMineCount(int newCount) {
      mineCountView.setText("지뢰남은 수: " + newCount); // 남은 지뢰 수 업데이트
   }

   public int getScore() {
      int takenTime = Integer.parseInt(timeView.getText());
      int score = (1200 - takenTime) * 10 / 12;
      return score;
   }

   public static void setDifficultyLevel(String level){
      difficultyLevel = level;
   }

   public static String getDifficultyLevel(){
      return difficultyLevel;
   }

   public void addMineSweeperMouseListener(MineSweeperMouseListener mineSweeperMouseListener) {
      restartButton.addMouseListener(mineSweeperMouseListener);
      backButton.addMouseListener(mineSweeperMouseListener);
   }

}
