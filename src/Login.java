import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Login {
    String user_name;
    String email;
    String hashed_pass;

    public boolean Checker(Optional<String> userName, String password, Optional<String> Email) {
        try {
            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] user = scanner.nextLine().split(",");

                String fileUserName = user[2];
                String fileEmail = user[3];
                String filePassword = user[5];

                boolean usernameMatch = userName.isPresent() && userName.get().equals(fileUserName);
                boolean emailMatch = Email.isPresent() && Email.get().equals(fileEmail);
                boolean passMatch = password.equals(filePassword);

                if ((usernameMatch || emailMatch) && passMatch) {
                    System.out.println("your in");
                    return true;
                }
            }
            System.out.println("The username/email or password is wrong");
            return false;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("sign in id you have an account");
        Optional<String> user_name_or_email= Optional.ofNullable(scanner.nextLine());
        String password=scanner.nextLine();
        Login login =new Login();
        login.Checker(
                user_name_or_email,
                password,
                user_name_or_email
        );
    }
}
