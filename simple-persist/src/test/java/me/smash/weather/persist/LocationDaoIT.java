package me.smash.weather.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import me.smash.weather.model.Location;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(MockitoJUnitRunner.class)
public class LocationDaoIT {

  private static final String ZIP_CODE = "2312";
  @Mock
  private HibernateTemplate hibernateTemplate;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  @Mock
  private SessionHolder sessionHolder;
  @Mock
  private Query query;
  @InjectMocks
  private LocationDAO locationDAO;


  @Before
  public void setUp() {
    when(hibernateTemplate.getSessionFactory()).thenReturn(sessionFactory);
    when(sessionFactory.openSession()).thenReturn(session);
    TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
    when(sessionHolder.containsSession(session)).thenReturn(true);
    when(session.getNamedQuery(anyString())).thenReturn(query);
    when(hibernateTemplate.execute(any(HibernateCallback.class))).thenAnswer(invocation -> {
      HibernateCallback callback = invocation.getArgument(0);
      return callback.doInHibernate(session);
    });
  }

  @Test
  public void test() {
    Location location = new Location();
    when(query.uniqueResult()).thenReturn(location);

    assertThat(locationDAO.findByZip(ZIP_CODE)).isEqualTo(location);

    verify(session).getNamedQuery("Location.uniqueByZip");
    verify(query).setString("zip", ZIP_CODE);

  }
}
