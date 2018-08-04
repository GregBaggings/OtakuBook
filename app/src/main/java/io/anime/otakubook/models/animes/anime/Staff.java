package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Staff implements Parcelable {

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("mal_id")
    private Integer malId;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("role")
    private String role;

    protected Staff(Parcel in) {
        imageUrl = in.readString();
        if (in.readByte() == 0) {
            malId = null;
        } else {
            malId = in.readInt();
        }
        url = in.readString();
        name = in.readString();
        role = in.readString();
    }

    public static Creator<Staff> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        if (malId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(malId);
        }
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(role);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("imageUrl", imageUrl).append("malId", malId).append("url", url).append("name", name).append("role", role).toString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
