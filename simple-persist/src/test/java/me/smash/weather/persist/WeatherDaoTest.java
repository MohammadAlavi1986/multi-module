package me.smash.weather.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Collections;
import java.util.List;
import me.smash.weather.model.Location;
import me.smash.weather.model.Weather;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
    WeatherDAO.class
})
public class WeatherDaoTest {

  @Mock
  private HibernateTemplate hibernateTemplate;
  @Mock
  private Session session;
  @Mock
  private Query query;
  @Spy
  private WeatherDAO weatherDAO = new WeatherDAO();


  @Before
  public void setUp() throws Exception {
    weatherDAO.setHibernateTemplate(hibernateTemplate);
    doReturn(session).when(weatherDAO, "getSession");
    when(hibernateTemplate.execute(any(HibernateCallback.class))).thenAnswer(invocation -> {
      HibernateCallback callback = invocation.getArgument(0);
      return callback.doInHibernate(session);
    });
  }

  @Test
  public void recentForLocation() {
    when(session.getNamedQuery("Weather.byLocation")).thenReturn(query);
    Weather weather = new Weather();
    when(query.list()).thenReturn(Collections.singletonList(weather));

    Location location = new Location();
    List<Weather> weathers = weatherDAO.recentForLocation(location);

    assertThat(weathers.size()).isEqualTo(1);
    assertThat(weathers.get(0)).isEqualTo(weather);
  }
}
