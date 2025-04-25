package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NavigatorComponent {

    private JPanel navPanel;
    private JButton homeButton;
    private JButton rankingButton;

    public NavigatorComponent(ActionListener actionListener) {
        navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout()); 

       
        homeButton = new JButton("홈");
        rankingButton = new JButton("랭킹판");

        homeButton.addActionListener(actionListener);
        rankingButton.addActionListener(actionListener);

    
        navPanel.add(homeButton);
        navPanel.add(rankingButton);
    }

    public JPanel getNavigatorPanel() {
        return navPanel; 
    }
}
