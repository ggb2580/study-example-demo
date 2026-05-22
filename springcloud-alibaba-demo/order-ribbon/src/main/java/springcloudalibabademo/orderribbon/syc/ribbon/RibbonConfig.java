package springcloudalibabademo.orderribbon.syc.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Say my name
 */
@Configuration
@Profile("disabled")
public class RibbonConfig {
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
