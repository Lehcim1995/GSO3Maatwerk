package MockFiles;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
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

    @Override
    public List<IFonds> getKoersen() throws RemoteException
    {
        updateKoersen();
        return koersen;
    }

    private void MakeTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    server.getRemotePublisherForDomain().inform(PROPERTY_NAME, getKoersen(), null);
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
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
