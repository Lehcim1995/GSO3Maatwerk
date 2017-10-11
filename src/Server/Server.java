package Server;

import Interfaces.IEffectenBeurs;
import MockFiles.MockEffectenbeurs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{

    private static final int portNumber = 1099;

    // Set binding name for student administration
    private static final String bindingName = "StudentAdmin";

    // References to registry and student administration
    private Registry registry = null;
    private IEffectenBeurs effectenBeurs = null;

    public Server()
    {
        try
        {
            effectenBeurs = new MockEffectenbeurs();
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Remote exection on effectenbeurs");
        }

        try
        {
            registry = LocateRegistry.createRegistry(portNumber);

        }
        catch (RemoteException ex)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not Create registry");
            registry = null;
        }

        // Bind student administration using registry
        try
        {
            if (registry != null)
            {
                registry.rebind(bindingName, effectenBeurs);
            }
            else
            {
                throw new RemoteException();
            }
        }
        catch (RemoteException ex)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not (re)bind " + bindingName);
        }
    }

    public static void main(String[] arg)
    {
        Server server = new Server();
    }
}
