package com.iremote.vo;

public class ProductorListVO {
    private String name;
    private String productor;

    public ProductorListVO(String name, String productor) {
        this.name = name;
        this.productor = productor;
    }
    public ProductorListVO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }
}
