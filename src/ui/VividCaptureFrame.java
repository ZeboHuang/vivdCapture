/*
 * Created by JFormDesigner on Sun Mar 06 16:00:17 CST 2022
 */

package ui;

import javax.swing.*;
import javax.swing.table.*;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import net.miginfocom.swing.*;
import transform.NetworkCapture;

import java.util.Vector;


/**
 * @author unknown
 */
public class VividCaptureFrame extends JFrame {


    private NetworkInterface mDevice;
    private NetworkCapture networkCapture;

    private Vector rows;

    public VividCaptureFrame() {
        rows = new Vector();


        initComponents();
        initJtableColumn();


        test();
    }

    private void initComponents() {
        setSize(800, 600);
        setVisible(true);

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        topMenuBar = new JMenuBar();
        files = new JMenu();
        start = new JMenuItem();
        exit = new JMenuItem();
        splitPane = new JSplitPane();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //======== topMenuBar ========
        {

            //======== files ========
            {
                files.setText("\u6587\u4ef6");

                //---- start ----
                start.setText("\u5f00\u59cb");
                files.add(start);

                //---- exit ----
                exit.setText("\u9000\u51fa");
                files.add(exit);
            }
            topMenuBar.add(files);
        }
        setJMenuBar(topMenuBar);

        //======== splitPane ========
        {
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(table1);
            }
            splitPane.setTopComponent(scrollPane1);
        }
        contentPane.add(splitPane, "cell 0 0 6 4");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar topMenuBar;
    private JMenu files;
    private JMenuItem start;
    private JMenuItem exit;
    private JSplitPane splitPane;
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    private void initJtableColumn() {
        Vector columns = new Vector();
        columns.add("序号");
        columns.add("时间");
        columns.add("源地址");
        columns.add("目的地址");
        columns.add("协议");
        columns.add("信息");
        columns.add("长度");

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setDataVector(rows, columns);
        table1.setModel(tableModel);
    }

    private void addPacket(Packet packet) {
        if (packet instanceof IPPacket) {
            IPPacket ipPacket = (IPPacket) packet;

            Vector r = new Vector();
            r.add(1);           //序号
            r.add("0.0000");    //时间
            r.add(ipPacket.src_ip);
            r.add(ipPacket.dst_ip);
            r.add(ipPacket.protocol);
            r.add("information");
            r.add(ipPacket.length);

            rows.add(r);
        }
    }

    private void test() {
        networkCapture = new NetworkCapture();
        NetworkInterface[] devices = getAllDevicesList();
        networkCapture.setmDevice(devices[1]);


        for (int i = 0; i < 10; i++) {
            if (networkCapture.getIpv4Packet() != null) {
                addPacket(networkCapture.getIpv4Packet());
            }
        }
    }

    private NetworkInterface[] getAllDevicesList() {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (NetworkInterface device :
                devices) {
            System.out.println(device.name + " " + device.description);
        }

        return JpcapCaptor.getDeviceList();
    }

    public static void main(String[] args) {
        new VividCaptureFrame();

    }
}
