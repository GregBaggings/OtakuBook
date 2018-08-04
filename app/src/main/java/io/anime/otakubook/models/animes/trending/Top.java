package io.anime.otakubook.models.animes.trending;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Top implements Parcelable {

    public final static Parcelable.Creator<Top> CREATOR = new Creator<Top>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Top createFromParcel(Parcel in) {
            return new Top(in);
        }

        public Top[] newArray(int size) {
            return (new Top[size]);
        }

    };
    @SerializedName("mal_id")
    @Expose
    private Integer malId;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("members")
    @Expose
    private Integer members;
    @SerializedName("airing_start")
    @Expose
    private String airingStart;
    @SerializedName("airing_end")
    @Expose
    private Object airingEnd;
    @SerializedName("episodes")
    @Expose
    private Integer episodes;

    protected Top(Parcel in) {
        this.malId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rank = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.score = ((Double) in.readValue((Double.class.getClassLoader())));
        this.members = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.airingStart = ((String) in.readValue((String.class.getClassLoader())));
        this.airingEnd = ((Object) in.readValue((Object.class.getClassLoader())));
        this.episodes = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Top() {
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getAiringStart() {
        return airingStart;
    }

    public void setAiringStart(String airingStart) {
        this.airingStart = airingStart;
    }

    public Object getAiringEnd() {
        return airingEnd;
    }

    public void setAiringEnd(Object airingEnd) {
        this.airingEnd = airingEnd;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("rank", rank).append("url", url).append("imageUrl", imageUrl).append("title", title).append("type", type).append("score", score).append("members", members).append("airingStart", airingStart).append("airingEnd", airingEnd).append("episodes", episodes).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(malId);
        dest.writeValue(rank);
        dest.writeValue(url);
        dest.writeValue(imageUrl);
        dest.writeValue(title);
        dest.writeValue(type);
        dest.writeValue(score);
        dest.writeValue(members);
        dest.writeValue(airingStart);
        dest.writeValue(airingEnd);
        dest.writeValue(episodes);
    }

    public int describeContents() {
        return 0;
    }

}
