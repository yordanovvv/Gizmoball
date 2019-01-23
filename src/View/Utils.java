package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Utils {

    protected JLabel editLabel(JLabel l, int size, Color c){
        l.setFont(new Font("Monaco", Font.PLAIN, size));
        l.setForeground(c);
        return l;
    }

    protected JButton addImgToBtn(String path,JButton button){
        //this.icon = ImageIO.read(this.getClass().getResourceAsStream("resources/" + imgName));
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath);
        System.out.println( filePath + "/res/" +  path);
        try {
            Image img = ImageIO.read(getClass().getResource(filePath + "/res/" +   path));
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
