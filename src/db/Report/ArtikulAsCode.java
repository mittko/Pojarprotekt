package db.Report;


public class ArtikulAsCode
{
    private String name;
    private String code;

    public ArtikulAsCode(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }
}