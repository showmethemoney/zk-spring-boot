package zk.springboot.bean;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import zk.springboot.bean.deserializer.IndustryCodeDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TWStock implements Serializable {
    @JsonProperty("ch")
    private String id = null;
    @JsonProperty("ex")
    private String ex = null;
    @JsonProperty("nf")
    private String fullName = null;
    @JsonProperty("n")
    private String name = null;
    @JsonProperty("key")
    @JsonDeserialize(using = IndustryCodeDeserializer.class)
    private String industryCode = null;

    public TWStock() {}

    public TWStock(String id, String ex, String fullName, String name, String industryCode) {
        super();
        this.id = id;
        this.ex = ex;
        this.fullName = fullName;
        this.name = name;
        this.industryCode = industryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

}
