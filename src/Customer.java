import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Customer extends User{

    @Override
    public boolean Create_account(String userName, String password, String Email ,String first_name ,String last_name) {
        try {

            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] user = scanner.nextLine().split(",");

                String fileUserName = user[4];
                String fileEmail = user[3];

                boolean usernameMatch =  userName.equals(fileUserName);
                boolean emailMatch =   Email.equals(fileEmail);


                if (usernameMatch) {
                    System.out.println("This User name is already exist");
                    return false;
                }else if(emailMatch){
                    System.out.println("This Email is already exist");
                    return false;
                }
            }
            scanner.close();
            BufferedWriter write =new BufferedWriter(new FileWriter("requests.txt",true));
            this.id++;
            String info=this.id+","+first_name+","+last_name+","+Email+","+userName+","+password.hashCode()+","+"C";
            write.write(info);
            write.newLine();
            write.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    System.out.println("Welcome to the System");
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
