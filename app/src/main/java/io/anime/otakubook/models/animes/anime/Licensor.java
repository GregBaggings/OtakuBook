package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Licensor implements Parcelable {

    public static final Creator<Licensor> CREATOR = new Creator<Licensor>() {
        @Override
        public Licensor createFromParcel(Parcel in) {
            return new Licensor(in);
        }

        @Override
        public Licensor[] newArray(int size) {
            return new Licensor[size];
        }
    };
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;

    protected Licensor(Parcel in) {
        url = in.readString();
        name = in.readString();
    }

    public static Creator<Licensor> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("url", url).append("name", name).toString();
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
}
