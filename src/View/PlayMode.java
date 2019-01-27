package View;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PlayMode extends JPanel implements Observer {

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

    public PlayMode(){
       // Color bg_color = new Color(241, 241, 255, 162);
        this.setBackground(new Color(0, 41, 57, 255));
        this.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(0,1,20,20));

        init();
    }

    private void init(){

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
        //                    STATS PANEL

        statsPanel = new JPanel();
        statsPanel.setBackground(panel_colour);
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setPreferredSize(new Dimension(200,200));
        statsPanel.setMaximumSize(new Dimension(200,200));

        JTextArea container = new JTextArea(
                "Fergalicious definition make them boys go loco\n" +
                        "They want my treasure, so they get their pleasures from my photo\n" +
                        "You could see me, you can't squeeze me\n" +
                        "I ain't easy, I ain't sleazy\n" +
                        "I got reasons why I tease 'em\n" +
                        "Boys just come and go like seasons\n" +
                        "Fergalicious (so delicious)\n" +
                        "But I ain't promiscuous\n" +
                        "And if you was suspicious\n" +
                        "All that shit is fictitious\n" +
                        "I blow kisses (mwah)\n" +
                        "That puts them boys on rock, rock\n" +
                        "And they be lining down the block just to watch what I got (four, tres, two, uno)");
        label_stats = new JLabel("Stats");
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

    @Override
    public void update(Observable o, Object arg) {

    }
}
