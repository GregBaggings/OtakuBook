package io.anime.otakubook.models.animes.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class SearchResponse implements Parcelable {

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel in) {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };
    @SerializedName("request_hash")
    private String requestHash;
    @SerializedName("request_cached")
    private Boolean requestCached;
    @SerializedName("result")
    private List<Result> result;
    @SerializedName("result_last_page")
    private Integer resultLastPage;

    protected SearchResponse(Parcel in) {
        requestHash = in.readString();
        byte tmpRequestCached = in.readByte();
        requestCached = tmpRequestCached == 0 ? null : tmpRequestCached == 1;
        result = in.createTypedArrayList(Result.CREATOR);
        if (in.readByte() == 0) {
            resultLastPage = null;
        } else {
            resultLastPage = in.readInt();
        }
    }

    public static Creator<SearchResponse> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestHash", requestHash).append("requestCached", requestCached).append("result", result).append("resultLastPage", resultLastPage).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestHash);
        dest.writeByte((byte) (requestCached == null ? 0 : requestCached ? 1 : 2));
        dest.writeTypedList(result);
        if (resultLastPage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(resultLastPage);
        }
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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getResultLastPage() {
        return resultLastPage;
    }

    public void setResultLastPage(Integer resultLastPage) {
        this.resultLastPage = resultLastPage;
    }
}
