import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class RMIServer extends UnicastRemoteObject implements ConverterInterface {
    public RMIServer() throws RemoteException {}

    public double[] converterParaPolar(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return new double[]{r, theta};
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("Converter", new RMIServer());
            System.out.println("Servidor RMI pronto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
