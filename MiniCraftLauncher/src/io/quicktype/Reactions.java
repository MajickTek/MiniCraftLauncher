package io.quicktype;

import com.fasterxml.jackson.annotation.*;

public class Reactions {
    private long the1;
    private long reactions1;
    private long confused;
    private long eyes;
    private long heart;
    private long hooray;
    private long laugh;
    private long rocket;
    private long totalCount;
    private String url;

    @JsonProperty("+1")
    public long getThe1() { return the1; }
    @JsonProperty("+1")
    public void setThe1(long value) { this.the1 = value; }

    @JsonProperty("-1")
    public long getReactions1() { return reactions1; }
    @JsonProperty("-1")
    public void setReactions1(long value) { this.reactions1 = value; }

    @JsonProperty("confused")
    public long getConfused() { return confused; }
    @JsonProperty("confused")
    public void setConfused(long value) { this.confused = value; }

    @JsonProperty("eyes")
    public long getEyes() { return eyes; }
    @JsonProperty("eyes")
    public void setEyes(long value) { this.eyes = value; }

    @JsonProperty("heart")
    public long getHeart() { return heart; }
    @JsonProperty("heart")
    public void setHeart(long value) { this.heart = value; }

    @JsonProperty("hooray")
    public long getHooray() { return hooray; }
    @JsonProperty("hooray")
    public void setHooray(long value) { this.hooray = value; }

    @JsonProperty("laugh")
    public long getLaugh() { return laugh; }
    @JsonProperty("laugh")
    public void setLaugh(long value) { this.laugh = value; }

    @JsonProperty("rocket")
    public long getRocket() { return rocket; }
    @JsonProperty("rocket")
    public void setRocket(long value) { this.rocket = value; }

    @JsonProperty("total_count")
    public long getTotalCount() { return totalCount; }
    @JsonProperty("total_count")
    public void setTotalCount(long value) { this.totalCount = value; }

    @JsonProperty("url")
    public String getURL() { return url; }
    @JsonProperty("url")
    public void setURL(String value) { this.url = value; }
}
