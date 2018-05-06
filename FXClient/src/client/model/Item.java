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

    public void setGpioPin(int gpioPin) {
        this.gpioPin = gpioPin;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
