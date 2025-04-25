package Presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingMenuButtonListener implements ActionListener {
    Core core;
    
    //수정 - core 입력
    public RankingMenuButtonListener(Core core){
        this.core = core;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("뒤로가기")) {
            core.init();
        }
    }
}
