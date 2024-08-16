package invoice.sklad;

import models.ArtikulModel;

import java.util.ArrayList;

public interface ILoadArtikuls {

    boolean isGrey();
    void loadArtikuls(ArrayList<ArtikulModel> artikuls);
}
