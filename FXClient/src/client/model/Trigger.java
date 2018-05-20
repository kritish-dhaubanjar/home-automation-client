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

    public String getName() {
        return name;
    }

    public boolean isShouldBeState() {
        return shouldBeState;
    }

    public boolean isTriggerState() {
        return triggerState;
    }

    public int getMasterPin() {
        return masterPin;
    }

    public int getSlavePin() {
        return slavePin;
    }
}
