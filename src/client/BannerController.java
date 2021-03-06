package client;

import Conts.Constants;
import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Conts.Constants.BINDING_NAME;
import static Conts.Constants.LOCAL_HOST;
import static Conts.Constants.PROPERTY_NAME;

public class BannerController extends UnicastRemoteObject implements IRemotePropertyListener
{

    private final Random r = new Random();
    private AEXBanner banner;
    private IEffectenBeurs effectenBeurs;
    private Timer pollingTimer;

    // References to registry and student administration
    private Registry registry = null;
    private IRemotePublisherForListener remotePropertyListener;

    public BannerController(final AEXBanner banner) throws RemoteException
    {
        this.banner = banner;
        String ipAddress = LOCAL_HOST;
        int portNumber = 1099;

        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not find registry on ip" + ipAddress + " and port " + portNumber);
            registry = null;
        }

        try
        {
            if (registry != null)
            {
                this.remotePropertyListener = (IRemotePublisherForListener) registry.lookup(BINDING_NAME);
            }
            else
            {
                throw new RemoteException();
            }
        }
        catch (RemoteException | NotBoundException e)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not find " + BINDING_NAME);
        }

        try
        {
            remotePropertyListener.subscribeRemoteListener(this, PROPERTY_NAME);
        }
        catch (RemoteException e)
        {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not subscribe to remote listener " + PROPERTY_NAME);
        }
    }

    private String fromKoersToString(List<IFonds> fonds)
    {
        StringBuilder print = new StringBuilder();
        // TODO Build something to prevent the jumping of charaters

        for (IFonds f : fonds)
        {
            print.append(f.getNaam()).append(" ").append(f.getKoers()).append(" | ");
        }

        return print.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException
    {
        banner.setKoersen(fromKoersToString((List<IFonds>) propertyChangeEvent.getNewValue()));
    }
}
