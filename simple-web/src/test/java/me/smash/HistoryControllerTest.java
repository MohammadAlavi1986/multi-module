package me.smash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import me.smash.weather.model.Atmosphere;
import me.smash.weather.model.Condition;
import me.smash.weather.model.Location;
import me.smash.weather.model.Weather;
import me.smash.weather.model.Wind;
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

  private static final String ZIP_CODE = "123243";
  private static final String LOCATION_CITY = "locationCity";
  private static final String LOCATION_COUNTRY = "locationCountry";
  private static final String LOCATION_REGION = "locationRegion";
  private static final String CONDITION_CODE = "conditionCode";
  private static final String CONDITION_DATE = "2019-09-11";
  private static final String CONDITION_TEMP = "conditionTemp";
  private static final String CONDITION_TEXT = "conditionText";
  private static final String WIND_CHILL = "windChill";
  private static final String WIND_DIRECTION = "windDirection";
  private static final String WIND_SPEED = "windSpeed";
  private static final String ATMOSPHERE_HUMIDITY = "atmosphereHumidity";
  private static final String ATMOSPHERE_PRESSURE = "atmospherePressure";
  private static final String ATMOSPHERE_RISING = "atmosphereRising";
  private static final String ATMOSPHERE_VISIBILITY = "atmosphereVisibility";
  private static final long ATMOSPHERE_ID = 4L;
  private static final long WIND_ID = 2L;
  private static final long CONDITION_ID = 3L;
  private static final long WEATHER_ID = 1L;
  private static final Date WEATHER_DATE = new Date(64535126361L);

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
    Weather weather = createWeather();
    when(servletRequest.getParameter("zip")).thenReturn(ZIP_CODE);
    when(locationDAO.findByZip(ZIP_CODE)).thenReturn(location);
    when(weatherDAO.recentForLocation(location)).thenReturn(Collections.singletonList(weather));

    ModelAndView modelAndView = historyController.handleRequest(servletRequest, null);

    assertThat(modelAndView.getViewName()).isEqualTo("history");
    assertThat(modelAndView.getModel().get("location")).isEqualTo(location);
    List<Weather> weathers = (List<Weather>) modelAndView.getModel().get("weathers");
    assertThat(weathers).isEqualTo(Collections.singletonList(weather));
    assertWeather(weathers.get(0));
  }

  private static Weather createWeather() {
    Weather weather = new Weather();
    weather.setId(WEATHER_ID);
    Location location = new Location();
    location.setCity(LOCATION_CITY);
    location.setCountry(LOCATION_COUNTRY);
    location.setRegion(LOCATION_REGION);
    location.setZip(ZIP_CODE);
    Condition condition = new Condition();
    condition.setCode(CONDITION_CODE);
    condition.setDate(CONDITION_DATE);
    condition.setId(CONDITION_ID);
    condition.setTemp(CONDITION_TEMP);
    condition.setText(CONDITION_TEXT);
    condition.setWeather(weather);
    Wind wind = new Wind();
    wind.setId(WIND_ID);
    wind.setChill(WIND_CHILL);
    wind.setDirection(WIND_DIRECTION);
    wind.setSpeed(WIND_SPEED);
    wind.setWeather(weather);
    Atmosphere atmosphere = new Atmosphere();
    atmosphere.setHumidity(ATMOSPHERE_HUMIDITY);
    atmosphere.setPressure(ATMOSPHERE_PRESSURE);
    atmosphere.setId(ATMOSPHERE_ID);
    atmosphere.setRising(ATMOSPHERE_RISING);
    atmosphere.setVisibility(ATMOSPHERE_VISIBILITY);
    atmosphere.setWeather(weather);
    weather.setAtmosphere(atmosphere);
    weather.setCondition(condition);
    weather.setLocation(location);
    weather.setWind(wind);
    weather.setDate(WEATHER_DATE);

    return weather;
  }

  private static void assertWeather(Weather weather) {
    Atmosphere atmosphere = weather.getAtmosphere();
    Condition condition = weather.getCondition();
    Wind wind = weather.getWind();
    Location location = weather.getLocation();

    assertThat(weather.getId()).isEqualTo(WEATHER_ID);
    assertThat(weather.getDate()).isEqualTo(WEATHER_DATE);

    assertThat(atmosphere.getId()).isEqualTo(ATMOSPHERE_ID);
    assertThat(atmosphere.getHumidity()).isEqualTo(ATMOSPHERE_HUMIDITY);
    assertThat(atmosphere.getPressure()).isEqualTo(ATMOSPHERE_PRESSURE);
    assertThat(atmosphere.getRising()).isEqualTo(ATMOSPHERE_RISING);
    assertThat(atmosphere.getVisibility()).isEqualTo(ATMOSPHERE_VISIBILITY);
    assertThat(atmosphere.getWeather()).isEqualTo(weather);

    assertThat(condition.getId()).isEqualTo(CONDITION_ID);
    assertThat(condition.getCode()).isEqualTo(CONDITION_CODE);
    assertThat(condition.getDate()).isEqualTo(CONDITION_DATE);
    assertThat(condition.getTemp()).isEqualTo(CONDITION_TEMP);
    assertThat(condition.getText()).isEqualTo(CONDITION_TEXT);
    assertThat(condition.getWeather()).isEqualTo(weather);

    assertThat(wind.getId()).isEqualTo(WIND_ID);
    assertThat(wind.getChill()).isEqualTo(WIND_CHILL);
    assertThat(wind.getDirection()).isEqualTo(WIND_DIRECTION);
    assertThat(wind.getSpeed()).isEqualTo(WIND_SPEED);
    assertThat(wind.getWeather()).isEqualTo(weather);


    assertThat(location.getZip()).isEqualTo(ZIP_CODE);
    assertThat(location.getCity()).isEqualTo(LOCATION_CITY);
    assertThat(location.getCountry()).isEqualTo(LOCATION_COUNTRY);
    assertThat(location.getRegion()).isEqualTo(LOCATION_REGION);
  }


}
