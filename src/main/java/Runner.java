import java.util.Scanner;
public class Runner {

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.run();
    }

    private void run() {
        RequestSender sender = new RequestSender();
        sender.getUser(getToken(), getUserId());
    }

    private String getToken() {
        System.out.println("Please insert Token");
        return scanner.nextLine();
    }

    private String getUserId() {
        System.out.println("Please insert UserID");
        return scanner.nextLine();
    }
}
