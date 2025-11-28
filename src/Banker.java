import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class Banker extends User{


    @Override
    public boolean Create_account(String userName, String password, String Email, String first_name, String last_name) {
        return false;
    }

    @Override
    public boolean Login(Optional<String> userName, String password, Optional<String> Email) {
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
}
