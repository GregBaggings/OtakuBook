package io.anime.otakubook.models.animes.anime;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class AnimeResponse implements Parcelable {

    public static final Creator<AnimeResponse> CREATOR = new Creator<AnimeResponse>() {
        @Override
        public AnimeResponse createFromParcel(Parcel in) {
            return new AnimeResponse(in);
        }

        @Override
        public AnimeResponse[] newArray(int size) {
            return new AnimeResponse[size];
        }
    };
    @SerializedName("request_hash")
    private String requestHash;
    @SerializedName("request_cached")
    private Boolean requestCached;
    @SerializedName("mal_id")
    private Integer malId;
    @SerializedName("link_canonical")
    private String linkCanonical;
    @SerializedName("title")
    private String title;
    @SerializedName("title_english")
    private String titleEnglish;
    @SerializedName("title_japanese")
    private String titleJapanese;
    @SerializedName("title_synonyms")
    private Object titleSynonyms;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("source")
    private String source;
    @SerializedName("episodes")
    private Integer episodes;
    @SerializedName("status")
    private String status;
    @SerializedName("airing")
    private Boolean airing;
    @SerializedName("aired_string")
    private String airedString;
    @SerializedName("aired")
    private Aired aired;
    @SerializedName("duration")
    private String duration;
    @SerializedName("rating")
    private String rating;
    @SerializedName("score")
    private Double score;
    @SerializedName("scored_by")
    private Integer scoredBy;
    @SerializedName("rank")
    private Integer rank;
    @SerializedName("popularity")
    private Integer popularity;
    @SerializedName("members")
    private Integer members;
    @SerializedName("favorites")
    private Integer favorites;
    @SerializedName("synopsis")
    private String synopsis;
    @SerializedName("background")
    private String background;
    @SerializedName("premiered")
    private String premiered;
    @SerializedName("broadcast")
    private String broadcast;
    //@SerializedName("related")
    // private Related related;
    @SerializedName("producer")
    private List<Producer> producer;
    @SerializedName("licensor")
    private List<Licensor> licensor;
    @SerializedName("studio")
    private List<Studio> studio;
    @SerializedName("genre")
    private List<Genre> genre;
    @SerializedName("opening_theme")
    private List<String> openingTheme;
    @SerializedName("ending_theme")
    private List<String> endingTheme;
    @SerializedName("character")
    private List<Character> character;
    @SerializedName("staff")
    private List<Staff> staff;

    protected AnimeResponse(Parcel in) {
        requestHash = in.readString();
        byte tmpRequestCached = in.readByte();
        requestCached = tmpRequestCached == 0 ? null : tmpRequestCached == 1;
        if (in.readByte() == 0) {
            malId = null;
        } else {
            malId = in.readInt();
        }
        linkCanonical = in.readString();
        title = in.readString();
        titleEnglish = in.readString();
        titleJapanese = in.readString();
        imageUrl = in.readString();
        type = in.readString();
        source = in.readString();
        if (in.readByte() == 0) {
            episodes = null;
        } else {
            episodes = in.readInt();
        }
        status = in.readString();
        byte tmpAiring = in.readByte();
        airing = tmpAiring == 0 ? null : tmpAiring == 1;
        airedString = in.readString();
        aired = in.readParcelable(Aired.class.getClassLoader());
        duration = in.readString();
        rating = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        if (in.readByte() == 0) {
            scoredBy = null;
        } else {
            scoredBy = in.readInt();
        }
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readInt();
        }
        if (in.readByte() == 0) {
            members = null;
        } else {
            members = in.readInt();
        }
        if (in.readByte() == 0) {
            favorites = null;
        } else {
            favorites = in.readInt();
        }
        synopsis = in.readString();
        background = in.readString();
        premiered = in.readString();
        broadcast = in.readString();
        //    related = in.readParcelable(Related.class.getClassLoader());
        producer = in.createTypedArrayList(Producer.CREATOR);
        licensor = in.createTypedArrayList(Licensor.CREATOR);
        studio = in.createTypedArrayList(Studio.CREATOR);
        genre = in.createTypedArrayList(Genre.CREATOR);
        openingTheme = in.createStringArrayList();
        endingTheme = in.createStringArrayList();
        character = in.createTypedArrayList(Character.CREATOR);
        staff = in.createTypedArrayList(Staff.CREATOR);
    }

    public static Creator<AnimeResponse> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestHash);
        dest.writeByte((byte) (requestCached == null ? 0 : requestCached ? 1 : 2));
        if (malId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(malId);
        }
        dest.writeString(linkCanonical);
        dest.writeString(title);
        dest.writeString(titleEnglish);
        dest.writeString(titleJapanese);
        dest.writeString(imageUrl);
        dest.writeString(type);
        dest.writeString(source);
        if (episodes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(episodes);
        }
        dest.writeString(status);
        dest.writeByte((byte) (airing == null ? 0 : airing ? 1 : 2));
        dest.writeString(airedString);
        dest.writeParcelable(aired, flags);
        dest.writeString(duration);
        dest.writeString(rating);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(score);
        }
        if (scoredBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scoredBy);
        }
        if (rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank);
        }
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(popularity);
        }
        if (members == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(members);
        }
        if (favorites == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(favorites);
        }
        dest.writeString(synopsis);
        dest.writeString(background);
        dest.writeString(premiered);
        dest.writeString(broadcast);
        //   dest.writeParcelable(related, flags);
        dest.writeTypedList(producer);
        dest.writeTypedList(licensor);
        dest.writeTypedList(studio);
        dest.writeTypedList(genre);
        dest.writeStringList(openingTheme);
        dest.writeStringList(endingTheme);
        dest.writeTypedList(character);
        dest.writeTypedList(staff);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getLinkCanonical() {
        return linkCanonical;
    }

    public void setLinkCanonical(String linkCanonical) {
        this.linkCanonical = linkCanonical;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleJapanese() {
        return titleJapanese;
    }

    public void setTitleJapanese(String titleJapanese) {
        this.titleJapanese = titleJapanese;
    }

    public Object getTitleSynonyms() {
        return titleSynonyms;
    }

    public void setTitleSynonyms(Object titleSynonyms) {
        this.titleSynonyms = titleSynonyms;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAiring() {
        return airing;
    }

    public void setAiring(Boolean airing) {
        this.airing = airing;
    }

    public String getAiredString() {
        return airedString;
    }

    public void setAiredString(String airedString) {
        this.airedString = airedString;
    }

    public Aired getAired() {
        return aired;
    }

    public void setAired(Aired aired) {
        this.aired = aired;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getScoredBy() {
        return scoredBy;
    }

    public void setScoredBy(Integer scoredBy) {
        this.scoredBy = scoredBy;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    /* Disabled as currently it breaks the app, will correct it later.
    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }*/

    public List<Producer> getProducer() {
        return producer;
    }

    public void setProducer(List<Producer> producer) {
        this.producer = producer;
    }

    public List<Licensor> getLicensor() {
        return licensor;
    }

    public void setLicensor(List<Licensor> licensor) {
        this.licensor = licensor;
    }

    public List<Studio> getStudio() {
        return studio;
    }

    public void setStudio(List<Studio> studio) {
        this.studio = studio;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public List<String> getOpeningTheme() {
        return openingTheme;
    }

    public void setOpeningTheme(List<String> openingTheme) {
        this.openingTheme = openingTheme;
    }

    public List<String> getEndingTheme() {
        return endingTheme;
    }

    public void setEndingTheme(List<String> endingTheme) {
        this.endingTheme = endingTheme;
    }

    public List<Character> getCharacter() {
        return character;
    }

    public void setCharacter(List<Character> character) {
        this.character = character;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestHash", requestHash).append("requestCached", requestCached).append("malId", malId).append("linkCanonical", linkCanonical).append("title", title).append("titleEnglish", titleEnglish).append("titleJapanese", titleJapanese).append("titleSynonyms", titleSynonyms).append("imageUrl", imageUrl).append("type", type).append("source", source).append("episodes", episodes).append("status", status).append("airing", airing).append("airedString", airedString).append("aired", aired).append("duration", duration).append("rating", rating).append("score", score).append("scoredBy", scoredBy).append("rank", rank).append("popularity", popularity).append("members", members).append("favorites", favorites).append("synopsis", synopsis).append("background", background).append("premiered", premiered).append("broadcast", broadcast).append("producer", producer).append("licensor", licensor).append("studio", studio).append("genre", genre).append("openingTheme", openingTheme).append("endingTheme", endingTheme).append("character", character).append("staff", staff).toString();
    }
}
