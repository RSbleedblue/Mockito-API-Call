import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class APIServiceTest {
    @Test
   public void FetchFiles() throws  Exception{
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        //Arrange
        FilePath fp = new FilePath( "E://IntelijFiles//untitled//src//JSON files//file.json");
        JsonNode jsonNode = fp.init();
        String ApiResponse = jsonNode.toString();
        String ExpectedResponse = new FilePath("E://IntelijFiles//untitled//src//JSON files//file.json").init().toString();
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);

        //Act
        when(mockResponse.body()).thenReturn(ApiResponse);
        when(httpClient.send(Mockito.any(),Mockito.any())).thenReturn(mockResponse);
        APIService apiService = new APIService(httpClient,"http://localhost:8080/mock-api");
        String result = apiService.FetchDataFromApi();
//        System.out.println(ExpectedResponse);
//        System.out.println(ApiResponse);
        //Assert
        assertEquals(ExpectedResponse,result);
    }
    @Test
    public void TestNullResponse() throws IOException, URISyntaxException, InterruptedException {
        // Arrange
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        String apiResponse = null;
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);

        // Act
        when(mockResponse.body()).thenReturn(apiResponse);
        when(httpClient.send(Mockito.any(), Mockito.any())).thenReturn(mockResponse);


        APIService apiService = new APIService(httpClient, "http://localhost:8080/mock-api"); // Providing null URL intentionally
        String result = apiService.FetchDataFromApi();

        // Assert
        assertNull(result);
    }
    @Test
    public void FetchName() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        //Arrange
        String expectedResult = "PhotosPeopleAirlinesAirportsMeGetNearestAirport";
        String FilterResponse = new FilePath("E://IntelijFiles//untitled//src//JSON files//file.json").init().toString();
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);

        //Act
        when(mockResponse.body()).thenReturn(FilterResponse);
        when(httpClient.send(Mockito.any(),Mockito.any())).thenReturn(mockResponse);
        APIService apiService = new APIService(httpClient,"http://localhost:8080/mock-api");
        String checkResult = apiService.FetchNames();

        //Assert
        assertEquals(expectedResult,checkResult);
    }
    @Test
    public void SearchNames() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        //Arrange
        String FilterResponse = new FilePath("E://IntelijFiles//untitled//src//JSON files//file.json").init().toString();
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);
        //Act
        when(mockResponse.body()).thenReturn(FilterResponse);
        when(httpClient.send(Mockito.any(),Mockito.any())).thenReturn(mockResponse);

        APIService apiService = new APIService(httpClient, "http://localhost:8080/mock-api");
        String checkResult = apiService.SearchNames("Pratik");

        //Assert
        assertNull(checkResult);

    }
    @Test
    public void CountSetTest() throws IOException, InterruptedException, URISyntaxException {
        //Arrange
        HttpClient httpClient  = Mockito.mock(HttpClient.class);
        String FilterResponse =  new FilePath("E://IntelijFiles//untitled//src//JSON files//file.json").init().toString();
        HttpResponse mockResponse = Mockito.mock(HttpResponse.class);

        //Act
        when(mockResponse.body()).thenReturn(FilterResponse);
        when(httpClient.send(Mockito.any(),Mockito.any())).thenReturn(mockResponse);
        APIService apiService = new APIService(httpClient,"http://localhost:8080/countSet");
        int result = apiService.countSets();

        //Assert
        assertEquals(6,result);
    }

}