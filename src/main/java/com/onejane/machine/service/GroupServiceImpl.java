package com.onejane.machine.service;

import com.onejane.machine.domain.Group;
import com.onejane.machine.domain.GroupRepository;
import com.onejane.machine.enums.OrderStatusChangeEvent;
import com.onejane.machine.enums.OrderStatusEnum;
import com.onejane.machine.handler.MotorOrderStatusHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    MotorOrderStatusHandler handler;
    @Autowired
    GroupRepository repository;

    public boolean handleAction(Group group, OrderStatusChangeEvent event) {
        return handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("order", group).setHeader("persistFlag", true).build(), OrderStatusEnum.getByCode(group.getStatus()));
    }
}
