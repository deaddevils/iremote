package com.iremote.common.jms.vo;

import java.util.Date;

public class RemoteNetworkChangedEvent extends RemoteEvent {
    private int network;
    private int networkIntensity;

    public RemoteNetworkChangedEvent() {
        super();
    }

    public RemoteNetworkChangedEvent(String deviceid, int network, int networkIntensity) {
        super.setDeviceid(deviceid);
        super.setEventtime(new Date());
        this.network = network;
        this.networkIntensity = networkIntensity;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    public int getNetworkIntensity() {
        return networkIntensity;
    }

    public void setNetworkIntensity(int networkIntensity) {
        this.networkIntensity = networkIntensity;
    }
}
