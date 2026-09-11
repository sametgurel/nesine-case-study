package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharingInfo {

    private String profileImageUri;
    private String username;
    private int cupLevel;
    private String encryptedMemberId;
    private String feedId;
    private String sharingDate;
    private long sharingDateEpoch;

    public SharingInfo() {
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCupLevel() {
        return cupLevel;
    }

    public void setCupLevel(int cupLevel) {
        this.cupLevel = cupLevel;
    }

    public String getEncryptedMemberId() {
        return encryptedMemberId;
    }

    public void setEncryptedMemberId(String encryptedMemberId) {
        this.encryptedMemberId = encryptedMemberId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getSharingDate() {
        return sharingDate;
    }

    public void setSharingDate(String sharingDate) {
        this.sharingDate = sharingDate;
    }

    public long getSharingDateEpoch() {
        return sharingDateEpoch;
    }

    public void setSharingDateEpoch(long sharingDateEpoch) {
        this.sharingDateEpoch = sharingDateEpoch;
    }

    @Override
    public String toString() {
        return "SharingInfo{" +
                "profileImageUri='" + profileImageUri + '\'' +
                ", username='" + username + '\'' +
                ", cupLevel=" + cupLevel +
                ", encryptedMemberId='" + encryptedMemberId + '\'' +
                ", feedId='" + feedId + '\'' +
                ", sharingDate='" + sharingDate + '\'' +
                ", sharingDateEpoch=" + sharingDateEpoch +
                '}';
    }
}
