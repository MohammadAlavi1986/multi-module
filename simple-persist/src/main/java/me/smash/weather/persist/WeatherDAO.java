package me.smash.weather.persist;

import java.util.ArrayList;
import java.util.List;
import me.smash.weather.model.Location;
import me.smash.weather.model.Weather;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class WeatherDAO extends HibernateDaoSupport {

  public WeatherDAO() {
  }

  public void save(Weather weather) {
    getHibernateTemplate().save(weather);
  }

  public Weather load(Integer id) {
    return (Weather) getHibernateTemplate().load(Weather.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Weather> recentForLocation(final Location location) {
    return (List<Weather>) getHibernateTemplate().execute(session -> {
      Query query = getSession().getNamedQuery("Weather.byLocation");
      query.setParameter("location", location);
      return new ArrayList<Weather>(query.list());
    });
  }
}