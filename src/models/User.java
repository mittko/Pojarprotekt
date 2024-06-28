package models;

public class User {
    private String usser;
    private String password;

    public void setService_Order(String service_Order) {
        this.service_Order = service_Order;
    }

    public void setWorking_Book(String working_Book) {
        this.working_Book = working_Book;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public void setNew_Ext(String new_Ext) {
        this.new_Ext = new_Ext;
    }

    public void setHidden_Menu(String hidden_Menu) {
        this.hidden_Menu = hidden_Menu;
    }

    public void setAcquittance(String acquittance) {
        this.acquittance = acquittance;
    }

    private String service_Order;
    private String working_Book;
    private String invoice;
    private String reports;
    private String new_Ext;
    private String hidden_Menu;
    private String acquittance;

    public void setUsser(String usser) {
        this.usser = usser;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getUsser() {
        return usser;
    }

    public String getPassword() {
        return password;
    }

    public String getService_Order() {
        return service_Order;
    }

    public String getWorking_Book() {
        return working_Book;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getReports() {
        return reports;
    }

    public String getNew_Ext() {
        return new_Ext;
    }

    public String getHidden_Menu() {
        return hidden_Menu;
    }

    public String getAcquittance() {
        return acquittance;
    }
}
