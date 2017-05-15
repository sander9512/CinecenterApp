package nl.avans.prog3les1.cinecenter.Domain;
/**
 * Created by marni on 28-3-2017.
 */

public class Rate {

    private String Id;
    private String rate;
    private double price;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
