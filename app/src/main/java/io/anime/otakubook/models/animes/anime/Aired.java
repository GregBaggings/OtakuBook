package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Aired implements Parcelable {

    public static final Creator<Aired> CREATOR = new Creator<Aired>() {
        @Override
        public Aired createFromParcel(Parcel in) {
            return new Aired(in);
        }

        @Override
        public Aired[] newArray(int size) {
            return new Aired[size];
        }
    };
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    protected Aired(Parcel in) {
        from = in.readString();
        to = in.readString();
    }

    public static Creator<Aired> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("from", from).append("to", to).toString();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
