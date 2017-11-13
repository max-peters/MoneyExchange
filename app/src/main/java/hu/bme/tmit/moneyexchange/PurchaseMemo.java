package hu.bme.tmit.moneyexchange;


public class PurchaseMemo {

    private String product;
    private String date;
    private double priceHUF;
    private double priceEUR;
    private long id;


    public PurchaseMemo(String product, String date, double priceHUF, double priceEUR, long id) {
        this.product = product;
        this.date = date;
        this.priceHUF = priceHUF;
        this.priceEUR = priceEUR;
        this.id = id;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public double getPriceHUF() {
        return priceHUF;
    }

    public double getPriceEUR() {
        return priceEUR;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return date + ": " + product + " for " + String.format("%.2f", priceEUR) + " EUR";
    }
}
