package vn.edu.hutech.quanlychitieu.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int id;

    private Double moneyAmount;

    private TransactionGroup group;

    private String note;

    private Date date;

    //Vi thuong hay the ATM
    private int walletType;

    public Transaction(Double moneyAmount, TransactionGroup group, String note, Date date, int walletType) {
        this.moneyAmount = moneyAmount;
        this.group = group;
        this.note = note;
        this.date = date;
        this.walletType = walletType;
    }

    public Transaction(int id, Double moneyAmount, TransactionGroup group, String note, Date date, int walletType) {
        this.id = id;
        this.moneyAmount = moneyAmount;
        this.group = group;
        this.note = note;
        this.date = date;
        this.walletType = walletType;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public TransactionGroup getGroup() {
        return group;
    }

    public void setGroup(TransactionGroup group) {
        this.group = group;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int type) {
        this.walletType = type;
    }
}
