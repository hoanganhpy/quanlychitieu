package vn.edu.hutech.quanlychitieu.model;

public class TransactionStatistic {
    public double beginMoneyAmount;
    public double endMoneyAmount;

    public TransactionStatistic(double beginMoneyAmount, double endMoneyAmount) {
        this.beginMoneyAmount = beginMoneyAmount;
        this.endMoneyAmount = endMoneyAmount;
    }

    public TransactionStatistic() {

    }

    public double getEndMoneyAmount() {
        return endMoneyAmount;
    }

    public void setEndMoneyAmount(double endMoneyAmount) {
        this.endMoneyAmount = endMoneyAmount;
    }

    public double getBeginMoneyAmount() {
        return beginMoneyAmount;
    }

    public void setBeginMoneyAmount(double beginMoneyAmount) {
        this.beginMoneyAmount = beginMoneyAmount;
    }
}
