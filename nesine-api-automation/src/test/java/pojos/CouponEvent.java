package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponEvent {

    private long betradarId;
    private double minOdd;
    private double maxOdd;
    private String type;
    private boolean isBank;
    private long bid;
    private String code;
    private String name;
    private String nameWithoutHandicap;
    private String groupName;
    private String startDate;
    private String startDay;
    private long startDateEpoch;
    private String startTime;
    private String resultHalf;
    private String resultMatch;
    private String minuteLabel;
    private String secondLabel;
    private boolean isActive;
    private boolean isNormalEvent;
    private boolean hasOfficialResult;
    private int isVar;
    private int isPoss;
    private List<EventOutcome> outcomes;
    private List<Object> eventScores;
    private List<Object> matchEvents;
    private List<Object> statisticsEvents;
    private List<Object> matchDateTimes;
    private int matchStatus;
    private boolean isFollowable;
    private BroadcastInfo broadcast;
    private long marketId;
    private String marketName;
    private double specialOddsValue;
    private boolean isLive;
    private boolean isMarketSpecial;
    private int sportId;
    private int marketStatus;
    private Object lsMatch;
    private List<Object> sr;
    private int orWaiting;
    private int cbsId;
    private int bof;
    private String matchTime;
    private String homeTeamTR;
    private String awayTeamTR;
    private int hasMatch;
    private String marketNo;
    private String channelName;
    private long marketSellEndDateEpoch;
    private int viewTypeId;
    private double specialOddsValue2;
    private double specialOddsValue3;
    private String statisticsUrl;
    private String navLink;
    private int eventTier;
    private boolean isValuable;

    public CouponEvent() {
    }

    public long getBetradarId() {
        return betradarId;
    }

    public void setBetradarId(long betradarId) {
        this.betradarId = betradarId;
    }

    public double getMinOdd() {
        return minOdd;
    }

    public void setMinOdd(double minOdd) {
        this.minOdd = minOdd;
    }

    public double getMaxOdd() {
        return maxOdd;
    }

    public void setMaxOdd(double maxOdd) {
        this.maxOdd = maxOdd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWithoutHandicap() {
        return nameWithoutHandicap;
    }

    public void setNameWithoutHandicap(String nameWithoutHandicap) {
        this.nameWithoutHandicap = nameWithoutHandicap;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public long getStartDateEpoch() {
        return startDateEpoch;
    }

    public void setStartDateEpoch(long startDateEpoch) {
        this.startDateEpoch = startDateEpoch;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getResultHalf() {
        return resultHalf;
    }

    public void setResultHalf(String resultHalf) {
        this.resultHalf = resultHalf;
    }

    public String getResultMatch() {
        return resultMatch;
    }

    public void setResultMatch(String resultMatch) {
        this.resultMatch = resultMatch;
    }

    public String getMinuteLabel() {
        return minuteLabel;
    }

    public void setMinuteLabel(String minuteLabel) {
        this.minuteLabel = minuteLabel;
    }

    public String getSecondLabel() {
        return secondLabel;
    }

    public void setSecondLabel(String secondLabel) {
        this.secondLabel = secondLabel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNormalEvent() {
        return isNormalEvent;
    }

    public void setNormalEvent(boolean normalEvent) {
        isNormalEvent = normalEvent;
    }

    public boolean isHasOfficialResult() {
        return hasOfficialResult;
    }

    public void setHasOfficialResult(boolean hasOfficialResult) {
        this.hasOfficialResult = hasOfficialResult;
    }

    public int getIsVar() {
        return isVar;
    }

    public void setIsVar(int isVar) {
        this.isVar = isVar;
    }

    public int getIsPoss() {
        return isPoss;
    }

    public void setIsPoss(int isPoss) {
        this.isPoss = isPoss;
    }

    public List<EventOutcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<EventOutcome> outcomes) {
        this.outcomes = outcomes;
    }

    public List<Object> getEventScores() {
        return eventScores;
    }

    public void setEventScores(List<Object> eventScores) {
        this.eventScores = eventScores;
    }

    public List<Object> getMatchEvents() {
        return matchEvents;
    }

    public void setMatchEvents(List<Object> matchEvents) {
        this.matchEvents = matchEvents;
    }

    public List<Object> getStatisticsEvents() {
        return statisticsEvents;
    }

    public void setStatisticsEvents(List<Object> statisticsEvents) {
        this.statisticsEvents = statisticsEvents;
    }

    public List<Object> getMatchDateTimes() {
        return matchDateTimes;
    }

    public void setMatchDateTimes(List<Object> matchDateTimes) {
        this.matchDateTimes = matchDateTimes;
    }

    public int getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
    }

    public boolean isFollowable() {
        return isFollowable;
    }

    public void setFollowable(boolean followable) {
        isFollowable = followable;
    }

    public BroadcastInfo getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(BroadcastInfo broadcast) {
        this.broadcast = broadcast;
    }

    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public double getSpecialOddsValue() {
        return specialOddsValue;
    }

    public void setSpecialOddsValue(double specialOddsValue) {
        this.specialOddsValue = specialOddsValue;
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

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(int marketStatus) {
        this.marketStatus = marketStatus;
    }

    public Object getLsMatch() {
        return lsMatch;
    }

    public void setLsMatch(Object lsMatch) {
        this.lsMatch = lsMatch;
    }

    public List<Object> getSr() {
        return sr;
    }

    public void setSr(List<Object> sr) {
        this.sr = sr;
    }

    public int getOrWaiting() {
        return orWaiting;
    }

    public void setOrWaiting(int orWaiting) {
        this.orWaiting = orWaiting;
    }

    public int getCbsId() {
        return cbsId;
    }

    public void setCbsId(int cbsId) {
        this.cbsId = cbsId;
    }

    public int getBof() {
        return bof;
    }

    public void setBof(int bof) {
        this.bof = bof;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamTR() {
        return homeTeamTR;
    }

    public void setHomeTeamTR(String homeTeamTR) {
        this.homeTeamTR = homeTeamTR;
    }

    public String getAwayTeamTR() {
        return awayTeamTR;
    }

    public void setAwayTeamTR(String awayTeamTR) {
        this.awayTeamTR = awayTeamTR;
    }

    public int getHasMatch() {
        return hasMatch;
    }

    public void setHasMatch(int hasMatch) {
        this.hasMatch = hasMatch;
    }

    public String getMarketNo() {
        return marketNo;
    }

    public void setMarketNo(String marketNo) {
        this.marketNo = marketNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public long getMarketSellEndDateEpoch() {
        return marketSellEndDateEpoch;
    }

    public void setMarketSellEndDateEpoch(long marketSellEndDateEpoch) {
        this.marketSellEndDateEpoch = marketSellEndDateEpoch;
    }

    public int getViewTypeId() {
        return viewTypeId;
    }

    public void setViewTypeId(int viewTypeId) {
        this.viewTypeId = viewTypeId;
    }

    public double getSpecialOddsValue2() {
        return specialOddsValue2;
    }

    public void setSpecialOddsValue2(double specialOddsValue2) {
        this.specialOddsValue2 = specialOddsValue2;
    }

    public double getSpecialOddsValue3() {
        return specialOddsValue3;
    }

    public void setSpecialOddsValue3(double specialOddsValue3) {
        this.specialOddsValue3 = specialOddsValue3;
    }

    public String getStatisticsUrl() {
        return statisticsUrl;
    }

    public void setStatisticsUrl(String statisticsUrl) {
        this.statisticsUrl = statisticsUrl;
    }

    public String getNavLink() {
        return navLink;
    }

    public void setNavLink(String navLink) {
        this.navLink = navLink;
    }

    public int getEventTier() {
        return eventTier;
    }

    public void setEventTier(int eventTier) {
        this.eventTier = eventTier;
    }

    public boolean isValuable() {
        return isValuable;
    }

    public void setValuable(boolean valuable) {
        isValuable = valuable;
    }

    @Override
    public String toString() {
        return "CouponEvent{" +
                "betradarId=" + betradarId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", minOdd=" + minOdd +
                ", maxOdd=" + maxOdd +
                ", type='" + type + '\'' +
                ", sportId=" + sportId +
                ", outcomes=" + (outcomes != null ? outcomes.size() : 0) +
                '}';
    }
}
