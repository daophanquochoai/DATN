package doctorhoai.learn.indentity_service.jwt.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveToken(String token, String username, long time) {
        redisTemplate.opsForValue().set(token, username , Duration.ofMillis(time));
    }

    @Override
    public boolean findToken(String token) {
        String returnValue = redisTemplate.opsForValue().get(token);
        if( returnValue == null ){
            return false;
        }
        return true;
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}
