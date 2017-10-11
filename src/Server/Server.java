package Server;

import Conts.Constants;
import Interfaces.IEffectenBeurs;
import MockFiles.MockEffectenbeurs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
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
            registry = LocateRegistry.createRegistry(Constants.portNumber);

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
                registry.rebind(Constants.bindingName, effectenBeurs);
            }
            else
            {
                throw new RemoteException();
            }
        }
        catch (RemoteException ex)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not (re)bind " + Constants.bindingName);
        }
    }

    public static void main(String[] arg)
    {
        Server server = new Server();
    }
}
