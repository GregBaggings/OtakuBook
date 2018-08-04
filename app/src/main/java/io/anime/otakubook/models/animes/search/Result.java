package io.anime.otakubook.models.animes.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Result implements Parcelable {

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    @SerializedName("mal_id")
    private int malId;
    @SerializedName("url")
    private String url;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("type")
    private String type;
    @SerializedName("score")
    private Double score;
    @SerializedName("episodes")
    private int episodes;
    @SerializedName("members")
    private int members;

    protected Result(Parcel in) {
        malId = in.readInt();
        url = in.readString();
        imageUrl = in.readString();
        title = in.readString();
        description = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        episodes = in.readInt();
        members = in.readInt();
    }

    public static Creator<Result> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(malId);
        dest.writeString(url);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(type);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(score);
        }
        dest.writeInt(episodes);
        dest.writeInt(members);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("url", url).append("imageUrl", imageUrl).append("title", title).append("description", description).append("type", type).append("score", score).append("episodes", episodes).append("members", members).toString();
    }

    public int getMalId() {
        return malId;
    }

    public void setMalId(int malId) {
        this.malId = malId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
