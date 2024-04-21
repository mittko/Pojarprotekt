package admin.àrtikul;

import utils.MainPanel;

import java.util.ArrayList;

public interface IEditArtikuls {
    void changeArtikulPrice(String artikul, String biggestPriceValue,String percentProfit,String kontragent,String invoice);

    void changeArtikulQuantity(String artikul, String newQuantity,String kontragent,String invoice);

    void deleteArtikul(String artikul,String kontragent, String invoice);

    void addArtikul(String client, String artikul, String sklad,String med, String deliveryValue, String price, String invoice, String date,String operator,String percentProfit,String barcode);

    void renameArtikul(String oldArtikulName,String newArtikulName);
}
