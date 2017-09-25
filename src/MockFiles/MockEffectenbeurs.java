package MockFiles;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenBeurs
{

    private final Random r = new Random();

    private List<IFonds> koersen;

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

    private void updateKoersen()
    {
        for (IFonds fonds: koersen)
        {
            int koers = r.nextInt(90) + 10;
            ((MockFonds)fonds).setKoers(koers);
        }
    }

}
