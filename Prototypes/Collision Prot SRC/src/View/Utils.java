package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {

    protected JLabel editLabel(JLabel l, int size, Color c) {
        l.setFont(new Font("Monaco", Font.PLAIN, size));
        l.setForeground(c);
        return l;
    }

    protected JButton addImgToBtn(String path, JButton button, Color subColour) {
        String filePath = new File("").getAbsolutePath();
        button.setFont(new Font("Monaco", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        try {
            Image img = TransparentIcon(path,subColour);;
            img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBackground(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);

        return button;
    }

    protected JTextField styleTextField(JTextField f, int size) {
        f.setFont(new Font("Monaco", Font.PLAIN, size));
        f.setEditable(false);
        return f;
    }

    private BufferedImage TransparentIcon(String path, Color subColour) throws IOException {
            String filePath = new File("").getAbsolutePath();
            BufferedImage icon = ImageIO.read(new File(filePath + "/res/" + path));
            BufferedImage new_icon = new BufferedImage(icon.getWidth(),icon.getHeight(),BufferedImage.TYPE_INT_ARGB);

            Color transparent = new Color(255, 255, 255,0);

            for (int x_coord = 0; x_coord < icon.getHeight(); x_coord++) {
                for (int y_coord = 0; y_coord < icon.getWidth(); y_coord++) {
                    Color currentColor = new Color(icon.getRGB(x_coord,y_coord));
                    if(currentColor.equals(Color.white)){
                        new_icon.setRGB(x_coord,y_coord,transparent.getRGB());
                    }else if(currentColor.equals(Color.black)){
                        new_icon.setRGB(x_coord,y_coord,subColour.getRGB());
                    }
                }
            }
            return new_icon;
    }
}
