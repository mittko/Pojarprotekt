package models;

public class TechnicalReview {
    public String getClient() {
        return client;
    }

    public String getType() {
        return type;
    }

    public String getWheight() {
        return wheight;
    }

    public String getT_O() {
        return t_O;
    }

    public String getP() {
        return p;
    }

    public String getHI() {
        return hi;
    }

    public String getNumber() {
        return number;
    }

    public String getAdditional_data() {
        return additional_data;
    }

    String client;
    String type;
    String wheight;
    String t_O;
    String p;
    String hi;
    String number;
    String additional_data;


    @Override
    public String toString() {
        return "TechnicalReview{" +
                "client='" + client + '\'' +
                ", type='" + type + '\'' +
                ", wheight='" + wheight + '\'' +
                ", T_O='" + t_O + '\'' +
                ", P='" + p + '\'' +
                ", HI='" + hi + '\'' +
                ", number='" + number + '\'' +
                ", additional_data='" + additional_data + '\'' +
                '}';
    }
}
