package control;

import com.mysql.cj.jdbc.Driver;
import entity.Data;
import entity.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 * Created by shaka on 22.11.2017.
 */
public class DBController {
    private static final String USERNAME = Data.login;
    private static final String PASSWORD = Data.pass;
    private static final String URL = "jdbc:mysql://185.146.157.97:3306/registration?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public Statement statement;
    private Connection connection;


    public DBController() {

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

        } catch (SQLException ex) {
            System.out.println("Ошибка при подключении");
        }
    }

    public void recharge(String phoneNumber, double amount){
        String sAmount = Double.toString(amount);
        String updateBalance = String.format("update phone.users set balance=balance+%2s where phone=\"%s\"", sAmount, phoneNumber);

        try {
            statement.executeUpdate(updateBalance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect(String phoneNumber, Service service){
        String serviceName = service.getName();
        String cost = Double.toString(service.getCost());

        String getIds = String.format("select users.id as user_id,service.id as service_id " +
                                        "from phone.users, phone.service " +
                                        "where service_name=\"%s\" and phone=\"%s\""
                                        , serviceName
                                        , phoneNumber);

        String updateBalance = String.format("update phone.users set balance=balance-%s where phone=\"%s\"", cost, phoneNumber);

        try {
            ResultSet resultSet = statement.executeQuery(getIds);
            resultSet.next();
            int serviceID = resultSet.getInt("service_id");
            int userId = resultSet.getInt("user_id");
            Date startDate = new Date();
            Date endDate = new Date(startDate.getTime() + 2592000000L);

            String insertConnection = String.format("insert into phone.connection " +
                                                    "values (0, %d, %d, %d, %d)"
                                                    , userId
                                                    , serviceID
                                                    ,startDate.getTime()
                                                    ,endDate.getTime());



            statement.execute(insertConnection);
            statement.executeUpdate(updateBalance);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void renewal(String phoneNumber, Service service){
        String serviceName = service.getName();
        String cost = Double.toString(service.getCost());

        String getIds = String.format("select users.id as user_id,service.id as service_id " +
                        "from phone.users, phone.service " +
                        "where service_name=\"%s\" and phone=\"%s\""
                , serviceName
                , phoneNumber);

        String updateBalance = String.format("update phone.users set balance=balance-%s where phone=\"%s\"", cost, phoneNumber);

        try {
            ResultSet resultSet = statement.executeQuery(getIds);
            resultSet.next();
            int serviceID = resultSet.getInt("service_id");
            int userId = resultSet.getInt("user_id");

            String updateEndTime = String.format("update phone.connection " +
                                                "set start_date = end_date,end_date=end_date+2592000000 " +
                                                "where user_id=%d and service_id=%d"
                                                , userId,
                                                serviceID);
            statement.execute(updateEndTime);
            statement.executeUpdate(updateBalance);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkDebt(String phoneNumber){
        String selectBalance = String.format("select balance " +
                                            "from phone.users " +
                                            "where phone=\"%s\"", phoneNumber);

        try {
            ResultSet resultSet = statement.executeQuery(selectBalance);
            resultSet.next();
            double balance = resultSet.getDouble("balance");
            if(balance<0){
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ObservableList<Service> loadServices(){
        String selectServices = "select * from phone.service";

        try {
            ResultSet resultSet = statement.executeQuery(selectServices);

            ObservableList<Service> services = FXCollections.observableArrayList();
            while (resultSet.next()){
                String name = resultSet.getString("service_name");
                double cost = resultSet.getDouble("cost");
                int period = resultSet.getInt("period");
                Service service = new Service(name, cost, period);

                services.add(service);
            }

            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
