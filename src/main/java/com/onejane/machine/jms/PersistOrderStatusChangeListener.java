package com.onejane.machine.jms;

import com.onejane.machine.enums.OrderStatusChangeEvent;
import com.onejane.machine.enums.OrderStatusEnum;
import com.onejane.machine.handler.MotorOrderStatusHandler;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class PersistOrderStatusChangeListener implements MotorOrderStatusHandler.PersistStateChangeListener {

//    private static final Logger logger = LoggerFactory.getLogger(PersistOrderStatusChangeListener.class);
//    @Reference
//    private RpcUniteOrderService rpcUniteOrderService;

    /**
     *  当状态被持久化，调用此方法
     * @param state
     * @param message
     * @param transition
     * @param stateMachine
     */
    @Override
    public void onPersist(State<OrderStatusEnum, OrderStatusChangeEvent> state, Message<OrderStatusChangeEvent> message, Transition<OrderStatusEnum, OrderStatusChangeEvent> transition, StateMachine<OrderStatusEnum, OrderStatusChangeEvent> stateMachine) {
        try {
            int orderStatus = state.getId().getCode();
            if (message != null && message.getHeaders().containsKey("order")) {
//                RpcUniteOrderDTO order = message.getHeaders().get("order", RpcUniteOrderDTO.class);
//                if (order == null) {
//                    logger.error("OrderPersistStateChangeListener error, 订单实体为空");
//                    message.getHeaders().put("persistFlag", false);
//                    return;
//                }
//
//                //改状态
//                RpcUniteOrderRequestDTO requestDTO = new RpcUniteOrderRequestDTO();
//                requestDTO.setOrderNum(order.getOrderNum());
//                requestDTO.setOrderStatus(orderStatus);
//                String srcExtra = order.getExtra();
//                SecondHandCarOrderExtraVO secondHandCarOrderExtraVO = GfJsonUtil.parseObject(srcExtra, SecondHandCarOrderExtraVO.class);
//                if (OrderStatusEnum.CANCEL.getCode() == orderStatus) {
//                    secondHandCarOrderExtraVO.setCancelTime(new Date());
//                } else if (OrderStatusEnum.WAIT_RECEIVE.getCode() == orderStatus) {
//                    secondHandCarOrderExtraVO.setSendOutTime(new Date());
//                } else if (OrderStatusEnum.COMPLETE.getCode() == orderStatus) {
//                    secondHandCarOrderExtraVO.setFinishTime(new Date());
//                }
//                String extra = JSONObject.toJSONString(secondHandCarOrderExtraVO);
//                requestDTO.setExtra(extra);
//
//                //根据是否变更extra字段调用对应rpc
//                boolean flag;
//                if (extra.equals(srcExtra)){
//                    flag = rpcUniteOrderService.updateUsedOrderStatus(requestDTO);
//                }else {
//                    flag = rpcUniteOrderService.updateOrderStatusAndExtra(requestDTO);
//                }
//
//                if (!flag) {
//                    logger.error("OrderPersistStateChangeListener error 订单号[{}], 订单状态持久化失败", order.getOrderNum());
//                    MMonitor.recordOne("second_hand_order_persist_error_count");
//                    message.getHeaders().put("persistFlag", false);
//                }
            }
        } catch (Exception e) {
//            logger.error("OrderPersistStateChangeListener error, 异常={}", ExceptionUtils.getStackTrace(e));
            message.getHeaders().put("persistFlag", false);
        }
    }
}