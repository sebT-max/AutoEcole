package com.example.AutoEcole.bll.serviceImpl;
import com.example.AutoEcole.bll.service.MapboxService;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapboxServiceImpl implements MapboxService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String MAPBOX_TOKEN =  dotenv.get("MAPBOX_TOKEN"); // Remplacez par votre token Mapbox
    private static final String MAPBOX_API_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/{address}.json?access_token={MAPBOX_TOKEN}";

//    public String getGeocodeData(String address) throws Exception {
//        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + address + ".json?access_token=" + MAPBOX_TOKEN;
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGet request = new HttpGet(url);
//        CloseableHttpResponse response = client.execute(request);
//        HttpEntity entity = (HttpEntity) response.getEntity();
//        String result = EntityUtils.toString((org.apache.http.HttpEntity) entity);
//        client.close();
//        return result;
//    }
    @Override
    public String getGeocodeData(String address) {
        String url = MAPBOX_API_URL.replace("{address}", address).replace("{MAPBOX_TOKEN}", MAPBOX_TOKEN );
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
