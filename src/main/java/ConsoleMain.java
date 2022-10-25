import java.util.Scanner;

public class ConsoleMain {
    public static void main(String[] args) {
        while (true){
            Scanner in = new Scanner(System.in);
            String message = in.nextLine();
            BotLogic botLogic = new BotLogic();
            System.out.println(botLogic.answer(message));
        }
    }
}
