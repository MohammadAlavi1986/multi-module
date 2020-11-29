package me.smash.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import me.smash.weather.model.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

  private static final String ZIP_CODE = "123424";
  @Mock
  private YahooParser yahooParser;
  @Mock
  private YahooRetriever yahooRetriever;
  @Mock
  private InputStream inputStream;
  @InjectMocks
  private WeatherService weatherService;

  @Test
  public void testRetrieveForecast() throws Exception {
    Weather weather = new Weather();
    when(yahooRetriever.retrieve(ZIP_CODE)).thenReturn(inputStream);
    when(yahooParser.parse(ZIP_CODE, inputStream)).thenReturn(weather);
    Weather forecast = weatherService.retrieveForecast(ZIP_CODE);

    assertThat(forecast).isEqualTo(forecast);
  }


}
