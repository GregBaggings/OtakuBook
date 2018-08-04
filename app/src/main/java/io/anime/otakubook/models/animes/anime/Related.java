package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Related implements Parcelable {

    public static final Creator<Related> CREATOR = new Creator<Related>() {
        @Override
        public Related createFromParcel(Parcel in) {
            return new Related(in);
        }

        @Override
        public Related[] newArray(int size) {
            return new Related[size];
        }
    };
    @SerializedName("Adaptation")
    @Expose
    private List<Adaptation> adaptation;
    @SerializedName("Sequel")
    @Expose
    private List<Sequel> sequel;
    @SerializedName("Summary")
    @Expose
    private List<Summary> summary;
    @SerializedName("Side story")
    @Expose
    private List<SideStory> sideStory;
    @SerializedName("Other")
    @Expose
    private List<Other> other;

    protected Related(Parcel in) {
        adaptation = in.createTypedArrayList(Adaptation.CREATOR);
        sequel = in.createTypedArrayList(Sequel.CREATOR);
        summary = in.createTypedArrayList(Summary.CREATOR);
        sideStory = in.createTypedArrayList(SideStory.CREATOR);
        other = in.createTypedArrayList(Other.CREATOR);
    }

    public static Creator<Related> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(adaptation);
        dest.writeTypedList(sequel);
        dest.writeTypedList(summary);
        dest.writeTypedList(sideStory);
        dest.writeTypedList(other);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("adaptation", adaptation).append("sequel", sequel).append("summary", summary).append("sideStory", sideStory).append("other", other).toString();
    }

    public List<Adaptation> getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(List<Adaptation> adaptation) {
        this.adaptation = adaptation;
    }

    public List<Sequel> getSequel() {
        return sequel;
    }

    public void setSequel(List<Sequel> sequel) {
        this.sequel = sequel;
    }

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    public List<SideStory> getSideStory() {
        return sideStory;
    }

    public void setSideStory(List<SideStory> sideStory) {
        this.sideStory = sideStory;
    }

    public List<Other> getOther() {
        return other;
    }

    public void setOther(List<Other> other) {
        this.other = other;
    }
}
