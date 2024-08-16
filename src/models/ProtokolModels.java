package models;

import java.util.List;

public class ProtokolModels {

    public ProtokolModels(){}
    List<ProtokolModel> list;

    public List<ProtokolModel> getList() {
        return list;
    }

    public void setList(List<ProtokolModel> list) {
        this.list = list;
    }

    public void setParts(List<PartsModel> parts) {
        this.parts = parts;
    }

    List<PartsModel> parts;

    public List<PartsModel> getParts() {
        return parts;
    }





}
