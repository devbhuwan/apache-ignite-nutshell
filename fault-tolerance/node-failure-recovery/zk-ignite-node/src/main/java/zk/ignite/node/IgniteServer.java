package zk.ignite.node;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/31
 */
@Configuration
public class IgniteServer {

    @Value("${ignite-configuration}")
    private String igniteCfg;

    @Bean
    public Ignite igniteInstance() {
        return Ignition.start(igniteCfg);
    }

}
