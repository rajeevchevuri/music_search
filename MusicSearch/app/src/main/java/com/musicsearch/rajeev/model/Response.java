package com.musicsearch.rajeev.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Response implements Serializable {

    private Integer resultCount;
    private List<MusicResponse> results = new ArrayList<MusicResponse>();

    /**
     * @return The resultCount
     */
    public Integer getResultCount() {
        return resultCount;
    }

    /**
     * @param resultCount The resultCount
     */
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    /**
     * @return The results
     */
    public List<MusicResponse> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<MusicResponse> results) {
        this.results = results;
    }

}
