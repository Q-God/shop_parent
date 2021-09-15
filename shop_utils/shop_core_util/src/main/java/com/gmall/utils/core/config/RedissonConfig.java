package com.gmall.utils.core.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * redisson配置信息
 */
@Configuration
@ConfigurationProperties("spring.redis")
public class RedissonConfig {

    // redis 服务器的ip 地址
    private String host;

    private String addresses;

    private String password;

    // redis 端口号
    private String port;

    private int timeout = 3000;
    private int connectionPoolSize = 64;
    private int connectionMinimumIdleSize=10;
    private int pingConnectionInterval = 60000;
    private static String ADDRESS_PREFIX = "redis://";

    public RedissonConfig() {
    }

    /**
     * 自动装配
     *
     */
    @Bean
    RedissonClient redissonSingle() {
        // 声明一个配置类
        Config config = new Config();
        // 判断缓存的Ip地址是否存在
        if(StringUtils.isEmpty(host)){
            throw new RuntimeException("host is empty");
        }
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(ADDRESS_PREFIX + this.host + ":" + port)
                .setTimeout(this.timeout)
                .setPingConnectionInterval(pingConnectionInterval)
                .setConnectionPoolSize(this.connectionPoolSize)
                .setConnectionMinimumIdleSize(this.connectionMinimumIdleSize);
        // 判断密码
        if(!StringUtils.isEmpty(this.password)) {
            serverConfig.setPassword(this.password);
        }
        return Redisson.create(config);
    }

    public String getHost() {
        return this.host;
    }

    public String getAddresses() {
        return this.addresses;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPort() {
        return this.port;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public int getConnectionPoolSize() {
        return this.connectionPoolSize;
    }

    public int getConnectionMinimumIdleSize() {
        return this.connectionMinimumIdleSize;
    }

    public int getPingConnectionInterval() {
        return this.pingConnectionInterval;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public void setPingConnectionInterval(int pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RedissonConfig)) return false;
        final RedissonConfig other = (RedissonConfig) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$host = this.getHost();
        final Object other$host = other.getHost();
        if (this$host == null ? other$host != null : !this$host.equals(other$host)) return false;
        final Object this$addresses = this.getAddresses();
        final Object other$addresses = other.getAddresses();
        if (this$addresses == null ? other$addresses != null : !this$addresses.equals(other$addresses)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$port = this.getPort();
        final Object other$port = other.getPort();
        if (this$port == null ? other$port != null : !this$port.equals(other$port)) return false;
        if (this.getTimeout() != other.getTimeout()) return false;
        if (this.getConnectionPoolSize() != other.getConnectionPoolSize()) return false;
        if (this.getConnectionMinimumIdleSize() != other.getConnectionMinimumIdleSize()) return false;
        if (this.getPingConnectionInterval() != other.getPingConnectionInterval()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RedissonConfig;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $host = this.getHost();
        result = result * PRIME + ($host == null ? 43 : $host.hashCode());
        final Object $addresses = this.getAddresses();
        result = result * PRIME + ($addresses == null ? 43 : $addresses.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $port = this.getPort();
        result = result * PRIME + ($port == null ? 43 : $port.hashCode());
        result = result * PRIME + this.getTimeout();
        result = result * PRIME + this.getConnectionPoolSize();
        result = result * PRIME + this.getConnectionMinimumIdleSize();
        result = result * PRIME + this.getPingConnectionInterval();
        return result;
    }

    public String toString() {
        return "RedissonConfig(host=" + this.getHost() + ", addresses=" + this.getAddresses() + ", password=" + this.getPassword() + ", port=" + this.getPort() + ", timeout=" + this.getTimeout() + ", connectionPoolSize=" + this.getConnectionPoolSize() + ", connectionMinimumIdleSize=" + this.getConnectionMinimumIdleSize() + ", pingConnectionInterval=" + this.getPingConnectionInterval() + ")";
    }
}
