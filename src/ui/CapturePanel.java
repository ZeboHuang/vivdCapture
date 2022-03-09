/*
 * Created by JFormDesigner on Tue Mar 08 21:02:10 CST 2022
 */

package ui;

import common.NetworkCapture;
import jpcap.NetworkInterface;
import jpcap.packet.*;
import net.miginfocom.swing.MigLayout;
import transform.ProtocolTransform;
import utils.GUIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * @author unknown
 */
public class CapturePanel extends JPanel {

    private NetworkInterface mDevice;

    private DefaultTableModel tableModel;
    private int packetCount = 0;
    private long startTime;

    private String filterText;

    private NetworkCapture capture;

    //监听相关
    private int clickRow;

    public CapturePanel(NetworkInterface mDevice, String mfilterText) {
        this.mDevice = mDevice;
        this.filterText = mfilterText;
        startTime = System.currentTimeMillis();
        initComponents();
        initMComponents();
    }

    private void startBtnMouseClicked(MouseEvent e) {
        // TODO add your code here

        System.out.println("startBtn: " + filterText);
        capture = new NetworkCapture(mDevice, this, filterText);
        capture.startGettingPackets();

        startBtn.setEnabled(false);
    }

    private void stopBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        capture.stopGettingPackets();
    }

    private void clearBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        clearPackets();
    }

    private void packetTableMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (e.getClickCount() == 2) {
            clickRow = packetTable.getSelectedRow();
            System.out.println("clickRow");
            updateJLabel();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        toolBar2 = new JToolBar();
        startBtn = new JButton();
        stopBtn = new JButton();
        clearBtn = new JButton();
        splitPane1 = new JSplitPane();
        scrollPane1 = new JScrollPane();
        packetTable = new JTable();
        detailLabel = new JLabel();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
                javax.swing.border.EmptyBorder(0, 0, 0, 0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax
                .swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java
                .awt.Font("Dia\u006cog", java.awt.Font.BOLD, 12), java.awt
                .Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.
                PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("\u0062ord\u0065r".
                        equals(e.getPropertyName())) throw new RuntimeException();
            }
        });
        setLayout(new MigLayout(
                "insets 0,hidemode 3,gap 0 0",
                // columns
                "[grow,left]",
                // rows
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[]"));

        //======== toolBar2 ========
        {

            //---- startBtn ----
            startBtn.setText("text");
            startBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    startBtnMouseClicked(e);
                }
            });
            toolBar2.add(startBtn);

            //---- stopBtn ----
            stopBtn.setText("text");
            stopBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stopBtnMouseClicked(e);
                }
            });
            toolBar2.add(stopBtn);

            //---- clearBtn ----
            clearBtn.setText("text");
            clearBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clearBtnMouseClicked(e);
                }
            });
            toolBar2.add(clearBtn);
        }
        add(toolBar2, "cell 0 0");

        //======== splitPane1 ========
        {
            splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane1.setDividerLocation(200);

            //======== scrollPane1 ========
            {

                //---- packetTable ----
                packetTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        packetTableMouseClicked(e);
                    }
                });
                scrollPane1.setViewportView(packetTable);
            }
            splitPane1.setTopComponent(scrollPane1);
            splitPane1.setBottomComponent(detailLabel);
        }
        add(splitPane1, "cell 0 3,aligny top,grow 100 0");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JToolBar toolBar2;
    private JButton startBtn;
    private JButton stopBtn;
    private JButton clearBtn;
    private JSplitPane splitPane1;
    private JScrollPane scrollPane1;
    private JTable packetTable;
    private JLabel detailLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    private void clearPackets() {
        packetCount = 0;
        tableModel.setRowCount(0);
        startTime = System.currentTimeMillis();
    }


    public void addPacket(Packet packet) {
        long time = System.currentTimeMillis() - startTime;
        Vector row = new Vector();


        if (packet instanceof IPPacket) {
            row.add(packetCount);
            row.add(String.format("%.3f s", (float) time / 1000));
            IPPacket mPacket = (IPPacket) packet;
            row.add(mPacket.src_ip);
            row.add(mPacket.dst_ip);
            row.add(ProtocolTransform.transformToString(mPacket.protocol)); //协议
            row.add(mPacket.caplen);

//
            row.add(mPacket.version);       //版本 7
            row.add(mPacket.header.length); //首部长度 8
            row.add(mPacket.data.length);   //数据长度
            row.add(mPacket.ident);         //标识 9
            //标志 10
            row.add((mPacket.more_frag == true ? 4 : 0) + (mPacket.dont_frag == true ? 1 : 0));                       //标志 10     //1 4 5 取值
            row.add(mPacket.offset);        //片偏移 11


        } else if (packet instanceof TCPPacket) {
            row.add(packetCount);
            row.add(String.format("%.6f", (float) time / 1000000));
            TCPPacket mPacket = (TCPPacket) packet;
            row.add(mPacket.src_ip);
            row.add(mPacket.dst_ip);
            row.add(ProtocolTransform.transformToString(mPacket.protocol));
            row.add(mPacket.length);
            row.add(mPacket.version);       //版本 7
            row.add(mPacket.header.length); //首部长度 8
            row.add(mPacket.data.length);   //数据长度
            row.add(mPacket.ident);         //标识 9
            //标志 10
            row.add((mPacket.more_frag == true ? 4 : 0) + (mPacket.dont_frag == true ? 1 : 0));                       //标志 10     //1 4 5 取值
            row.add(mPacket.offset);        //片偏移 11
        } else if (packet instanceof UDPPacket) {
            row.add(packetCount);
            row.add(String.format("%.6f", (float) time / 1000000));
            UDPPacket mPacket = (UDPPacket) packet;
            row.add(mPacket.src_ip);
            row.add(mPacket.dst_ip);
            row.add(ProtocolTransform.transformToString(mPacket.protocol));
            row.add(mPacket.length);

            row.add(mPacket.version);       //版本 7
            row.add(mPacket.header.length); //首部长度 8
            row.add(mPacket.data.length);   //数据长度
            row.add(mPacket.ident);         //标识 9
            //标志 10
            row.add((mPacket.more_frag == true ? 4 : 0) + (mPacket.dont_frag == true ? 1 : 0));                       //标志 10     //1 4 5 取值
            row.add(mPacket.offset);        //片偏移 11
        } else if (packet instanceof ICMPPacket) {
            row.add(packetCount);           //序号 0
            row.add(String.format("%.6f", (float) time / 1000000)); //到达时间 1
            ICMPPacket mPacket = (ICMPPacket) packet;
            row.add(mPacket.src_ip);    //源地址 2
            row.add(mPacket.dst_ip);    //目的地址 3
            row.add(ProtocolTransform.transformToString(mPacket.protocol)); //协议 4
            row.add(mPacket.caplen);        //总长度 6

            row.add(mPacket.version);       //版本 7
            row.add(mPacket.header.length); //首部长度 8
            row.add(mPacket.data.length);   //数据长度
            row.add(mPacket.ident);         //标识 9
            //标志 10
            row.add((mPacket.more_frag == true ? 4 : 0) + (mPacket.dont_frag == true ? 1 : 0));                       //标志 10     //1 4 5 取值
            row.add(mPacket.offset);        //片偏移 11
        }

        if (row.size() > 0) {
            row.add(getBytes(packet.header));
            row.add(getBytes(packet.data));

            tableModel.addRow(row);
            packetTable.setModel(tableModel);
            packetCount++;
            System.out.println("add success.");
            System.out.println(packet.header.length);
        }
    }

    private String getBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
        }
        return sb.toString();
    }

    private void initMComponents() {
        //initdata

        //initbtns
        GUIUtils.setToImageJButton(startBtn, "开始", 20, 20, "src/res/icons/play.png");
        GUIUtils.setToImageJButton(stopBtn, "停止", 20, 20, "src/res/icons/stop.png");
        GUIUtils.setToImageJButton(clearBtn, "清除", 20, 20, "src/res/icons/close.png");

        //inittable
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;   //不可编辑
            }
        };
        Vector columns = new Vector();
        columns.add("序号");
        columns.add("时间");
        columns.add("源地址");
        columns.add("目的地址");
        columns.add("协议");
        columns.add("长度");

        //hide
        columns.add("版本");  //6
        columns.add("首部长度");
        columns.add("数据长度");
        columns.add("标识");
        columns.add("标志");
        columns.add("片偏移");
        columns.add("首部");//12
        columns.add("数据");//13


        Vector rows = new Vector();
        tableModel.setDataVector(rows, columns);
        packetTable.setModel(tableModel);

        packetTable.getColumnModel().getColumn(12).setMaxWidth(0);
        packetTable.getColumnModel().getColumn(13).setMaxWidth(0);

        //jtree init
        //when table row click jtree update

    }

    private void updateJLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("版本: " + packetTable.getValueAt(clickRow, 6) + "<br/>");
        sb.append("首部长度: " + packetTable.getValueAt(clickRow, 7) + "<br/>");
        sb.append("总长度: " + packetTable.getValueAt(clickRow, 5) + "<br/>");
        sb.append("标识: " + packetTable.getValueAt(clickRow, 9) + "<br/>");
        sb.append("标志: " + packetTable.getValueAt(clickRow, 10) + "<br/>");
        if (packetTable.getValueAt(clickRow, 10).toString().equals("0")) {
            sb.append("是否有分片: 没有分片<br/>");
        } else {
            sb.append("是否有分片: 有分片<br/>");
        }
        sb.append("片偏移: " + packetTable.getValueAt(clickRow, 11) + "<br/>");
        sb.append("协议: " + packetTable.getValueAt(clickRow, 4) + "<br/>");
        sb.append("源地址: " + packetTable.getValueAt(clickRow, 2) + "<br/>");
        sb.append("目的地址: " + packetTable.getValueAt(clickRow, 3) + "<br/>");
        sb.append("首部数据: " + packetTable.getValueAt(clickRow, 12) + "<br/>");
        sb.append("数据部分: " + packetTable.getValueAt(clickRow, 13) + "<br/>");
        sb.append("</body></html>");

        detailLabel.setText(sb.toString());
    }

    //用来标志
//    private static boolean[] hflags = new boolean[64];
//
//    static {
//        hflags[4] = true;
//
//    }

    //无力分析。。。。。
//    private Vector analyzeHeader(byte[] header) {
//        //0-3 version
//        Vector vector = new Vector();
//    }

}