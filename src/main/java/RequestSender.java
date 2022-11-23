import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RequestSender {
    private List<String> usersFirstNameList, usersLastNamelist, usersLocalLanguage;

    public RequestSender() {
    }

    public void getUser(String token, String userId) {
        String finalToken = "Basic " + token;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(UriBuiler(userId)))
                .header("Authorization", finalToken)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().toString();
            usersFirstNameList = JsonPath.read(body, "$.data[*].firstName");
            usersLastNamelist = JsonPath.read(body, "$.data[*].lastName");
            usersLocalLanguage = JsonPath.read(body, "$.data[*].config.locale");
            printUsersInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void printUsersInformation() {
        for (int i = 0; i < usersFirstNameList.size(); i++) {
            int j = i + 1;
            System.out.println("===============================");
            System.out.println("User no : " + j);
            System.out.println("First name : " + usersFirstNameList.get(i));
            System.out.println("Last name : " + usersLastNamelist.get(i));
            System.out.println("Locale : " + usersLocalLanguage.get(i).substring(0, 2));
            System.out.println("===============================");
        }
    }

    private String UriBuiler(String fragment) {
        String protocol = "https";
        String host = "backend.staffbase.com";
        String path = "/api/users";
        String query = "id";
        URI uri = null;
        try {
            uri = new URI(protocol, null, host, -1, path, query, fragment);
            System.out.println(uri);
            return uri.toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
