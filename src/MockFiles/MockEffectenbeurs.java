package MockFiles;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Server.Server;

import static Conts.Constants.PROPERTY_NAME;

public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenBeurs
{

    private final Random r = new Random();

    private List<IFonds> koersen;
    private Timer timer;
    private Server server;

    public MockEffectenbeurs() throws RemoteException
    {
        this.koersen = new ArrayList<>();

        this.koersen.add(new MockFonds("Heineken"));
        this.koersen.add(new MockFonds("Fontys"));
        this.koersen.add(new MockFonds("Jupiler"));
        this.koersen.add(new MockFonds("EA Sports"));
        this.koersen.add(new MockFonds("Jetbrains"));
    }

    public MockEffectenbeurs(Server server) throws RemoteException
    {
        this.koersen = new ArrayList<>();

        this.koersen.add(new MockFonds("Heineken"));
        this.koersen.add(new MockFonds("Fontys"));
        this.koersen.add(new MockFonds("Jupiler"));
        this.koersen.add(new MockFonds("EA Sports"));
        this.koersen.add(new MockFonds("Jetbrains"));

        this.server = server;
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException
    {
        updateKoersen();
        return koersen;
    }

    public void makeTimer()
    {
        Logger.getAnonymousLogger().log(Level.INFO, "Created Time");

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    server.getRemotePublisherForDomain().inform(PROPERTY_NAME, getKoersen(), getKoersen());
                }
                catch (RemoteException e)
                {
                    Logger.getAnonymousLogger().log(Level.INFO, "Could not inform " + PROPERTY_NAME);
                }
            }
        },1000,1000);
    }

    private void updateKoersen()
    {
        for (IFonds fonds: koersen)
        {
            int koers = r.nextInt(90) + 10;
            ((MockFonds)fonds).setKoers(koers);
        }
    }
}
