package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIUtils {
    public static void setToImageJButton(JButton jButton, String btnHint, int btnWidth, int btnHeight, String iconPath) {
        jButton.setBounds(0, 0, btnWidth, btnHeight);
        jButton.setToolTipText(btnHint);
        jButton.setText(null);

        ImageIcon imageIcon = new ImageIcon(iconPath);
        Image image = imageIcon.getImage().getScaledInstance(btnWidth, btnHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        jButton.setIcon(imageIcon);
    }
}
