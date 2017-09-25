package MockFiles;

import Interfaces.IFonds;

public class MockFonds implements IFonds
{
    private String naam;
    private double koers;

    public MockFonds()
    {
        koers = 50;
    }

    public MockFonds(String naam)
    {
        this();
        this.naam = naam;
    }

    public MockFonds(String naam, double koers)
    {
        this.naam = naam;
        this.koers = koers;
    }

    @Override
    public String getNaam()
    {
        return naam;
    }

    @Override
    public double getKoers()
    {
        return koers;
    }

    public void setKoers(double koers)
    {
        this.koers = koers;
    }
}
