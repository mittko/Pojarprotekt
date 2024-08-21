package models;

import java.util.List;

public class InvoiceModels<T> {

    List<InvoiceModel> parentInvoiceModels;
    List<InvoiceModel> childInvoiceModels;

    public List<InvoiceModel> getParentInvoiceModels() {
        return parentInvoiceModels;
    }

    public void setParentInvoiceModels(List<InvoiceModel> parentInvoiceModels) {
        this.parentInvoiceModels = parentInvoiceModels;
    }

    public List<InvoiceModel> getChildInvoiceModels() {
        return childInvoiceModels;
    }

    public void setChildInvoiceModels(List<InvoiceModel> childInvoiceModels) {
        this.childInvoiceModels = childInvoiceModels;
    }
}
