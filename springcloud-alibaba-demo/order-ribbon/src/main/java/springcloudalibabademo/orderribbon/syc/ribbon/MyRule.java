package springcloudalibabademo.orderribbon.syc.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyRule extends AbstractLoadBalancerRule {


    @Override
    public Server choose(Object o) {

        ILoadBalancer loadBalancer = this.getLoadBalancer();
        List<Server> reachableServers = loadBalancer.getReachableServers();
        //获取随机的线程
        int random = ThreadLocalRandom.current().nextInt(reachableServers.size());
        Server server = reachableServers.get(random);
        return server;

    }


    //这个方法 里面什么都不屑
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

}
