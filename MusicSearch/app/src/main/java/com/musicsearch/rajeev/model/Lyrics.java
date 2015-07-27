package com.musicsearch.rajeev.model;

/**
 * Model for Lyrics.
 */
public class Lyrics {
    private String song;

    private String isOnTakedownList;

    private String page_id;

    private String page_namespace;

    private String artist;

    private String lyrics;

    private String url;

    public String getSong ()
    {
        return song;
    }

    public void setSong (String song)
    {
        this.song = song;
    }

    public String getIsOnTakedownList ()
    {
        return isOnTakedownList;
    }

    public void setIsOnTakedownList (String isOnTakedownList)
    {
        this.isOnTakedownList = isOnTakedownList;
    }

    public String getPage_id ()
    {
        return page_id;
    }

    public void setPage_id (String page_id)
    {
        this.page_id = page_id;
    }

    public String getPage_namespace ()
    {
        return page_namespace;
    }

    public void setPage_namespace (String page_namespace)
    {
        this.page_namespace = page_namespace;
    }

    public String getArtist ()
    {
        return artist;
    }

    public void setArtist (String artist)
    {
        this.artist = artist;
    }

    public String getLyrics ()
    {
        return lyrics;
    }

    public void setLyrics (String lyrics)
    {
        this.lyrics = lyrics;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [song = "+song+", isOnTakedownList = "+isOnTakedownList+", page_id = "+page_id+", page_namespace = "+page_namespace+", artist = "+artist+", lyrics = "+lyrics+", url = "+url+"]";
    }
}
