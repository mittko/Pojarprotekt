package admin.artikul;

public class ArtikulTable extends AvailableArtikulsTable{

    public ArtikulTable(){}
    @Override
    public String getTableName() {
        return AVAILABLE_ARTIKULS;
    }

    @Override
    public boolean isGrey() {
        return false;
    }
}
