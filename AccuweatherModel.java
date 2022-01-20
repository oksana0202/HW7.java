/**
 * JAVA 2 Homework7
 *
 * @author Oksana Ilyakova
 * @version 20.01.2022
 */
 
public class AccuweatherModel implements WeatherModel {
//http://dataservice.accuweather.com/forecasts/v1/daily/1day/
// http://dataservice.accuweather.com/forecasts/v1/daily/5day/

    private static final String PROTOCOL = "http";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "V1";
    private static final String DAILY = "daily";
    private static final String FIVE_DAYS = "5days"
    private static final String ONE_DAY = "1day"
    private static final String API_KEY = "RZn4AO9JYqiARlMgGgxqYiMn3B0A8TaQ";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMappernt = new ObjectMapper();
    private String selectedCity;



    @Override
    public void getWeather(String selectedCity, Period period) {
        swith(period){
            case ONE_DAY:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOCOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARA, API_KEY)
                        .build();
                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response oneDayForecastsResponse = okHttpClient.newCall(request).execute();
                String weatherResponse = oneDayForecastsResponse.body().string();

                System.out.println(weatherResponse);
                break;

            case FIVE_DAYS:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOCOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAYS)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARA, API_KEY)
                        .build();
                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response oneDayForecastsResponse = okHttpClient.newCall(request).execute();
                String weatherResponse = oneDayForecastsResponse.body().string();

                System.out.println(weatherResponse);
                break;
        }
    }

    public String detectCityKey(String selectedCity) {
        this.selectedCity = selectedCity;
        //http://dataservice.accuweather.com/locations/v1/cities/autocomplete
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter(name:"q", selectedCity)
                        .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader(name:"accept", value"application/json")
                 .build();

        Response response  = okHttpClient.newCall(request).execute();
        String responseCity = response.body().string();

        String cityKey = objectMapper.readTree(responseCity).get(0).at("/Key").asText();
        return cityKey;
    }

}
