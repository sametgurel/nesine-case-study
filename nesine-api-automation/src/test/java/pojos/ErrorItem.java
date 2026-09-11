package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorItem {

    @JsonProperty("c")
    private int c;

    @JsonProperty("m")
    private String m;

    public ErrorItem() {
    }

    public ErrorItem(int c, String m) {
        this.c = c;
        this.m = m;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "ErrorItem{" +
                "c=" + c +
                ", m='" + m + '\'' +
                '}';
    }
}
