/*
 * Attention: The following code is part of a project that is not "Complete," 
 *  meaning that updates and other assorted changes have yet to take shape. Also
 *  note that this was originally written with the intention of nobody but myself 
 *  seeing or using it.Tthere are some aspects of this code that would not
 *  be present in a true enterprise application, most notably of which would be 
 *  attempts to make the usage more "safe," or to prevent the user from making
 *  changes they did not mean to make.
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
 * 
 * This Application was designed for a user to keep track of shows that they are
 * watching, how far behind in that show they are, and in the event of not being
 * able to decide what to watch randomly selects one for them.
 * 
 * This backing bean is application scoped for the reason that I was the only
 * intended user, thus the series list can be considered as always being present
 * for the application. It was originally developed as request scoped, and it
 * worked just fine like that, but I wanted to eliminate the constant database
 * queries as well as test out other scopes.
 */
@Named
@ApplicationScoped
public class SelectorBacker {

    @Inject
    private SeriesBean seriesBean;
    private List<Series> serList;
    private final Random random;
    private String randSeries;
    private String seriesToAdd;

    /**
     * Creates a new instance of SelectorBacker
     */
    public SelectorBacker() {
        this.random = new Random();
        this.seriesToAdd = "";
        this.randSeries = "";
    }

    public boolean isRandSet() {
        return !this.randSeries.equals("");
    }

    @PostConstruct
    public void init() {
        try {
            this.serList = this.seriesBean.getAllSeries();
        } catch (Exception ex) {
            this.serList = new ArrayList<>();
        }
    }

    /*
    Performs the logic to select a random series from the series list. Series that
    are caught up on are not included in the selection.
    */
    public void selectRandomSeries() {
        List<Series> randList = new ArrayList<>();
        for (Series s : this.serList) {
            if (s.getEps() != 0) {
                randList.add(s);
            }
        }
        this.randSeries = randList.get(this.random.nextInt(randList.size())).getName();
    }

    public String getRandSeries() {
        return this.randSeries;
    }

    public String addSeries() {
        Series added = new Series(this.seriesToAdd);
        this.seriesBean.createSeries(added);
        this.serList = this.seriesBean.getAllSeries();
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
