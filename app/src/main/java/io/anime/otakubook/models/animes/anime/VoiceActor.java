package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class VoiceActor implements Parcelable {

    public static final Creator<VoiceActor> CREATOR = new Creator<VoiceActor>() {
        @Override
        public VoiceActor createFromParcel(Parcel in) {
            return new VoiceActor(in);
        }

        @Override
        public VoiceActor[] newArray(int size) {
            return new VoiceActor[size];
        }
    };
    @SerializedName("mal_id")
    private Integer malId;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("language")
    private String language;
    @SerializedName("image_url")
    private String imageUrl;

    protected VoiceActor(Parcel in) {
        if (in.readByte() == 0) {
            malId = null;
        } else {
            malId = in.readInt();
        }
        name = in.readString();
        url = in.readString();
        language = in.readString();
        imageUrl = in.readString();
    }

    public static Creator<VoiceActor> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (malId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(malId);
        }
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(language);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("name", name).append("url", url).append("language", language).append("imageUrl", imageUrl).toString();
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
