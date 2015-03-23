/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bryce
 */
@Stateless
public class SeriesBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "RandomSelectorPU")
    private EntityManager em;
    
    public Series findSeriesByName(String name)
    {
        return em.find(Series.class, name);
    }
    
    public Series createSeries(Series show)
    {
        em.persist(show);
        return show;
    }
    
    public void removeSeries(Series show)
    {
        show = em.find(Series.class, show.getName());
        em.remove(show);
    }
    
    public void incSeries(Series show)
    {
        em.merge(show.incEps());
    }
    
    public void decSeries(Series show)
    {
        em.merge(show.decEps());
    }
    
    public List<Series> getAllSeries()
    {
        // Uncomment for different sorting behavior, make sure to comment out the switched out behavior
        // Query query = em.createQuery("Select s From Series s Order By s.epsBehind", Series.class);
        Query query = em.createQuery("Select s From Series s Order By s.seriesName", Series.class);
        List<Series> showList = query.getResultList();
        return showList;
    }
    
    public void clearList()
    {
        em.clear();
    }
}
