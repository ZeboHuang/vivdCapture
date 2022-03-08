/*
 * Created by JFormDesigner on Sun Mar 06 16:00:17 CST 2022
 */

package ui;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import net.miginfocom.swing.MigLayout;
import common.NetworkCapture;
import transform.ProtocolTransform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;


/**
 *
 * 主体窗口；设置了两个pane,一个打开软件时，显示选择网卡以及开始
 *                      一个是开始抓包，分析的界面
 *
 *
 *
 * @author vivibobo
 */
public class VividCaptureFrame extends JFrame {


    private NetworkInterface mDevice;
    private NetworkCapture networkCapture;


    private DefaultTableModel tableModel;
    private Vector rowList;


    public VividCaptureFrame() {
        tableModel = new DefaultTableModel();
        rowList = new Vector();

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
        splitPane1 = new JSplitPane();

        //======== this ========
        Container contentPane = getContentPane();
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
            splitPane.setResizeWeight(1.0);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(table1);
            }
            splitPane.setTopComponent(scrollPane1);

            //======== splitPane1 ========
            {
                splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
            }
            splitPane.setBottomComponent(splitPane1);
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
    private JSplitPane splitPane1;
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
        rowList.add(columns);
        tableModel.setDataVector(rowList, columns);
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
            r.add(ProtocolTransform.transformToString(ipPacket.protocol));
            r.add("information");
            r.add(ipPacket.length);

            System.out.println("src_ip: " + ipPacket.src_ip);
            System.out.println("dst_ip: " + ipPacket.dst_ip);
            System.out.println("protocol: " + ipPacket.protocol);
            System.out.println("length: " + ipPacket.length);

            rowList.add(r);
            tableModel.addRow(r);
            table1.setModel(tableModel);
//            rows.add(r);
        }
    }

    private void test() {
        /**
         * just for test
         */

        NetworkInterface[] devices = getAllDevicesList();
        mDevice = devices[1];
        JpcapCaptor jpcapCaptor = null;
//        networkCapture = new NetworkCapture(mDevice);
        try {
            jpcapCaptor = JpcapCaptor.openDevice(mDevice, 1514, true, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * test end
         */

        int i = 0;
        while (i < 10) {
            Packet packet = jpcapCaptor.getPacket();
            if (packet instanceof IPPacket && ((IPPacket) packet).version == 4) {
                i++;
                IPPacket ip = (IPPacket) packet;//强转

                addPacket(ip);


                System.out.println("版本：IPv4" + ip.version);
                System.out.println("优先权：" + ip.priority);
                System.out.println("区分服务：最大的吞吐量： " + ip.t_flag);
                System.out.println("区分服务：最高的可靠性：" + ip.r_flag);
                System.out.println("长度：" + ip.length);
                System.out.println("标识：" + ip.ident);
                System.out.println("DF:Don't Fragment: " + ip.dont_frag);
                System.out.println("NF:Nore Fragment: " + ip.more_frag);
                System.out.println("片偏移：" + ip.offset);
                System.out.println("生存时间：" + ip.hop_limit);

                String protocol = "";
                switch (new Integer(ip.protocol)) {
                    case 1:
                        protocol = "ICMP";
                        break;
                    case 2:
                        protocol = "IGMP";
                        break;
                    case 6:
                        protocol = "TCP";
                        break;
                    case 8:
                        protocol = "EGP";
                        break;
                    case 9:
                        protocol = "IGP";
                        break;
                    case 17:
                        protocol = "UDP";
                        break;
                    case 41:
                        protocol = "IPv6";
                        break;
                    case 89:
                        protocol = "OSPF";
                        break;
                    default:
                        break;
                }
                System.out.println("协议：" + protocol);
                System.out.println("源IP " + ip.src_ip.getHostAddress());
                System.out.println("目的IP " + ip.dst_ip.getHostAddress());
                System.out.println("源主机名： " + ip.src_ip);
                System.out.println("目的主机名： " + ip.dst_ip);
                System.out.println("----------------------------------------------");
            }

            //表格响应 更新数据。

        }


//        for (int i = 0; i < 10; i++) {
//            if (networkCapture.getIpv4Packet() != null) {
//                addPacket(networkCapture.getIpv4Packet());
//            }
//        }
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
