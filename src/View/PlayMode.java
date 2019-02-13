package View;

import Controller.PlayListeners.PlayModeListener;
import Controller.MainFrameListeners.KeyListenerTest;
import Model.GizmoballModel;

import javax.swing.*;
import java.awt.*;

public class PlayMode extends JPanel {

    private JPanel controlPanel;
        JPanel outterPanel;
        JLabel label_title;

        JButton button_start;
        JButton button_stop;
        JButton button_tick;
        JButton button_restart;

    private JPanel statsPanel;
        JLabel label_stats;
        JScrollPane area_stats;

        private MainFrame mainFrame;

    public PlayMode(MainFrame mainFrame){
       // Color bg_color = new Color(241, 241, 255, 162);
        this.setBackground(new Color(0, 41, 57, 255));
        this.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(0,1,20,20));
        this.setFocusable(true);

        this.mainFrame = mainFrame;

        //KeyListenerTest kl = new KeyListenerTest("play", mainFrame);

        GizmoballModel model = mainFrame.getGameBoard().getGizModel();
        KeyListenerTest absorberListener = new KeyListenerTest(model.getAbsorber().getID(),
                model, 'f', model.getAbsorber());

        System.out.println("--------sssss");
        mainFrame.addKeyListener(absorberListener);
        mainFrame.requestFocus();

        System.out.println(mainFrame.getKeyListeners());


        //mainFrame.getFocusOwner().addKeyListener(kl);

        init();
    }

    private void init(){

        //--------------------

        Color panel_colour = new Color(0, 55, 77, 255);
        Utils utils = new Utils();
        //--------------------------------------------------------
        //                    CONTROL PANEL

        controlPanel = new JPanel();
        outterPanel = new JPanel(new BorderLayout());

        label_title = new JLabel("Controls");
        label_title = utils.editLabel(label_title,15,Color.WHITE);

        outterPanel.add(label_title,BorderLayout.PAGE_START);
        outterPanel.setBackground(panel_colour);

        controlPanel.setBackground(panel_colour);
        controlPanel.setLayout(new GridLayout(0,2));
        controlPanel.setPreferredSize(new Dimension(200,200));
        controlPanel.setMaximumSize(new Dimension(200,200));

        controlPanel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button_start  = new JButton("Play");
        button_stop = new JButton("Pause");
        button_restart = new JButton("Restart");
        button_tick = new JButton("Tick");

        button_start = utils.addImgToBtn("play.png",button_start, Color.GREEN);
        button_stop = utils.addImgToBtn("pause.png",button_stop,Color.RED);
        button_restart = utils.addImgToBtn("restart.png",button_restart,Color.CYAN);
        button_tick = utils.addImgToBtn("tick.png",button_tick,Color.ORANGE);

        controlPanel.add(button_start);
        controlPanel.add(button_stop);
        controlPanel.add(button_tick);
        controlPanel.add(button_restart);

        outterPanel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outterPanel.add(controlPanel,BorderLayout.CENTER);
        this.add(outterPanel);

        //--------------------------------------------------------
        //                 BUTTON LISTENER!
        //Need to pass in the view for mainframe!
        PlayModeListener startClickListener = new PlayModeListener(mainFrame, "start");
        PlayModeListener stopClickListener = new PlayModeListener(mainFrame, "stop");
        PlayModeListener tickClickListener = new PlayModeListener(mainFrame, "tick");

        button_start.addActionListener(startClickListener);
        button_stop.addActionListener(stopClickListener);
        button_tick.addActionListener(tickClickListener);

        button_start.setFocusable(false);
        button_stop.setFocusable(false);
        button_tick.setFocusable(false);

        //--------------------------------------------------------
        //                    STATS PANEL

        statsPanel = new JPanel();
        statsPanel.setBackground(panel_colour);
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setPreferredSize(new Dimension(200,200));
        statsPanel.setMaximumSize(new Dimension(200,200));

        JTextArea container = new JTextArea(
                "No triggers available");
        label_stats = new JLabel("Triggers");
        label_stats = utils.editLabel(label_stats,15,Color.WHITE);
        container.setLineWrap(true);
        area_stats = new JScrollPane(container);
        area_stats.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

        statsPanel.add(label_stats, BorderLayout.PAGE_START);
        statsPanel.add(area_stats, BorderLayout.CENTER);
        statsPanel.setAutoscrolls(true);
        statsPanel.setEnabled(false);

        statsPanel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(statsPanel);
    }
}
