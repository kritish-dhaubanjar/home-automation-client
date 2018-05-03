import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("raspberrypi.local", 1099);
        IPiServer piServer = (IPiServer) registry.lookup("PISERVER");
        
        boolean bool = true;
        
        for(int i=0; i<10; i++){
        	piServer.setState(0, bool);
        	piServer.setState(1, !bool);
        	bool = !bool;
        	try{
        		Thread.sleep(5000);
        	}catch(InterruptedException e){
        		//
        	}
        }
        
        
    }
}