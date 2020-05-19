package com.onejane.machine.handler;

import com.onejane.machine.enums.OrderStatusChangeEvent;
import com.onejane.machine.enums.OrderStatusEnum;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.listener.AbstractCompositeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lxu on 2020/4/26.
 */
@Component
public class MotorOrderStatusHandler extends LifecycleObjectSupport {

    private final StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine;
    private final PersistingStateChangeInterceptor interceptor = new PersistingStateChangeInterceptor();
    private final CompositePersistStateChangeListener listeners = new CompositePersistStateChangeListener();

    @Autowired
    public MotorOrderStatusHandler(StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine) {
        Assert.notNull(stateMachine, "State machine must be set");
        this.stateMachine = stateMachine;
    }

    protected void onInit() throws Exception {
        stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.addStateMachineInterceptor(interceptor));

        Map<String, PersistStateChangeListener> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory) this.getBeanFactory(), PersistStateChangeListener.class, true, false);
        if (!matchingBeans.isEmpty()) {
            listeners.setListeners(new ArrayList(matchingBeans.values()));
        }
    }

    public boolean handleEventWithState(Message<OrderStatusChangeEvent> event, OrderStatusEnum state) {
        stateMachine.stop();
        List<StateMachineAccess<OrderStatusEnum, OrderStatusChangeEvent>> withAllRegions = stateMachine.getStateMachineAccessor().withAllRegions();
        for (StateMachineAccess<OrderStatusEnum, OrderStatusChangeEvent> a : withAllRegions) {
            a.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
        }
        stateMachine.start();
        //判断状态扭转是否成功
        boolean eventFlag = stateMachine.sendEvent(event);
        //判断持久化是否成功
        boolean persistFlag = event.getHeaders().get("persistFlag") == null || (boolean) event.getHeaders().get("persistFlag");
        return eventFlag && persistFlag;
    }

    public void addPersistStateChangeListener(PersistStateChangeListener listener) {
        listeners.register(listener);
    }

    public interface PersistStateChangeListener {
        void onPersist(State<OrderStatusEnum, OrderStatusChangeEvent> state, Message<OrderStatusChangeEvent> message, Transition<OrderStatusEnum, OrderStatusChangeEvent> transition,
                       StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine);
    }

    private class PersistingStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatusEnum, OrderStatusChangeEvent> {
        @Override
        public void preStateChange(State<OrderStatusEnum, OrderStatusChangeEvent> state, Message<OrderStatusChangeEvent> message, Transition<OrderStatusEnum, OrderStatusChangeEvent> transition,
                                   StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine) {
            listeners.onPersist(state, message, transition, stateMachine);
        }
    }

    private class CompositePersistStateChangeListener extends AbstractCompositeListener<PersistStateChangeListener> implements PersistStateChangeListener {
        public void onPersist(State<OrderStatusEnum, OrderStatusChangeEvent> state, Message<OrderStatusChangeEvent> message, Transition<OrderStatusEnum, OrderStatusChangeEvent> transition,
                              StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine) {
            for (Iterator<PersistStateChangeListener> iterator = getListeners().reverse(); iterator.hasNext(); ) {
                PersistStateChangeListener listener = iterator.next();
                listener.onPersist(state, message, transition, stateMachine);
            }
        }
    }

}
