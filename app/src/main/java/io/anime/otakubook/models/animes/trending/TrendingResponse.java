package io.anime.otakubook.models.animes.trending;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


public class TrendingResponse implements Parcelable {

    public final static Parcelable.Creator<TrendingResponse> CREATOR = new Creator<TrendingResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TrendingResponse createFromParcel(Parcel in) {
            return new TrendingResponse(in);
        }

        public TrendingResponse[] newArray(int size) {
            return (new TrendingResponse[size]);
        }

    };
    @SerializedName("request_hash")
    @Expose
    private String requestHash;
    @SerializedName("request_cached")
    @Expose
    private Boolean requestCached;
    @SerializedName("top")
    @Expose
    private List<Top> top = null;

    protected TrendingResponse(Parcel in) {
        this.requestHash = ((String) in.readValue((String.class.getClassLoader())));
        this.requestCached = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.top, (io.anime.otakubook.models.animes.trending.Top.class.getClassLoader()));
    }

    public TrendingResponse() {
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public Boolean getRequestCached() {
        return requestCached;
    }

    public void setRequestCached(Boolean requestCached) {
        this.requestCached = requestCached;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestHash", requestHash).append("requestCached", requestCached).append("top", top).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(requestHash);
        dest.writeValue(requestCached);
        dest.writeList(top);
    }

    public int describeContents() {
        return 0;
    }

}
