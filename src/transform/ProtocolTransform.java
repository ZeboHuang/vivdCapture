package transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProtocolTransform {
    public static final short NONE_FILTER = -1;

    private static final Map<Short, String> protocolOf;
    private static final Map<String, Short> protocolOfRe;

    static {
        protocolOf = new HashMap<>();

        protocolOf.put((short) 1, "ICMP");
        protocolOf.put((short) 2, "IGMP");
        protocolOf.put((short) 4, "IP");
        protocolOf.put((short) 6, "TCP");
        protocolOf.put((short) 17, "UDP");
        protocolOf.put((short) 45, "IDRP");
        protocolOf.put((short) 46, "RSVP");
        protocolOf.put((short) 47, "GRE");
        protocolOf.put((short) 54, "NHRP");
        protocolOf.put((short) 88, "IGRP");
        protocolOf.put((short) 89, "OSPF");

        protocolOfRe = new HashMap<>();
        protocolOfRe.put("ICMP", (short) 1);
        protocolOfRe.put("IGMP", (short) 2);
        protocolOfRe.put("IP", (short) 4);
        protocolOfRe.put("TCP", (short) 6);
        protocolOfRe.put("UDP", (short) 17);
        protocolOfRe.put("IDRP", (short) 45);
        protocolOfRe.put("RSVP", (short) 46);
        protocolOfRe.put("GRE", (short) 47);
        protocolOfRe.put("NHRP", (short) 54);
        protocolOfRe.put("IGRP", (short) 88);
        protocolOfRe.put("OSPF", (short) 89);
    }

    public static String transformToString(short protocol) {
        if (protocolOf.containsKey(protocol))
            return protocolOf.get(protocol);
        else return String.valueOf(protocol);
    }

    public static short transformToShort(String name) {
        if (protocolOfRe.containsKey(name))
            return protocolOfRe.get(name);
        else return NONE_FILTER;
    }

    public static String[] getStringKeys() {
        String[] keys = new String[protocolOfRe.keySet().size()];
        int i = 0;
        for (String key :
                protocolOfRe.keySet()) {
            keys[i++] = key;
        }
        return keys;
    }


}
