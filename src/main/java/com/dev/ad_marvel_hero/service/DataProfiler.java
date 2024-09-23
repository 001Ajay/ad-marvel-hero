package com.dev.ad_marvel_hero.service;

import com.dev.ad_marvel_hero.pojo.Analysis;
import com.dev.ad_marvel_hero.pojo.characters.Characters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class DataProfiler {

    private MarvelApiService service;
    
    
    public void startAnalysis(Analysis analysis) {

        log.debug("Analysis started...");
        Characters characters = service.getCharactersApi(0,1);
        Integer charactersCount = characters.getData().getTotal();

        log.debug("Characters count reported : {}", charactersCount);
        int pageSize = 100;
        for(int i = 0; i < charactersCount; i=i+pageSize){
            Characters charactersApiResponse = service.getCharactersApi(i, pageSize);
            collectDataFromResponse(charactersApiResponse, analysis);
        }

        Map<String, Integer> comicsCountMap = analysis.getComicsCountMap();
        int charactersFound = comicsCountMap.size();
        analysis.setCharactersCount(charactersFound);
        comicsCountMap.forEach((key, value) -> System.out.println(key + " --> " + value));
        log.debug("Characters found : {}", charactersFound);

        /* // Comics
        List<String> characters = new LinkedList<>();
        characters.add("3-D Man");
        characters.add("A-Bomb (HAS)");
        characters.forEach(character -> service.getComics(character));
         */

        log.debug("Analysis complete...");
    }

    private void collectDataFromResponse(Characters response, Analysis analysis){
        response
                .getData()
                .getResults()
                .forEach(result -> {
                    String characterName = result.getName();
                    Integer comicsCount = result.getComics().getAvailable();
                    Map<String, Integer> comicsCountMap = analysis.getComicsCountMap();
                    Integer integer = comicsCountMap.get(characterName);
                    if (integer == null) {
                        integer = comicsCount;
                    } else {
                        log.debug("Multiple entries for character '{}' detected...", characterName);
                        analysis.getCharacterWithMultipleEntries().add(characterName);
                        integer = integer + comicsCount;
                    }
                    comicsCountMap.put(characterName, integer);
                });
    }
}