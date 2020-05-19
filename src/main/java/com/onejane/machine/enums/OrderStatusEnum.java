package com.onejane.machine.enums;

/**
 * Created by lxu on 2020/4/24.
 */
public enum OrderStatusEnum {

    WAIT_PAYMENT(0, "待支付"),
    WAIT_ORDERED(101, "待接单"),
    WAIT_RECEIVE(102, "待收货"),
    COMPLETE(4, "交易完成"),
    CANCEL(-4, "订单取消");

    private String name;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     *
     * @param code
     * @param name
     */
    OrderStatusEnum(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public static OrderStatusEnum getByName(String value) {
        for (OrderStatusEnum typeEnum : values()) {
            if (typeEnum.getName().equalsIgnoreCase(value)) {
                return typeEnum;
            }
        }
        return null;
    }
    public static OrderStatusEnum getByCode(int code) {
        for (OrderStatusEnum typeEnum : values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum;
            }
        }
        return null;
    }

}
