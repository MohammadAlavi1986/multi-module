package me.smash.weather.persist;

import java.util.ArrayList;
import java.util.List;
import me.smash.weather.model.Location;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class LocationDAO extends HibernateDaoSupport {

  public LocationDAO() {
  }

  public Location findByZip(final String zip) {
    return (Location) getHibernateTemplate().execute(session -> {
      Query query = getSession().getNamedQuery("Location.uniqueByZip");
      query.setString("zip", zip);
      return query.uniqueResult();
    });
  }

  @SuppressWarnings("unchecked")
  public List<Location> all() {
    return new ArrayList<Location>(getHibernateTemplate().loadAll(Location.class));
  }

}