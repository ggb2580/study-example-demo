package springcloudalibabademo.orderribbon;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springcloudalibabademo.orderribbon.syc.ribbon.RibbonConfig;

@SpringBootApplication
//@RibbonClients(
//        value = {
//                @RibbonClient(name = "stock-server",configuration = RibbonConfig.class)
//        }
//)
public class OrderRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderRibbonApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public ApplicationRunner checkRibbonRule(SpringClientFactory clientFactory) {
        return args -> {
            // 获取 ZoneAwareLoadBalancer 实例
            ZoneAwareLoadBalancer<?> loadBalancer =
                    (ZoneAwareLoadBalancer<?>) clientFactory.getLoadBalancer("stock-server");
            // 打印当前负载均衡规则
            System.out.println("实际使用的负载均衡规则: " + loadBalancer.getRule().getClass().getName());
        };
    }

}
