package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Utils {

    protected JLabel editLabel(JLabel l, int size, Color c){
        l.setFont(new Font("Monaco", Font.PLAIN, size));
        l.setForeground(c);
        return l;
    }


    protected JButton addImgToBtn(String path,JButton button){
        JButton play = new JButton("Play");
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null);
        button.setContentAreaFilled( false );

        return button;
    }
}
