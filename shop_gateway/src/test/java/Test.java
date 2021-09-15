import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @version v1.0
 * @ClassName Test
 * @Description TODO
 * @Author Q
 */
public class Test {

    public static void main(String[] args) {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setPort(6379);
        rsc.setHostName("10.10.10.10");
        rsc.setDatabase(0);

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory fac = new LettuceConnectionFactory(rsc);

        fac.afterPropertiesSet();
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        String s = redisTemplate.opsForValue().get("user:login:f13ed76c-531e-4a32-8dad-7534ba403fb1");
        System.out.println("s = " + s);
    }

}
