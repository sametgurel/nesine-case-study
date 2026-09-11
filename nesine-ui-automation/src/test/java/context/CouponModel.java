package context;

import java.util.ArrayList;
import java.util.List;

public class CouponModel {

    private String couponTitle;
    private String totalOdd;
    private String playCount;
    private final List<EventItem> events = new ArrayList<>();

    public static class EventItem {
        private final String eventName;
        private final String eventDate;
        private final String marketName;
        private final String odd;

        public EventItem(String eventName, String eventDate, String marketName, String odd) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.marketName = marketName;
            this.odd = odd;
        }

        public String getEventName() {
            return eventName;
        }

        public String getEventDate() {
            return eventDate;
        }

        public String getMarketName() {
            return marketName;
        }

        public String getOdd() {
            return odd;
        }

        @Override
        public String toString() {
            return "EventItem{eventName='" + eventName + "', eventDate='" + eventDate + "', marketName='" + marketName + "', odd='" + odd + "'}";
        }
    }

    public void addEvent(String eventName, String eventDate, String marketName, String odd) {
        events.add(new EventItem(eventName, eventDate, marketName, odd));
    }

    public List<EventItem> getEvents() {
        return events;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getTotalOdd() {
        return totalOdd;
    }

    public void setTotalOdd(String totalOdd) {
        this.totalOdd = totalOdd;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    @Override
    public String toString() {
        return "CouponModel{title='" + couponTitle + "', totalOdd='" + totalOdd + "', events=" + events + "}";
    }
}
