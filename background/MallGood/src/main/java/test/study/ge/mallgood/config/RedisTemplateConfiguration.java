//package test.study.ge.mallgood.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.time.Duration;
//
//@Configuration
//public class RedisTemplateConfiguration {
//    @Bean
//    @Resource
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        // 设置key和value的序列化规则
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        // 设置hashKey和hashValue的序列化规则
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        // 设置支持事物
//        //redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//    /**
//     * 1分钟过期
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    @Resource
//    public RedisCacheManager cacheManager1Minute(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = instanceConfig(60L);
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//    }
//
//
//    /**
//     * 默认是1小时
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    @Primary
//    @Resource
//    public RedisCacheManager cacheManager1Hour(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = instanceConfig(3600L);
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//    }
//
//    /**
//     * 1天过期
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    @Resource
//    public RedisCacheManager cacheManager1Day(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheConfiguration config = instanceConfig(3600 * 24L);
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//    }
//
//    private RedisCacheConfiguration instanceConfig(Long ttl) {
//
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.registerModule(new JavaTimeModule());
//        // 去掉各种@JsonSerialize注解的解析
//        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
//        // 只针对非空的值进行序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        // 将类型序列化到属性json字符串中
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(ttl))
//                //.disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
//
//    }
//
//
//    /**
//     * 自定义缓存key规则
//     * @return
//     */
//    @Bean
//    public KeyGenerator springCacheCustomKeyGenerator() {
//
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                String key = o.getClass().getSimpleName() + "_" + method.getName() + "_" + StringUtils.arrayToDelimitedString(objects, "_");
//                System.out.println(key);
//                return key;
//            }
//        };
//
//    }
//}
