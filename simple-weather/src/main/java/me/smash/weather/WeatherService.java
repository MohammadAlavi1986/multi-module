package me.smash.weather;

import java.io.InputStream;
import me.smash.weather.model.Weather;

public class WeatherService {

  private YahooParser yahooParser;
  private YahooRetriever yahooRetriever;

  public WeatherService() {
  }

  public Weather retrieveForecast(String zip) throws Exception {
    InputStream dataIn = getYahooRetriever().retrieve(zip);
    return getYahooParser().parse(zip, dataIn);
  }

  public void setYahooParser(YahooParser yahooParser) {
    this.yahooParser = yahooParser;
  }

  public void setYahooRetriever(YahooRetriever yahooRetriever) {
    this.yahooRetriever = yahooRetriever;
  }

  private YahooParser getYahooParser() {
    return yahooParser;
  }

  private YahooRetriever getYahooRetriever() {
    return yahooRetriever;
  }
}
