package src.main.java;

/**
 * Created by nava on 9/14/15.
 */
public class Main {
    public static void main(String [] args) {
        GithubService service = new GithubService("username", "token");
        String fileContent = "file content";
        service.createFile("github-rest", "file.txt", "testing", fileContent.getBytes(), "master");
        System.out.println();
    }
}
