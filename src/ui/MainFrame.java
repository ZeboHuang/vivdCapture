/*
 * Created by JFormDesigner on Mon Mar 07 22:19:31 CST 2022
 */

package ui;

import java.awt.event.*;
import javax.swing.event.*;

import jpcap.NetworkInterface;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;
import static java.lang.System.setOut;

/**
 * @author unknown
 */
public class MainFrame extends JFrame implements OnMClickListener {

    private JPanel welcomePanel;
    private JFrame mFrame;
    private OnMClickListener mClickListener;
    private CapturePanel capturePanel;

    public MainFrame() {
        mFrame = this;
        mClickListener = this;
        initComponents();
        initFrame();
        initMenu();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        exit = new JMenuItem();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u83dc\u5355");

                //---- exit ----
                exit.setText("\u9000\u51fa");
                menu1.add(exit);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem exit;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    private void initFrame() {
        this.setTitle("网络数据包的抓取与分析程序");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomePanel = new WelcomePanel(this);
        this.add(welcomePanel);

        this.setVisible(true);
    }

    private void initMenu() {
//        start.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (capturePanel != null) {
//                    System.out.println("go to welcome");
//                    capturePanel.setVisible(false);
//                    mFrame.remove(capturePanel);
//                    mFrame.add(welcomePanel);
//                }
//            }
//        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mFrame.dispose();
                exit(0);
            }
        });
    }


    public static void main(String[] args) {
        new MainFrame();
    }

    @Override
    public void onMClick(NetworkInterface device, String filterStr) {
        //点击后切换界面
        if (device != null) {
            this.remove(welcomePanel);
            System.out.println("welcomePanel had removed, " + filterStr);
            capturePanel = new CapturePanel(device, filterStr);
            this.add(capturePanel);
        } else {
            System.out.println("请选择网卡");
        }
    }
}
