package Server;

import Interfaces.IEffectenBeurs;
import MockFiles.MockEffectenbeurs;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Conts.Constants.BINDING_NAME;
import static Conts.Constants.PORT_NUMBER;
import static Conts.Constants.PROPERTY_NAME;

public class Server
{
    // References to registry and student administration
    private Registry registry = null;
    private IEffectenBeurs effectenBeurs = null;

    private IRemotePublisherForDomain remotePublisherForDomain;

    public Server()
    {
        try
        {
            effectenBeurs = new MockEffectenbeurs(this);
            ((MockEffectenbeurs)effectenBeurs).makeTimer();
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Remote exception on MockEffectenbeurs");
        }

        try
        {
            remotePublisherForDomain = new RemotePublisher();
            remotePublisherForDomain.registerProperty(PROPERTY_NAME);
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Remote exception on remotePublisherForDomain");
        }

        try
        {
            registry = LocateRegistry.createRegistry(PORT_NUMBER);

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
                registry.rebind(BINDING_NAME, remotePublisherForDomain);
            }
            else
            {
                throw new RemoteException();
            }
        }
        catch (RemoteException ex)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not (re)bind " + BINDING_NAME);
        }
    }

    public static void main(String[] arg)
    {
        Server server = new Server();


    }

    public IRemotePublisherForDomain getRemotePublisherForDomain()
    {
        return remotePublisherForDomain;
    }
}
