package me.smash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import me.smash.weather.model.Location;
import me.smash.weather.model.Weather;
import me.smash.weather.persist.LocationDAO;
import me.smash.weather.persist.WeatherDAO;
import me.smash.web.HistoryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class HistoryControllerTest {

  public static final String ZIP_CODE = "123243";
  @Mock
  private LocationDAO locationDAO;
  @Mock
  private WeatherDAO weatherDAO;
  @Mock
  private HttpServletRequest servletRequest;
  @InjectMocks
  private HistoryController historyController;

  @Test
  public void handleRequest() {
    Location location = new Location();
    Weather weather = new Weather();
    when(servletRequest.getParameter("zip")).thenReturn(ZIP_CODE);
    when(locationDAO.findByZip(ZIP_CODE)).thenReturn(location);
    when(weatherDAO.recentForLocation(location)).thenReturn(Collections.singletonList(weather));

    ModelAndView modelAndView = historyController.handleRequest(servletRequest, null);

    assertThat(modelAndView.getViewName()).isEqualTo("history");
    assertThat(modelAndView.getModel().get("location")).isEqualTo(location);
    assertThat(modelAndView.getModel().get("weathers")).isEqualTo(Collections.singletonList(weather));
  }

}
