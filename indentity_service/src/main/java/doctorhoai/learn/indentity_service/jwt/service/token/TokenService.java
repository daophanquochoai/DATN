package doctorhoai.learn.indentity_service.jwt.service.token;


public interface TokenService {
    void saveToken(String token, String username, long time);
    boolean findToken(String token);
    void deleteToken(String token);
}
