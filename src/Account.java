import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public abstract class Account implements ITransactional {
    protected String accountNumber;
    protected double balance;
public int overdrafting_count=0;
public User user;
public String card_type;
    DebitCards card;


    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_type() {
        return card_type;
    }

    public Account(String accountNumber, double balance , User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user=user;

    }

    public String getType() {
        return this instanceof SavingsAccount ? "SAV" : "CHK";
    }

    public void FindAccAndEditBalance(String targetAcc,double amount){
        try {

            List<String> lines = Files.readAllLines(Path.of("users.txt"));

            for (int i = 0; i <lines.size() ; i++) {
                String line=lines.get(i);
                String[] user=line.split(",");
                if(user[7].equals(targetAcc)){
                    user[9]=String.valueOf(Double.parseDouble(user[9])+amount);
                    lines.set(i,String.join(",",user));
                }
            }
            Files.write(Path.of("users.txt"),lines);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    public static String generateAccountNumber() {
        long number = (long)(Math.random() * 1_000_000_0000L);
        return String.format("%010d", number);
    }

    @Override
    public void deposit(double amount) {

        double used = getTodayTotal("deposit");
        double limit = card.getDepositLimitPerDay();

        if (used + amount > limit) {
            System.out.println("Deposit failed: Daily limit exceeded");
            return;
        }
            balance+=amount;
            FindAccAndEditBalance(accountNumber,amount);
            System.out.println("you balance now is :" + balance);


            if(overdrafting_count ==2 && balance>0){
                overdrafting_count=0;
            }

        Writehistory(amount ,"deposit");

    }

    @Override
    public void withdraw(double amount )  {


        double usedToday = getTodayTotal("withdraw");
        double limit = card.getWithdrawLimitPerDay();

        if (usedToday + amount > limit) {
            System.out.println("Withdraw failed: Daily limit exceeded");
            return;
        }
        if(overdrafting_count ==2 ) {
            System.out.println("you cant do anything your account is locked");
            return;
        }
        if(balance < 0) {
            overdrafting_count++;
            balance -=35;
            if(amount >= 100){
                return;
            }
        }
        balance -= amount;
        FindAccAndEditBalance(accountNumber, -amount);
        System.out.println("you balance now is :" + balance);
        Writehistory(amount ,"withdraw");
    }

    @Override
    public void transfer(String targetAcc, double amount) {
        if (overdrafting_count == 2) {
            System.out.println("Your account is locked");
            return;
        }

        boolean isOwnAccount = user.getAccounts().stream().anyMatch(acc -> acc.accountNumber.equals(targetAcc));

        double usedToday;
        double limit;

        if (isOwnAccount) {
            usedToday = getTodayTotal("own-transfer");
            limit = card.getTransferLimitOwnAccountPerDay();
        } else {
            usedToday = getTodayTotal("transfer");
            limit = card.getTransferLimitPerDay();
        }

        if (usedToday + amount > limit) {
            System.out.println("Transfer failed: Daily limit exceeded.");
            return;
        }


        withdraw(amount);
        FindAccAndEditBalance(targetAcc, amount);
        String tranType = isOwnAccount ? "own-transfer" : "transfer";
        Writehistory(amount, tranType);
    }



    public void Writehistory(double amount , String tran_type){
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter("history.txt", true));
            write.write(user.getId()+","+"  Account Type:"+getType()+","+"  Account number:"+accountNumber+","+"  Transaction Type:"+tran_type+","+"  Amount:"+amount+","+"  Post Balance:"+balance+","+"  Date:"+ LocalDate.now()+","+"  Time:"+ LocalTime.now());
            write.newLine();
            write.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CreateCard(String card_type){
        try {
            boolean found=false;
            List<String> lines = Files.readAllLines(Path.of("users.txt"));

            for (int i = 0; i <lines.size() ; i++) {
                String line=lines.get(i);
                String[] users=line.split(",");
                if(Double.parseDouble(users[0])==(user.getId()) && users[6].equals(getType())){
                    if(!users[8].equals("No Card")){
                        System.out.println("You already have a card");
                        return;
                    }
                    users[8]=card_type;
                    lines.set(i,String.join(",",users));
                    System.out.println("Your card "+users[8]+"has been created");
                     card=new DebitCards(card_type);
                    setCard_type(users[8]);
                }
            }

            Files.write(Path.of("users.txt"),lines);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public double getTodayTotal(String transaction_type) {
        double total = 0;

        try {
            List<String> lines = Files.readAllLines(Path.of("history.txt"));

            for (String line : lines) {
                String[] data = line.split(",");

                String id = data[0].trim();
                String type = data[3].trim();
                String date = data[6].trim();

                if (!id.equals(String.valueOf(user.getId()))) continue;
                if (!type.equals("Transaction Type:" + transaction_type)) continue;
                if (!date.equals("Date:" + LocalDate.now())) continue;

                double amount = Double.parseDouble(data[4].replace("Amount:", "").trim());
                total += amount;
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }

        return total;
    }




}
