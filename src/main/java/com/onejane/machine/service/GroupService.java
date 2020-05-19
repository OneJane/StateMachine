package com.onejane.machine.service;

import com.onejane.machine.domain.Group;
import com.onejane.machine.enums.OrderStatusChangeEvent;

public interface GroupService {

    boolean handleAction(Group group, OrderStatusChangeEvent event);
}
