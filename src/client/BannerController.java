package client;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;
import MockFiles.MockEffectenbeurs;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BannerController
{

    private final Random r = new Random();
    private AEXBanner banner;
    private IEffectenBeurs effectenBeurs;
    private Timer pollingTimer;

    public BannerController(final AEXBanner banner)
    {
        this.banner = banner;
        this.effectenBeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();

        pollingTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                banner.setKoersen(fromKoersToString(effectenBeurs.getKoersen()));
            }
        }, 1000, 2000);
    }

    private String fromKoersToString(List<IFonds> fonds)
    {
        StringBuilder print = new StringBuilder();

        for (IFonds f : fonds)
        {
            print.append(f.getNaam()).append(" ").append(f.getKoers()).append(" | ");
        }

        return print.toString();
    }

    // Stop banner controller
    public void stop()
    {
        pollingTimer.cancel();
        // Stop simulation timer of effectenBeurs
        // TODO
    }
}
