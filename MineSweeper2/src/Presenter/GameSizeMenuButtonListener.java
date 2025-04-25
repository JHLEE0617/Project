package Presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class GameSizeMenuButtonListener implements ActionListener{
 
    private Core core;
    
    public GameSizeMenuButtonListener(Core core) {
        this.core = core;
    }
 
    @Override
    public void actionPerformed(ActionEvent e) { //초기화면에서 시작이 눌리면 게임시작
        if(e.getActionCommand().equals("시작"))
            core.clickedItemMenu();
        else if(e.getActionCommand().equals("랭킹"))
            core.clickedRankingMenu();
    }
    
    
 
    
}