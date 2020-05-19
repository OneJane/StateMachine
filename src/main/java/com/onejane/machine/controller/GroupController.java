package com.onejane.machine.controller;

import com.onejane.machine.domain.Group;
import com.onejane.machine.enums.OrderStatusChangeEvent;
import com.onejane.machine.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    @RequestMapping("/timeout")
    public boolean handle(Group group) {
        return groupService.handleAction(group,  OrderStatusChangeEvent.PAYED_TIMEOUT);
    }

}