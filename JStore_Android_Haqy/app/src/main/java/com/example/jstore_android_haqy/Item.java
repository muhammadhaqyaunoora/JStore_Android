package com.example.jstore_android_haqy;

public class Item {
    private int id;
    private String name;
    private int price;
    private String category;
    private String status;
    private Supplier supplier;

    public Item(int id, String name, int price, String category, String status, Supplier supplier)
    {
        this.id=id;
        this.name=name;
        this.price=price;
        this.category=category;
        this.status=status;
        this.supplier=supplier;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getPrice()
    {
        return this.price;
    }

    public String getCategory()
    {
        return this.category;
    }

    public Supplier getSupplier()
    {
        return this.supplier;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
}
