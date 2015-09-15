package src.main.java;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by nava on 9/14/15.
 */
public class GithubService {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String BASE_URL = "https://api.github.com";
    private HttpRequestFactory requestFactory;
    private String user;
    private String token;

    public GithubService(String userName, String token) {
        this.requestFactory = HTTP_TRANSPORT.createRequestFactory();
        this.user = userName;
        this.token = token;
    }

    public class Params {
        public String path;
        public String content;
        public String message;
        public String branch;
    }

    public String createFile(String repo, String path, String comment, byte[] content, String branch) {
        String baseUrl = "/repos/" + user + "/" + repo + "/contents/" + path;
        GenericUrl url = new GenericUrl(BASE_URL + baseUrl);
        Params params = new Params();
        params.path = path;
        params.message = comment;
        params.content = Base64.getEncoder().encodeToString(content);
        params.branch = branch;
        Gson gson = new Gson();
        String json = gson.toJson(params);
        HttpHeaders headers = new HttpHeaders();
        headers.setAuthorization("token " + token);

        try {
            HttpRequest request = requestFactory.buildPutRequest(url, new ByteArrayContent(UrlEncodedParser.MEDIA_TYPE, json.getBytes()));
            request.setHeaders(headers);
            HttpResponse response = request.execute();
            System.out.println(response.parseAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAuthorization() {
        return "token " + this.token;
    }


}
