import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class User implements IRegistrtion {
    public int id=0;
    public String f_name;
    public String l_name;
    public String user_name;
    public String email;
    public String hashed_pass;
    public String user_type;
    public List<Account> accounts = new ArrayList<>();


    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashed_pass() {
        return hashed_pass;
    }

    public void setHashed_pass(String hashed_pass) {
        this.hashed_pass = hashed_pass;
    }

    public  boolean Login(Optional<String> userName, String password, Optional<String> Email) {
        try {
            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] user = scanner.nextLine().split(",");

                setF_name(user[1]);
                setL_name(user[2]);
                setUser_name(user[4]);
                setEmail(user[3]) ;
                setHashed_pass(user[5]);


                boolean usernameMatch = userName.isPresent() && userName.get().equals(getUser_name());
                boolean emailMatch = Email.isPresent() && Email.get().equals(getEmail());
                boolean passMatch = password.equals(getHashed_pass());

                if ((usernameMatch || emailMatch) && passMatch) {
                    loadAccounts();
                    if(user[9].equals("C")){
                        setUser_type("C");
                        System.out.println("Welcome Back "+getF_name());
                    }else if(user[9].equals("B")){
                        setUser_type("B");
                        System.out.println("Welcome Back Employee "+getF_name());
                    }
                    return true;
                }
            }
            System.out.println("The username/email or password is wrong");
            return false;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAccounts() {
        try {

            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");

                if (data[4].equals(getUser_name())) {
                    String accNum = data[7];
                    double bal = Double.parseDouble(data[8]);
                    String type = data[6];

                    Account acc;
                    if(type.equalsIgnoreCase("SAV")) acc = new SavingsAccount(accNum, bal);
                    else acc = new CheckingAccount(accNum, bal);

                    addAccount(acc);
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean Create_account(String userName, String password, String Email, String first_name, String last_name,String accType) {
        return false;
    }
}
