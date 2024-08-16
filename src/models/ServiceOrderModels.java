package models;

import java.util.List;

public class ServiceOrderModels {

    public ServiceOrderModels() {}

    List<ServiceOrderModel> serviceOrders;

    List<String> barcodesToUpdateInProtokol;

    public List<ServiceOrderModel> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrderModel> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public List<String> getBarcodesToUpdateInProtokol() {
        return barcodesToUpdateInProtokol;
    }

    public void setBarcodesToUpdateInProtokol(List<String> barcodesToUpdateInProtokol) {
        this.barcodesToUpdateInProtokol = barcodesToUpdateInProtokol;
    }

    public List<ServiceOrderModel> getServiceOrderModels() {
        return serviceOrders;
    }

    public void setServiceOrderModels(List<ServiceOrderModel> serviceOrderModels) {
        this.serviceOrders = serviceOrderModels;
    }
}
