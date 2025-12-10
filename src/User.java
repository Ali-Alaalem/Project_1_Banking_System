import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class User implements IRegistrtion {
    public int id=0;
    public String f_name;
    public String l_name;
    public String user_name;
    public String email;
    public String hashed_pass;
    public String user_type;
    public List<Account> accounts = new ArrayList<>();
    public int feild_login=0;


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


                    String dbUserName = user[4];
                    String dbEmail = user[3];
                    String dbPassword = user[5];

                    boolean usernameMatch = userName.isPresent() && userName.get().equals(dbUserName);
                    boolean emailMatch = Email.isPresent() && Email.get().equals(dbEmail);
                    boolean passMatch = password.equals(dbPassword);

                    if ((usernameMatch || emailMatch) && passMatch) {
                        setF_name(user[1]);
                        setL_name(user[2]);
                        setUser_name(user[4]);
                        setEmail(user[3]);
                        setHashed_pass(user[5]);
                        setId(Integer.parseInt(user[0]));

                        loadAccounts();
                        if (user[10].equals("C")) {
                            setUser_type("C");
                            System.out.println("Welcome Back " + getF_name());
                        } else if (user[10].equals("B")) {
                            setUser_type("B");
                            System.out.println("Welcome Back Employee " + getF_name());
                        }
                        return true;
                    }else  if ((usernameMatch || emailMatch) && !passMatch){
                        if (feild_login == 3){
                            System.out.println();
                            System.out.println("Your account is looked try again after 1 min");

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    feild_login=0;
                                }
                            },60000 );
                            return false;
                        }
                        feild_login++;
                    }
                }

                System.out.println();
                System.out.println("Your User name / Email is wrong please try again");
                System.out.print("Press ENTER to return to log in page...");
                try { System.in.read(); } catch (Exception e) { }
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
                    double bal = Double.parseDouble(data[9]);
                    String type = data[6];

                    Account acc;
                    if(type.equalsIgnoreCase("SAV")) acc = new SavingsAccount(accNum, bal,this);
                    else acc = new CheckingAccount(accNum, bal ,this);
                   acc.card=new DebitCards(data[8]);

                    addAccount(acc);
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DisplayHistory(String acc_type){
        try {

            File file = new File("history.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");

                if (Integer.parseInt(data[0])==getId() && data[1].equals("  Account Type:"+acc_type)) {
                    String date=data[6];
                    String type=data[1];
                    String post_balance=data[5];
                    System.out.println(date+" "+type+" "+post_balance);
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DetailedAccountStatment(String acc_type){
        String balance="";
        try {

            File file = new File("history.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");

                if (Integer.parseInt(data[0])==getId()&& data[1].equals("  Account Type:"+acc_type)) {
                    balance=data[5];
                    String history=Arrays.stream(data).filter(n->!n.equals(data[0])).collect(Collectors.joining(","));
                    System.out.println(history);

                }
            }
            if(!balance.equals("")){
                System.out.println("Your current balance is :" + balance);
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void FilterTransaction(String choice){
        try {

            File file = new File("history.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");

                if (Integer.parseInt(data[0]) == getId()) {

                    if (choice.equals("1") && data[6].equals("  Date:" + LocalDate.now())) {
                        System.out.println(line);

                    } else if (choice.equals("2") && data[6].equals("  Date:" + LocalDate.now().minusDays(1))) {
                        System.out.println(line);

                    } else if (choice.equals("3") &&
                            !LocalDate.parse(data[6].replace("  Date:", "")).isBefore(LocalDate.now().minusDays(7)) &&
                            !LocalDate.parse(data[6].replace("  Date:", "")).isAfter(LocalDate.now())) {
                        System.out.println(line);

                    } else if (choice.equals("4") &&
                            !LocalDate.parse(data[6].replace("  Date:", "")).isBefore(LocalDate.now().minusDays(30)) &&
                            !LocalDate.parse(data[6].replace("  Date:", "")).isAfter(LocalDate.now())) {
                        System.out.println(line);
                    }
                }}
                scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public boolean Create_account(String userName, String password, String Email, String first_name, String last_name,String accType,Optional<String> Card_CHK_type,Optional<String> Card_SAV_type) {
        return false;
    }
}
