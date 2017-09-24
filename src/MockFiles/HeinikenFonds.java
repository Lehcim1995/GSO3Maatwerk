package MockFiles;

import Interfaces.IFonds;

import java.util.Random;

public class HeinikenFonds implements IFonds
{
    private final Random r = new Random();

    private double koers;

    public HeinikenFonds()
    {
        koers = 50;
    }

    @Override
    public String getNaam()
    {
        return "Heiniken";
    }

    @Override
    public double getKoers()
    {
        koers += r.nextDouble() - 0.5d;

        return Math.round(koers * 100d) / 100d;
    }
}
