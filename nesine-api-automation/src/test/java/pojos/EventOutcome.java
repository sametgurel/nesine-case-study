package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventOutcome {

    private String id;
    private String name;
    private String mbs;
    private int nsnMarketTypeId;
    private String odd;
    private String status;
    private Object handicap;
    private int outcomeGroup;
    private Object marketId;
    private boolean isActive;
    private boolean isLive;
    private boolean isMarketSpecial;

    public EventOutcome() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbs() {
        return mbs;
    }

    public void setMbs(String mbs) {
        this.mbs = mbs;
    }

    public int getNsnMarketTypeId() {
        return nsnMarketTypeId;
    }

    public void setNsnMarketTypeId(int nsnMarketTypeId) {
        this.nsnMarketTypeId = nsnMarketTypeId;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getHandicap() {
        return handicap;
    }

    public void setHandicap(Object handicap) {
        this.handicap = handicap;
    }

    public int getOutcomeGroup() {
        return outcomeGroup;
    }

    public void setOutcomeGroup(int outcomeGroup) {
        this.outcomeGroup = outcomeGroup;
    }

    public Object getMarketId() {
        return marketId;
    }

    public void setMarketId(Object marketId) {
        this.marketId = marketId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isMarketSpecial() {
        return isMarketSpecial;
    }

    public void setMarketSpecial(boolean marketSpecial) {
        isMarketSpecial = marketSpecial;
    }

    @Override
    public String toString() {
        return "EventOutcome{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", odd='" + odd + '\'' +
                ", status='" + status + '\'' +
                ", mbs='" + mbs + '\'' +
                '}';
    }
}
