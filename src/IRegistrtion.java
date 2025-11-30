import java.util.Optional;

public interface IRegistrtion {
    public boolean Login(Optional<String> userName, String password, Optional<String> Email);
    public boolean Create_account(String userName, String password, String Email ,String first_name ,String last_name,String accType);
}
