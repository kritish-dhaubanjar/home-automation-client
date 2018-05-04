import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("raspberrypi.local", 1099);
        IPiServer piServer = (IPiServer) registry.lookup("PISERVER");

        new Receiver().start();

//        piServer.createTrigger("Trigger", "Trigger 001", 0,1, false, false);
//        piServer.createItem(false, "GPIO 02", "Pin 13", 2,0);
//        piServer.createTrigger("Trigger", "Trigger 001", 1,2, false, false);
//
        piServer.setState(0, true);
//        piServer.setState(0, false);
//        piServer.setState(1, true);

    }
}