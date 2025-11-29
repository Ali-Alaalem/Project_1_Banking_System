import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Customer customer = new Customer();
        Banker banker =new Banker();
        User user  =new User();





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

            user.Login(
                    Optional.of(user_name_or_email),
                    String.valueOf(password.hashCode()),
                    Optional.of(user_name_or_email)
            );
if (user.getUser_type().equals("B")){
    System.out.println("1-Check requests \n 2-Deposit \n 3-Withdraw");
    System.out.println("What do you want to do : ");
    String option=scanner.next();
    if(option.equals("1")){
        banker.Requests_num();
        System.out.println("Do you want to proceed  (y/n)");
        String option2=scanner.next();
if(option2.equals("y")){
    banker.Display_Requests();
    banker.CheckRequests();
}else if(option2.equals("n")){
    System.exit(0);
}
    }
}

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
