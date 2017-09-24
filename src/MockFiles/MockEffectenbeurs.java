package MockFiles;

import Interfaces.IEffectenBeurs;
import Interfaces.IFonds;

import java.util.ArrayList;
import java.util.List;

public class MockEffectenbeurs implements IEffectenBeurs
{

    private List<IFonds> koersen;

    public MockEffectenbeurs()
    {
        this.koersen = new ArrayList<IFonds>();

        this.koersen.add(new HeinikenFonds());
    }

    @Override
    public List<IFonds> getKoersen()
    {
        return koersen;
    }
}
