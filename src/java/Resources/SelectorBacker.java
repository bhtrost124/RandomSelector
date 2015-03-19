/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Bryce
 */
@Named
@ApplicationScoped
public class SelectorBacker {

    @Inject
    private SeriesBean seriesBean;
    private List<Series> serList;
    private Random rnGesus;
    private Series series;
    private String randSeries;

    /**
     * Creates a new instance of SelectorBacker
     */
    public SelectorBacker() {
        this.series = new Series();
        this.rnGesus = new Random();
        
    }

    @PostConstruct
    public void init()
    {
        try {
            this.serList = this.seriesBean.getAllSeries();
        } catch (Exception ex) {
            this.serList = new ArrayList<>();
        }
    }
    
    public void selectRandomSeries() {
        List<Series> randList = new ArrayList<>();
        for (Series s : this.serList) {
            if (s.getEps() != 0) {
                randList.add(s);
            }
        }
        this.randSeries = randList.get(this.rnGesus.nextInt(randList.size())).getName();
    }

    public String getRandSeries() {
        return this.randSeries;
    }

    public String addSeries() {
        this.seriesBean.createSeries(this.series);
        this.serList.add(this.series);
        return "index.xhtml";
    }

    public String removeSeries(Series series) {
        this.seriesBean.removeSeries(series);
        this.serList.remove(series);
        return "index.xhtml";
    }

    public String addEpsBehind(Series series) {
        this.seriesBean.incSeries(series);
        this.serList = this.seriesBean.getAllSeries();
        return "index.xhtml";
    }

    public String decEpsBehind(Series series) {
        if (series.getEps() != 0) {
            this.seriesBean.decSeries(series);
            this.serList = this.seriesBean.getAllSeries();
        }
        return "index.xhtml";
    }

    public List<Series> getSeriesList() {
        return this.serList;
    }

    public Series getSeries() {
        return this.series;
    }

}
