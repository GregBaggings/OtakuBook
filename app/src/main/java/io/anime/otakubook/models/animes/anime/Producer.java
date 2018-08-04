package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Producer implements Parcelable {

    public static final Creator<Producer> CREATOR = new Creator<Producer>() {
        @Override
        public Producer createFromParcel(Parcel in) {
            return new Producer(in);
        }

        @Override
        public Producer[] newArray(int size) {
            return new Producer[size];
        }
    };
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;

    protected Producer(Parcel in) {
        url = in.readString();
        name = in.readString();
    }

    public static Creator<Producer> getCREATOR() {
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
