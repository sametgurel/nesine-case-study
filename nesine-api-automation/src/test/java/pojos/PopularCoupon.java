package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PopularCoupon {

    private String couponHash;
    private List<CouponEvent> events;
    private String playedDate;
    private int count1H;
    private int count24H;
    private int countLast1H;
    private int countLast24H;
    private int count;
    private int eventCount;
    private String minEventDate;
    private String creationDate;
    private String totalOdd;
    private String lastPlayedDate;
    private boolean isWon;
    private String resultDate;
    private int orderIndex;
    private SharingInfo sharing;
    private String couponId;
    private int multiply;
    private boolean isIncludeUvEvent;
    private String minEventDateEpoch;
    private String playedDateEpoch;

    public PopularCoupon() {
    }

    public String getCouponHash() {
        return couponHash;
    }

    public void setCouponHash(String couponHash) {
        this.couponHash = couponHash;
    }

    public List<CouponEvent> getEvents() {
        return events;
    }

    public void setEvents(List<CouponEvent> events) {
        this.events = events;
    }

    public String getPlayedDate() {
        return playedDate;
    }

    public void setPlayedDate(String playedDate) {
        this.playedDate = playedDate;
    }

    public int getCount1H() {
        return count1H;
    }

    public void setCount1H(int count1H) {
        this.count1H = count1H;
    }

    public int getCount24H() {
        return count24H;
    }

    public void setCount24H(int count24H) {
        this.count24H = count24H;
    }

    public int getCountLast1H() {
        return countLast1H;
    }

    public void setCountLast1H(int countLast1H) {
        this.countLast1H = countLast1H;
    }

    public int getCountLast24H() {
        return countLast24H;
    }

    public void setCountLast24H(int countLast24H) {
        this.countLast24H = countLast24H;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public String getMinEventDate() {
        return minEventDate;
    }

    public void setMinEventDate(String minEventDate) {
        this.minEventDate = minEventDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTotalOdd() {
        return totalOdd;
    }

    public void setTotalOdd(String totalOdd) {
        this.totalOdd = totalOdd;
    }

    public String getLastPlayedDate() {
        return lastPlayedDate;
    }

    public void setLastPlayedDate(String lastPlayedDate) {
        this.lastPlayedDate = lastPlayedDate;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public SharingInfo getSharing() {
        return sharing;
    }

    public void setSharing(SharingInfo sharing) {
        this.sharing = sharing;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public int getMultiply() {
        return multiply;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public boolean isIncludeUvEvent() {
        return isIncludeUvEvent;
    }

    public void setIncludeUvEvent(boolean includeUvEvent) {
        isIncludeUvEvent = includeUvEvent;
    }

    public String getMinEventDateEpoch() {
        return minEventDateEpoch;
    }

    public void setMinEventDateEpoch(String minEventDateEpoch) {
        this.minEventDateEpoch = minEventDateEpoch;
    }

    public String getPlayedDateEpoch() {
        return playedDateEpoch;
    }

    public void setPlayedDateEpoch(String playedDateEpoch) {
        this.playedDateEpoch = playedDateEpoch;
    }

    @Override
    public String toString() {
        return "PopularCoupon{" +
                "couponHash='" + couponHash + '\'' +
                ", eventCount=" + eventCount +
                ", events=" + (events != null ? events.size() : 0) +
                ", orderIndex=" + orderIndex +
                ", totalOdd='" + totalOdd + '\'' +
                ", count=" + count +
                '}';
    }
}
