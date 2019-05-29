package entity;

import java.util.ArrayList;

/**
 * Created by shaka on 29.05.2019.
 */
public class Subscriber {
    private String FIO;
    private String phoneNumber;
    private double balance;
    private ArrayList<Service> services;

    public Subscriber(String FIO, String phoneNumber, double balance) {
        this.FIO = FIO;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
