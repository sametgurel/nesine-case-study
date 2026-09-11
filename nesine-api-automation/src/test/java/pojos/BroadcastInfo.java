package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BroadcastInfo {

    private String key;
    private int status;
    private String description;
    private String androidMinVersion;
    private Long broadCastChannelId;

    public BroadcastInfo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAndroidMinVersion() {
        return androidMinVersion;
    }

    public void setAndroidMinVersion(String androidMinVersion) {
        this.androidMinVersion = androidMinVersion;
    }

    public Long getBroadCastChannelId() {
        return broadCastChannelId;
    }

    public void setBroadCastChannelId(Long broadCastChannelId) {
        this.broadCastChannelId = broadCastChannelId;
    }

    @Override
    public String toString() {
        return "BroadcastInfo{" +
                "key='" + key + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", androidMinVersion='" + androidMinVersion + '\'' +
                ", broadCastChannelId=" + broadCastChannelId +
                '}';
    }
}
