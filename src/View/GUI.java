package View;

import javax.swing.*;
import java.awt.*;


public class GUI {
    private JFrame frame;

    public GUI() {
        frame = new JFrame("Test");
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        leftPanel.setPreferredSize(new Dimension(300, 300));
        leftPanel.setMaximumSize(new Dimension(300, 300));
        leftPanel.setMinimumSize(new Dimension(300, 300));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JButton switchButton = new JButton("Switch mode");
        JLabel addGizmoLabel = new JLabel("Add Gizmo");

        JPanel gizmosPanel = new JPanel();
        //gizmosPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gizmosPanel.setPreferredSize(new Dimension(300, 150));
        gizmosPanel.setMaximumSize(new Dimension(300, 150));
        gizmosPanel.setMinimumSize(new Dimension(300, 150));
        gizmosPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gizmosPanel.setLayout(new FlowLayout());
        JButton triangleButton = new JButton("Triangle");
        JButton circleButton = new JButton("Circle");
        JButton squareButton = new JButton("Square");
        JButton absorberButton = new JButton("Absorber");
        JButton ballButton = new JButton("Ball");
        JButton lFlipperButton = new JButton("Left Flipper");
        JButton rFlipperButton = new JButton("Right Flipper");
        gizmosPanel.add(addGizmoLabel);
        gizmosPanel.add(triangleButton);
        gizmosPanel.add(circleButton);
        gizmosPanel.add(squareButton);
        gizmosPanel.add(absorberButton);
        gizmosPanel.add(ballButton);
        gizmosPanel.add(lFlipperButton);
        gizmosPanel.add(rFlipperButton);


        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setPreferredSize(new Dimension(300, 150));
        propertiesPanel.setMaximumSize(new Dimension(300, 150));
        propertiesPanel.setMinimumSize(new Dimension(300, 150));
        propertiesPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        propertiesPanel.setLayout(new FlowLayout());
        JButton rotateButton = new JButton("Rotate");
        JButton connectButton = new JButton("Connect");
        JButton disconnectButton = new JButton("Disconnect");
        JButton deleteButton = new JButton("Delete");
        JButton showConnButton = new JButton("Show Connections");
        JButton clearButton = new JButton("Clear");
        propertiesPanel.add(rotateButton);
        propertiesPanel.add(connectButton);
        propertiesPanel.add(disconnectButton);
        propertiesPanel.add(deleteButton);
        propertiesPanel.add(showConnButton);
        propertiesPanel.add(clearButton);

        leftPanel.add(switchButton);
        leftPanel.add(gizmosPanel);
        leftPanel.add(propertiesPanel);

        pane.add(leftPanel);


        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setPreferredSize(new Dimension(500, 500));
        gridPanel.setMaximumSize(new Dimension(400, 400));
        gridPanel.setMinimumSize(new Dimension(400, 400));
        pane.add(gridPanel);


        frame.revalidate();
    }
}
