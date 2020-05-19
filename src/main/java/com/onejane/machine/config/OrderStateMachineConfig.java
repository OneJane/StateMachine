package com.onejane.machine.config;

import com.onejane.machine.enums.OrderStatusChangeEvent;
import com.onejane.machine.enums.OrderStatusEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * Created by lxu on 2020/4/24.
 */
@EnableStateMachine
@Configuration
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusChangeEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusChangeEvent> states) throws Exception {
        states.withStates()
                // 定义初始状态
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                // 定义所有状态集合
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderStatusChangeEvent> config) throws Exception {
        config.withConfiguration().machineId("orderStatusMachine");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                //买家支付
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.WAIT_ORDERED)
                .event(OrderStatusChangeEvent.PAYED)
                .action(payed())

                //待支付时买家取消订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.PAYED_CANCEL)
                .action(payedCancel())

                //待支付时卖家关闭订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.PAYED_CLOSE)
                .action(payedClose())

                //待付款超时
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.PAYED_TIMEOUT)
                .action(payedTimeout())

                //卖家接单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_ORDERED).target(OrderStatusEnum.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.ORDERED)
                .action(ordered())

                //待接单时买家取消订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_ORDERED).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.ORDERED_CANCEL)
                .action(orderedCancel())

                //待接单时卖家关闭订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_ORDERED).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.ORDERED_CLOSE)
                .action(orderedClose())

                //待接单超时
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_ORDERED).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.ORDERED_TIMEOUT)
                .action(orderedTimeout())

                //买家确认收货
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_RECEIVE).target(OrderStatusEnum.COMPLETE)
                .event(OrderStatusChangeEvent.RECEIVED)
                .action(received())

                //确认收货超时
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_RECEIVE).target(OrderStatusEnum.COMPLETE)
                .event(OrderStatusChangeEvent.RECEIVED_TIMEOUT)
                .action(receivedTimeout())

                //平台介入同意退款
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_RECEIVE).target(OrderStatusEnum.CANCEL)
                .event(OrderStatusChangeEvent.AGREE_REFUND)
                .action(agreeRefund());

    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> payed() {
        return context -> {
            System.out.println("payed");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> payedCancel() {
        return context -> {
            System.out.println("payedCancel");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> payedClose() {
        return context -> {
            System.out.println("payedClose");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> payedTimeout() {
        return context -> {
            System.out.println("payedTimeout");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> ordered() {
        return context -> {
            System.out.println("ordered");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> orderedCancel() {
        return context -> {
            System.out.println("orderedCancel");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> orderedClose() {
        return context -> {
            System.out.println("orderedClose");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> orderedTimeout() {
        return context -> {
            System.out.println("orderedTimeout");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> received() {
        return context -> {
            System.out.println("received");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> receivedTimeout() {
        return context -> {
            System.out.println("receivedTimeout");
        };
    }

    @Bean
    public Action<OrderStatusEnum, OrderStatusChangeEvent> agreeRefund() {
        return context -> {
            System.out.println("agreeRefund");
        };
    }

}
