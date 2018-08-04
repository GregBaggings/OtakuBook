package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Character implements Parcelable {

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
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
    @SerializedName("voice_actor")
    private List<VoiceActor> voiceActor;

    protected Character(Parcel in) {
        imageUrl = in.readString();
        if (in.readByte() == 0) {
            malId = null;
        } else {
            malId = in.readInt();
        }
        url = in.readString();
        name = in.readString();
        role = in.readString();
        voiceActor = in.createTypedArrayList(VoiceActor.CREATOR);
    }

    public static Creator<Character> getCREATOR() {
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
        dest.writeTypedList(voiceActor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("imageUrl", imageUrl).append("malId", malId).append("url", url).append("name", name).append("role", role).append("voiceActor", voiceActor).toString();
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

    public List<VoiceActor> getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(List<VoiceActor> voiceActor) {
        this.voiceActor = voiceActor;
    }
}
