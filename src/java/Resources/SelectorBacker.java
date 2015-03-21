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
    private final Random rnGesus;
    private String randSeries;
    private String seriesToAdd;

    /**
     * Creates a new instance of SelectorBacker
     */
    public SelectorBacker() {
        this.rnGesus = new Random();
        this.seriesToAdd = "";
    }

    @PostConstruct
    public void init() {
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
        Series added = new Series(this.seriesToAdd);
        this.seriesBean.createSeries(added);
        this.serList.add(added);
        this.seriesToAdd = "";
        this.randSeries = "";
        return "index.xhtml";
    }

    public String removeSeries(Series series) {
        this.seriesBean.removeSeries(series);
        this.serList.remove(series);
        this.randSeries = "";

        return "index.xhtml";
    }

    public String addEpsBehind(Series series) {
        this.seriesBean.incSeries(series);
        this.serList = this.seriesBean.getAllSeries();
        this.randSeries = "";

        return "index.xhtml";
    }

    public String decEpsBehind(Series series) {
        if (series.getEps() != 0) {
            this.seriesBean.decSeries(series);
            this.serList = this.seriesBean.getAllSeries();
            this.randSeries = "";

        }
        return "index.xhtml";
    }

    public List<Series> getSeriesList() {
        return this.serList;
    }

    public String getSeriesToAdd() {
        return this.seriesToAdd;
    }

    public void setSeriesToAdd(String s) {
        this.seriesToAdd = s;
    }
}
