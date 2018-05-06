import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("raspberrypi.local", 1099);
        IPiServer piServer = (IPiServer) registry.lookup("PISERVER");
        
        boolean bool = true;
        
        
        
        piServer.setState(2, true);
        piServer.setState(0, true);
        piServer.setState(1, true);
        
        //piServer.setState(0, false);
        
        //piServer.setState(1, false);
        
        
        
        
        /*for(int i=0; i<10; i++){
        	piServer.setState(0, bool);
        	piServer.setState(1, !bool);
        	bool = !bool;
        	try{
        		Thread.sleep(5000);
        	}catch(InterruptedException e){
        		//
        	}
        }*/
        
        /*piServer.setState(0, true);
        piServer.setState(1, true);
        piServer.setState(2, true);
        /*
        /*piServer.createTrigger("Trigger", "Trigger 001", 0,
                               1, false, false);
       piServer.createTrigger("Trigger", "Trigger 002", 1,
                               2, false, false);*/
        //piServer.setState(1, false);
        
    }
}
