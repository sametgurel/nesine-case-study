package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PopularCouponsResponse {

    private int sc;
    private List<PopularCoupon> d;
    private List<ErrorItem> el;
    private Object ml;

    public PopularCouponsResponse() {
    }

    public int getSc() {
        return sc;
    }

    public void setSc(int sc) {
        this.sc = sc;
    }

    public List<PopularCoupon> getD() {
        return d;
    }

    public void setD(List<PopularCoupon> d) {
        this.d = d;
    }

    public List<ErrorItem> getEl() {
        return el;
    }

    public void setEl(List<ErrorItem> el) {
        this.el = el;
    }

    public Object getMl() {
        return ml;
    }

    public void setMl(Object ml) {
        this.ml = ml;
    }

    @Override
    public String toString() {
        return "PopularCouponsResponse{" +
                "sc=" + sc +
                ", d=" + (d != null ? d.size() : "null") +
                ", el=" + el +
                ", ml=" + ml +
                '}';
    }
}
