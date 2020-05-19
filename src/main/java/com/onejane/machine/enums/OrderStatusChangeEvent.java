package com.onejane.machine.enums;


/**
 * Created by lxu on 2020/4/24.
 */
public enum OrderStatusChangeEvent {

    // 支付|接单|确认收货|取消订单|超时|同意退款

    PAYED(1, "买家支付"),
    PAYED_CANCEL(2, "待支付时买家取消订单"),
    PAYED_CLOSE(3, "待支付时卖家关闭订单"),
    PAYED_TIMEOUT(4, "待支付超时"),
    ORDERED(5, "卖家接单"),
    ORDERED_CANCEL(6, "待接单时买家取消订单"),
    ORDERED_CLOSE(7, "待接单时卖家关闭订单"),
    ORDERED_TIMEOUT(8, "待接单超时"),
    RECEIVED(9, "买家确认收货"),
    RECEIVED_TIMEOUT(10, "确认收货超时"),
    AGREE_REFUND(11, "平台介入同意退款"),
    /*只记录日志*/
    DISAGREE_REFUND(12, "平台驳回退款"),
    CREATE_ORDER(13,"买家下单");

    private String name;
    private Integer code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @param code
     * @param name
     */
    OrderStatusChangeEvent(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    public static OrderStatusChangeEvent getByName(String value) {
        for (OrderStatusChangeEvent typeEnum : values()) {
            if (typeEnum.getName().equalsIgnoreCase(value)) {
                return typeEnum;
            }
        }
        return null;
    }
    public static OrderStatusChangeEvent getByCode(Integer code) {
        for (OrderStatusChangeEvent typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
