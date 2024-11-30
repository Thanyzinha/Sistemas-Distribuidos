import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConverterInterface extends Remote {
    double[] converterParaPolar(double x, double y) throws RemoteException;
}
