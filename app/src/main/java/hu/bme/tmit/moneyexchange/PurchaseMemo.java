package hu.bme.tmit.moneyexchange;


public class PurchaseMemo {

    private String product;
    private String date;
    private double price;
    private long id;


    public PurchaseMemo(String product, String date, double price, long id) {
        this.product = product;
        this.date = date;
        this.price = price;
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


    public double getPrice() {
        return price;
    }

    public void setId(double price) {
        this.price = price;
    }

    public long getID(){
        return id;
    }

    public void setID(long id){
        this.id = id;
    }

    @Override
    public String toString() {
        String output = id + " " + date + ": " + product + " for " + price;

        return output;
    }
}
