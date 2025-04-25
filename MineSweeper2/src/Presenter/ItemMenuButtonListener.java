package Presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemMenuButtonListener implements ActionListener{
 
    private Core core;
    
    public ItemMenuButtonListener(Core core) {
        this.core = core;
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("start")) {
        	core.clickedMakeGame();        	
        }
        if(e.getActionCommand().equals("back")) {
        	core.init();     	
        }
    }
}