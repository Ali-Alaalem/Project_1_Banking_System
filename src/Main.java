import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Customer customer = new Customer();
        Banker banker;


        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to ACME Bank tha safest bank in the world");
        System.out.println("*************************************************");
        System.out.println();
        System.out.println("Do you have an account or you want to create one?  (y/n)");
        String account=scanner.nextLine();
        if(account.equals("y")){
            System.out.print("User name / Email :");
            String user_name_or_email=scanner.next();
            System.out.print("Password :");
            String password=scanner.next();


            customer.Login(
                    Optional.of(user_name_or_email),
                    String.valueOf(password.hashCode()),
                    Optional.of(user_name_or_email)
            );

        }else if(account.equals("n")){
            System.out.print("Do you want to create one?  (y/n) :");
            String create_acc=scanner.next();
            if(create_acc.equals("n")){
                System.exit(0);
            }else{
                System.out.print("First name :");
                String f_name=scanner.next();
                System.out.print("Last name :");
                String l_name=scanner.next();
                System.out.print("Email :");
                String Email=scanner.next();
                System.out.print("User name :");
                String user_name=scanner.next();
                System.out.print("Password :");
                String password=scanner.next();
                customer.Create_account(user_name,password,Email,f_name,l_name);
                System.out.println("Your account will be reviewed and created within 24 hours");
            }
        }



    }
}
