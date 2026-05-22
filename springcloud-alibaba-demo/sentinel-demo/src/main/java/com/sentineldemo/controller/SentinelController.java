package com.sentineldemo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.sentineldemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Say my name
 */
@RestController
public class SentinelController {
    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    @RequestMapping("/hello")
    public String hello() {
        Entry entry = null;
        try {
            //针对资源进行限流
            SphU.entry(RESOURCE_NAME);
            //不限流执行这个
            String str = "没有被限流";
            return str;
        } catch (BlockException blockException) {
            return "被限流了";
        } finally {
            if (entry != null) {
                entry.close();
            }
        }
    }

    @PostConstruct
    private static void initFlowRules() {
        //定义规则
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RESOURCE_NAME);
        //QPS 流控规则
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);//每秒1次。访问限流
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME,
            blockHandler = "blockHandlerForGetUser",
            fallback = "fallbackForGetUser")
    public User getUser(String id) {
        int i = 1 / 0;
        return new User("GGB");
    }

    public User blockHandlerForGetUser(String id, BlockException b) {
        b.printStackTrace();
        return new User("流控!!!!!!!!!");
    }

    public User fallbackForGetUser(String id, Throwable e) {
        System.out.println("进入fallback~~~~~~~~~~~~~~");
        e.printStackTrace();
        return new User("异常处理");
    }

    /*
     * 降级
     * */
    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType =
            EntryType.IN, blockHandler = "blockHandlerForFb")
    public User degrade(String id) {
        throw new RuntimeException("异常~~~~~~~~~~~~");
    }

    public User blockHandlerForFb(String id, BlockException b) {
        return new User("熔断降级~~~~~~~~~");
    }

    @PostConstruct
    private void initDegradeRules() {
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setCount(2);
        degradeRule.setMinRequestAmount(2);
        degradeRule.setStatIntervalMs(60 * 1000);
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }
}