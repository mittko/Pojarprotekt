package models;

import java.util.List;

public class ServiceOrderBodyList {

    List<ServiceOrderModel> serviceOrders;

    public List<ServiceOrderModel> getServiceOrderModels() {
        return serviceOrders;
    }

    public void setServiceOrderModels(List<ServiceOrderModel> serviceOrderModels) {
        this.serviceOrders = serviceOrderModels;
    }
}
