package com.dev.ad_marvel_hero.service;

import com.dev.ad_marvel_hero.pojo.characters.Characters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@Slf4j
@AllArgsConstructor
public class MarvelApiService {

    private RestClient restClient;
    private RequestHelper requestHelper;

    public Characters getCharactersApi(int offset, int limit){
        log.debug("Fetching next {} records after processing {} record(s)...", limit, offset);
        String url = requestHelper.createCharactersRequestUrl(offset, limit);
        return restClient
                .get()
                .uri(url)
                .retrieve()
                .body(Characters.class);
    }

    public Integer getComicsApi(String name){
        String url = requestHelper.createComicsRequestUrl(name);
        Integer response = restClient
                .get()
                .uri(url)
                .retrieve()
                .body(Integer.class);
        log.debug("{} --> {}", name, response);
        return response;
    }
}
