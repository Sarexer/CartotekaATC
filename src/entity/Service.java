package entity;

/**
 * Created by shaka on 29.05.2019.
 */
public class Service {
    private String name;
    private double cost;
    private int period;

    public Service(String name, double cost, int period) {
        this.name = name;
        this.cost = cost;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return name +". Цена: " + cost + " руб/мес.";
    }
}
