package com.dev.ad_marvel_hero.controller;


import com.dev.ad_marvel_hero.pojo.Analysis;
import com.dev.ad_marvel_hero.service.DataProfiler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController("/analysis")
@Slf4j
public class DataController {

    private final DataProfiler dataProfiler;
    private final DateFormat dateFormat;

    private Analysis analysedResult;

    public DataController(DataProfiler dataProfiler){
        this.dataProfiler = dataProfiler;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.analysedResult = null;
    }

    @GetMapping("/start")
    public Analysis startAnalysis(){
        log.debug("GET /analysis/start ---->");
        String startTime = dateFormat.format(Calendar.getInstance().getTime());
        Analysis analysis = new Analysis(startTime);
        log.debug("Analysis start time : {} ...", startTime);
        dataProfiler.startAnalysis(analysis);
        log.debug("{} Characters found.", analysis.getCharactersCount());
        String endTime = dateFormat.format(Calendar.getInstance().getTime());
        analysis.setEndTime(endTime);
        this.analysedResult = analysis;
        return analysis;
    }

    @GetMapping("/comics/{character}/count")
    public Integer count(@PathVariable @NonNull String character){
        Integer count = this.analysedResult.getComicsCountMap().get(character);
        log.debug("Character {} has appeared in {} number of comic(s).", character, count);
        return count != null ? count : -1;
    }

    @GetMapping("/refresh")
    public Analysis refresh(){
        log.debug("GET /analysis/refresh ---->");
        return startAnalysis();
    }
}