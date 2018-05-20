package client.model;

import java.time.LocalDateTime;

public class Item {

    private int gpioPin;
    private String deviceName;
    private String notes;
    private boolean state;
    private int roomId;
    private LocalDateTime updated_at;

    public Item(int gpioPin, String deviceName, String notes, boolean state, int roomId, LocalDateTime updated_at) {
        this.gpioPin = gpioPin;
        this.deviceName = deviceName;
        this.notes = notes;
        this.state = state;
        this.roomId = roomId;
        this.updated_at = updated_at;
    }

    public int getGpioPin() {
        return gpioPin;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isState() {
        return state;
    }
}
