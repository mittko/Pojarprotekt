package invoice.Sklad;

import java.util.ArrayList;

public interface ILoadArtikuls {

    public ArrayList<Object[]> getArtikuls();
    void loadArtikuls(ArrayList<Object[]> artikuls);
}
