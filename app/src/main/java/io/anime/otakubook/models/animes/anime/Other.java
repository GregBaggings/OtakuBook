package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Other implements Parcelable {

    public static final Creator<Other> CREATOR = new Creator<Other>() {
        @Override
        public Other createFromParcel(Parcel in) {
            return new Other(in);
        }

        @Override
        public Other[] newArray(int size) {
            return new Other[size];
        }
    };
    @SerializedName("mal_id")
    private Integer malId;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;

    protected Other(Parcel in) {
        if (in.readByte() == 0) {
            malId = null;
        } else {
            malId = in.readInt();
        }
        type = in.readString();
        url = in.readString();
        title = in.readString();
    }

    public static Creator<Other> getCREATOR() {
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
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("type", type).append("url", url).append("title", title).toString();
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
