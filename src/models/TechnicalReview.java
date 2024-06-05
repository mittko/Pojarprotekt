package models;

public class TechnicalReview {
    String client;
    String type;
    String wheight;
    String T_O;
    String P;
    String HI;
    String number;
    String additional_data;


    @Override
    public String toString() {
        return "TechnicalReview{" +
                "client='" + client + '\'' +
                ", type='" + type + '\'' +
                ", wheight='" + wheight + '\'' +
                ", T_O='" + T_O + '\'' +
                ", P='" + P + '\'' +
                ", HI='" + HI + '\'' +
                ", number='" + number + '\'' +
                ", additional_data='" + additional_data + '\'' +
                '}';
    }
}
