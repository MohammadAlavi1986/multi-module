package me.smash.weather;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.InputStream;
import me.smash.weather.model.Weather;
import me.smash.weather.model.Location;
import me.smash.weather.model.Condition;
import me.smash.weather.model.Atmosphere;
import me.smash.weather.model.Wind;
import org.junit.Test;

public class YahooParserTest {
  @Test
  public void testParse() throws Exception {
    InputStream inputStream  = this.getClass().getResourceAsStream("/ny-weather.xml");
    Weather weather = new YahooParser().parse("123", inputStream);
    Location location = weather.getLocation();
    Condition condition = weather.getCondition();
    Atmosphere atmosphere = weather.getAtmosphere();
    Wind wind = weather.getWind();

    assertThat(weather).isNotNull();
    assertThat(location).isNotNull();
    assertThat(condition).isNotNull();
    assertThat(atmosphere).isNotNull();
    assertThat(wind).isNotNull();
  }
}