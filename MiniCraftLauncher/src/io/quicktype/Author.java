package io.quicktype;

import com.fasterxml.jackson.annotation.*;

public class Author {
    private String login;
    private long id;
    private String nodeID;
    private String avatarURL;
    private String gravatarID;
    private String url;
    private String htmlURL;
    private String followersURL;
    private String followingURL;
    private String gistsURL;
    private String starredURL;
    private String subscriptionsURL;
    private String organizationsURL;
    private String reposURL;
    private String eventsURL;
    private String receivedEventsURL;
    private String type;
    private boolean siteAdmin;

    @JsonProperty("login")
    public String getLogin() { return login; }
    @JsonProperty("login")
    public void setLogin(String value) { this.login = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("node_id")
    public String getNodeID() { return nodeID; }
    @JsonProperty("node_id")
    public void setNodeID(String value) { this.nodeID = value; }

    @JsonProperty("avatar_url")
    public String getAvatarURL() { return avatarURL; }
    @JsonProperty("avatar_url")
    public void setAvatarURL(String value) { this.avatarURL = value; }

    @JsonProperty("gravatar_id")
    public String getGravatarID() { return gravatarID; }
    @JsonProperty("gravatar_id")
    public void setGravatarID(String value) { this.gravatarID = value; }

    @JsonProperty("url")
    public String getURL() { return url; }
    @JsonProperty("url")
    public void setURL(String value) { this.url = value; }

    @JsonProperty("html_url")
    public String getHTMLURL() { return htmlURL; }
    @JsonProperty("html_url")
    public void setHTMLURL(String value) { this.htmlURL = value; }

    @JsonProperty("followers_url")
    public String getFollowersURL() { return followersURL; }
    @JsonProperty("followers_url")
    public void setFollowersURL(String value) { this.followersURL = value; }

    @JsonProperty("following_url")
    public String getFollowingURL() { return followingURL; }
    @JsonProperty("following_url")
    public void setFollowingURL(String value) { this.followingURL = value; }

    @JsonProperty("gists_url")
    public String getGistsURL() { return gistsURL; }
    @JsonProperty("gists_url")
    public void setGistsURL(String value) { this.gistsURL = value; }

    @JsonProperty("starred_url")
    public String getStarredURL() { return starredURL; }
    @JsonProperty("starred_url")
    public void setStarredURL(String value) { this.starredURL = value; }

    @JsonProperty("subscriptions_url")
    public String getSubscriptionsURL() { return subscriptionsURL; }
    @JsonProperty("subscriptions_url")
    public void setSubscriptionsURL(String value) { this.subscriptionsURL = value; }

    @JsonProperty("organizations_url")
    public String getOrganizationsURL() { return organizationsURL; }
    @JsonProperty("organizations_url")
    public void setOrganizationsURL(String value) { this.organizationsURL = value; }

    @JsonProperty("repos_url")
    public String getReposURL() { return reposURL; }
    @JsonProperty("repos_url")
    public void setReposURL(String value) { this.reposURL = value; }

    @JsonProperty("events_url")
    public String getEventsURL() { return eventsURL; }
    @JsonProperty("events_url")
    public void setEventsURL(String value) { this.eventsURL = value; }

    @JsonProperty("received_events_url")
    public String getReceivedEventsURL() { return receivedEventsURL; }
    @JsonProperty("received_events_url")
    public void setReceivedEventsURL(String value) { this.receivedEventsURL = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("site_admin")
    public boolean getSiteAdmin() { return siteAdmin; }
    @JsonProperty("site_admin")
    public void setSiteAdmin(boolean value) { this.siteAdmin = value; }
}
