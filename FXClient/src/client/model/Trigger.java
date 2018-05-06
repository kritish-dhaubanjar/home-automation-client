package client.model;

public class Trigger {

    private int _id;
    private String name;
    private String note;
    private boolean shouldBeState;
    private boolean triggerState;
    private int masterPin;
    private int slavePin;

    public Trigger(int _id, String name, String note, boolean shouldBeState, boolean triggerState, int masterPin, int slavePin) {
        this._id = _id;
        this.name = name;
        this.note = note;
        this.shouldBeState = shouldBeState;
        this.triggerState = triggerState;
        this.masterPin = masterPin;
        this.slavePin = slavePin;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isShouldBeState() {
        return shouldBeState;
    }

    public void setShouldBeState(boolean shouldBeState) {
        this.shouldBeState = shouldBeState;
    }

    public boolean isTriggerState() {
        return triggerState;
    }

    public void setTriggerState(boolean triggerState) {
        this.triggerState = triggerState;
    }

    public int getMasterPin() {
        return masterPin;
    }

    public void setMasterPin(int masterPin) {
        this.masterPin = masterPin;
    }

    public int getSlavePin() {
        return slavePin;
    }

    public void setSlavePin(int slavePin) {
        this.slavePin = slavePin;
    }
}
