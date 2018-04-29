package zk.springboot.bean;

import java.io.Serializable;

public class StockRecord implements Serializable {
    private String price = null;
    private String count = null;

    public StockRecord() {}

    public StockRecord(String price, String count) {
        this.price = price;
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
