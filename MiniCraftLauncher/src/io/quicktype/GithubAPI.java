package io.quicktype;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

public class GithubAPI {
    private String url;
    private String assetsURL;
    private String uploadURL;
    private String htmlURL;
    private long id;
    private Author author;
    private String nodeID;
    private String tagName;
    private String targetCommitish;
    private String name;
    private boolean draft;
    private boolean prerelease;
    private OffsetDateTime createdAt;
    private OffsetDateTime publishedAt;
    private List<Asset> assets;
    private String tarballURL;
    private String zipballURL;
    private String body;
    private long mentionsCount;

    @JsonProperty("url")
    public String getURL() { return url; }
    @JsonProperty("url")
    public void setURL(String value) { this.url = value; }

    @JsonProperty("assets_url")
    public String getAssetsURL() { return assetsURL; }
    @JsonProperty("assets_url")
    public void setAssetsURL(String value) { this.assetsURL = value; }

    @JsonProperty("upload_url")
    public String getUploadURL() { return uploadURL; }
    @JsonProperty("upload_url")
    public void setUploadURL(String value) { this.uploadURL = value; }

    @JsonProperty("html_url")
    public String getHTMLURL() { return htmlURL; }
    @JsonProperty("html_url")
    public void setHTMLURL(String value) { this.htmlURL = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("author")
    public Author getAuthor() { return author; }
    @JsonProperty("author")
    public void setAuthor(Author value) { this.author = value; }

    @JsonProperty("node_id")
    public String getNodeID() { return nodeID; }
    @JsonProperty("node_id")
    public void setNodeID(String value) { this.nodeID = value; }

    @JsonProperty("tag_name")
    public String getTagName() { return tagName; }
    @JsonProperty("tag_name")
    public void setTagName(String value) { this.tagName = value; }

    @JsonProperty("target_commitish")
    public String getTargetCommitish() { return targetCommitish; }
    @JsonProperty("target_commitish")
    public void setTargetCommitish(String value) { this.targetCommitish = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("draft")
    public boolean getDraft() { return draft; }
    @JsonProperty("draft")
    public void setDraft(boolean value) { this.draft = value; }

    @JsonProperty("prerelease")
    public boolean getPrerelease() { return prerelease; }
    @JsonProperty("prerelease")
    public void setPrerelease(boolean value) { this.prerelease = value; }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() { return createdAt; }
    @JsonProperty("created_at")
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    @JsonProperty("published_at")
    public OffsetDateTime getPublishedAt() { return publishedAt; }
    @JsonProperty("published_at")
    public void setPublishedAt(OffsetDateTime value) { this.publishedAt = value; }

    @JsonProperty("assets")
    public List<Asset> getAssets() { return assets; }
    @JsonProperty("assets")
    public void setAssets(List<Asset> value) { this.assets = value; }

    @JsonProperty("tarball_url")
    public String getTarballURL() { return tarballURL; }
    @JsonProperty("tarball_url")
    public void setTarballURL(String value) { this.tarballURL = value; }

    @JsonProperty("zipball_url")
    public String getZipballURL() { return zipballURL; }
    @JsonProperty("zipball_url")
    public void setZipballURL(String value) { this.zipballURL = value; }

    @JsonProperty("body")
    public String getBody() { return body; }
    @JsonProperty("body")
    public void setBody(String value) { this.body = value; }

    @JsonProperty("mentions_count")
    public long getMentionsCount() { return mentionsCount; }
    @JsonProperty("mentions_count")
    public void setMentionsCount(long value) { this.mentionsCount = value; }
}
