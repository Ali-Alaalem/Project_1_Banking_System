public interface ITransactional {
    void deposit(double amount);
    boolean withdraw(double amount) throws Exception;
    void transfer(String targetAcc, double amount) throws Exception;
}
