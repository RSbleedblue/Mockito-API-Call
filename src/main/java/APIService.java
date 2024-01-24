import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APIService {
    private final HttpClient httpClient;
    private final String url;

    public APIService(HttpClient httpClient,String url){
        this.httpClient = httpClient;
        this.url = url;
    }
    public String FetchDataFromApi() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
       // System.out.println(response.body().toString());
        //return response.body();
        String file = response.body();
        if(response.body() == null){
            return null;
        }
       // System.out.println(file);
        return response.body();
    }
    public String FetchNames() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        if(jsonNode == null){
            return null;
        }
        List<String> list = new ArrayList<>();
        for(JsonNode names : jsonNode.path("value")){
           list.add(names.path("name").asText());
        };
        StringBuilder ans = new StringBuilder();
        for(String word: list){
            ans.append(word);
        }
        return ans.toString();
    }
    public String SearchNames(String name) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> res = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        ObjectMapper obj = new ObjectMapper();
        JsonNode jsonNode = obj.readTree(res.body());
        for(JsonNode names : jsonNode.path("value")){
            if(names.path("name").asText().equals(name)){
                return "Found";
            }
        }
        return null;
    }
    public int countSets() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        HttpResponse<String> res = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        JsonNode jsonNode = new ObjectMapper().readTree(res.body());
        List<String> list = new ArrayList<>();
        for(JsonNode names : jsonNode.path("value")){
            list.add(names.path("name").asText());
        }
        return list.size();
    }
    public static void main(String args[]) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        APIService api = new APIService(httpClient,"https://services.odata.org/V4/(S(adslzk2deqsbc23y5au0xrwk))/TripPinServiceRW/");
        String result = api.FetchDataFromApi();
        String allNames = api.FetchNames();
        String foundNames = api.SearchNames("Photos");
        int countSet = api.countSets();
        System.out.println(countSet);
        System.out.println(result);
       // System.out.println(allNames);
    }
}
