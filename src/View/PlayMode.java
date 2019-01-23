package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PlayMode extends JPanel implements Observer {

    private JPanel controlPanel;
        JButton button_start;
        JButton button_stop;
        JButton button_tick;
        JButton button_restart;


    private JPanel statsPanel;
        JLabel label_stat;


    public PlayMode(){
        GridLayout layout_playMode = new GridLayout(1,1,10,10);

        Color bg_color = new Color(22, 21, 60, 171);

        this.setBackground(bg_color);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(layout_playMode);


        init();
    }

    private void init(){

        controlPanel = new JPanel();
        //GridLayout layout_controlPanel = new GridLayout(4,1,10,10);
        //controlPanel.setLayout(layout_controlPanel);

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        button_start  = new JButton("Start");
        button_stop = new JButton("Stop");
        button_restart = new JButton("Restart");
        button_tick = new JButton("Tick");

        controlPanel.add(button_start);
        controlPanel.add(button_stop);
        controlPanel.add(button_tick);
        controlPanel.add(button_restart);

        this.add(controlPanel);

        statsPanel = new JPanel();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
