package common;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import ui.CapturePanel;

import java.io.IOException;


public class NetworkCapture {

    private JpcapCaptor jpcapCaptor;
    private NetworkInterface device;
    private int snaplen = 1514;
    private boolean promiscCheck = true;
    private int to_ms = 50;

    private CapturePanel capturePanel;

    private Thread gettingPacketThread;
    private boolean isStop;

    public NetworkCapture(NetworkInterface device, CapturePanel capturePanel) {
        this.device = device;
        this.capturePanel = capturePanel;
        try {
            jpcapCaptor = JpcapCaptor.openDevice(device, snaplen, false, to_ms);
            if(capturePanel.getFilterText()!=null){
                jpcapCaptor.setFilter(capturePanel.getFilterText(), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private PacketReceiver packetReceiver = new PacketReceiver() {
        @Override
        public void receivePacket(Packet packet) {
            //
            capturePanel.addPacket(packet);
        }
    };

    public void startGettingPackets() {
        if (gettingPacketThread != null) {
            return;
        }
        isStop = false;
        gettingPacketThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    jpcapCaptor.processPacket(10000, packetReceiver);
//                    capturePanel.addPacket(jpcapCaptor.getPacket());
                }
            }
        });
        gettingPacketThread.start();
    }

    public void stopGettingPackets() {
        isStop = true;
        gettingPacketThread = null;
    }

}
