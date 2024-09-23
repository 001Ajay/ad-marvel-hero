package com.dev.ad_marvel_hero.pojo;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Analysis {
    public int charactersCount;
    public Map<String, Integer> comicsCountMap;
    public String startTime;
    public String endTime;
    public List<String> characterWithMultipleEntries;

    public Analysis(String startTime){
        this.charactersCount = -1;
        this.comicsCountMap = new HashMap<>();
        this.startTime = startTime;
        this.characterWithMultipleEntries = new LinkedList<>();
    }
}
