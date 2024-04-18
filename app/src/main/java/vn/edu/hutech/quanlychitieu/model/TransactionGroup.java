package vn.edu.hutech.quanlychitieu.model;

import java.io.Serializable;

public class TransactionGroup implements Serializable {

    public static final int INCOMING = 1;
    public static final int OUTGOING = 2;

    private int id;
    private String name;
    private int iconResource;
    private int type;

    public TransactionGroup(int id, String name, int iconResource, int type) {
        this.id = id;
        this.name = name;
        this.iconResource = iconResource;
        this.type = type;
    }

    public TransactionGroup(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public TransactionGroup() {
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
