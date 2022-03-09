/*
 * Created by JFormDesigner on Mon Mar 07 20:20:40 CST 2022
 */

package ui;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author unknown
 */
public class WelcomePanel extends JPanel {
    private static final String label2text = "<html><body>欢迎使用vividCapture网络抓包工具<br/>"
            + "请在下方选择网卡后，点击开始!"
            + " </body></html>";

    private NetworkInterface selectedDevice;
    private OnMClickListener mClickListener;

    private NetworkInterface[] devices;
    private String[] deviceNames;

    private String filterText;


    public WelcomePanel(OnMClickListener mClickListener) {
        this.mClickListener = mClickListener;
        initComponents();

        initDevices();
    }

    private void initDevices() {
        devices = JpcapCaptor.getDeviceList();
        deviceNames = new String[devices.length];
        for (int i = 0; i < devices.length; i++) {
            deviceNames[i] = devices[i].datalink_name + " " + devices[i].description + " " + devices[i].datalink_description;
        }
        deviceList.setListData(deviceNames);
    }

    private void startBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        System.out.println("request to remove welcomePanel");
        if (textField1.getText() == null) {
            filterText = null;
        } else {
            filterText = textField1.getText();
        }
        System.out.println("welcome filter: " + filterText);
        mClickListener.onMClick(selectedDevice, filterText);
    }

    private void deviceListValueChanged(ListSelectionEvent e) {
        // TODO add your code here
        System.out.println("device selected.  index: " + e.getFirstIndex());
        selectedDevice = devices[e.getFirstIndex()];
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        scrollPane1 = new JScrollPane();
        deviceList = new JList();
        label1 = new JLabel();
        label2 = new JLabel();
        startBtn = new JButton();
        textField1 = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing
        . border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder
        . CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .
        awt . Font. BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) )
        ;  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } )
        ;

        //======== scrollPane1 ========
        {

            //---- deviceList ----
            deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            deviceList.addListSelectionListener(e -> deviceListValueChanged(e));
            scrollPane1.setViewportView(deviceList);
        }

        //---- label1 ----
        label1.setText("\u6b22\u8fce!!!");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 10f));
        label1.setHorizontalAlignment(SwingConstants.CENTER);

        //---- label2 ----
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 5f));

        //---- startBtn ----
        startBtn.setText("\u5f00\u59cb");
        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startBtnMouseClicked(e);
                startBtnMouseClicked(e);
            }
        });

        //---- label3 ----
        label3.setText("\u8bf7\u9009\u62e9\u7f51\u5361");

        //---- label4 ----
        label4.setText("\u8bf7\u8f93\u5165\u8fc7\u6ee4\u534f\u8bae\u6216ip");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label4)
                                .addComponent(startBtn, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                            .addGap(15, 15, 15)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3)
                        .addComponent(label4))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startBtn)))
                    .addGap(44, 44, 44))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JScrollPane scrollPane1;
    private JList deviceList;
    private JLabel label1;
    private JLabel label2;
    private JButton startBtn;
    private JTextField textField1;
    private JLabel label3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
