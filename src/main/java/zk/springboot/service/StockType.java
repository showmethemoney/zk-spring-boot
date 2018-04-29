package zk.springboot.service;

public enum StockType {
    TSE("上市股票", "tse"), OTC("上櫃股票", "otc");

    StockType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    private String name = null;
    private String type = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
