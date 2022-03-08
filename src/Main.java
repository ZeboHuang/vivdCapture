import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import common.NetworkCapture;

public class Main {


    public static void main(String[] args) {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (NetworkInterface ne :
                devices) {
            System.out.println(ne.name);
        }

    }


}
