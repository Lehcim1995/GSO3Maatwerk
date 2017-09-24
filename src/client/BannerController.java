package client;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;
import MockFiles.MockEffectenbeurs;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BannerController {

    private AEXBanner banner;
    private IEffectenBeurs effectenbeurs;
    private Timer pollingTimer;

    private final Random r = new Random();

    public BannerController(final AEXBanner banner) {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();

        pollingTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                banner.setKoersen(fromKoersToString(effectenbeurs.getKoersen()));
            }
        }, 1000, 2000);
    }

    private String fromKoersToString(List<IFonds> fonds)
    {
        StringBuilder print = new StringBuilder();

        for (IFonds f: fonds)
        {
            print.append(f.getNaam()).append(" ").append(f.getKoers()).append(" | ");
        }

        return print.toString();
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }
}
