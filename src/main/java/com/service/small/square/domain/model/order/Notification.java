package com.service.small.square.domain.model.order;

public class Notification {
    private Long orderId;
    private String customerPhone;
    private String securityPin;


    public Notification(Long orderId, String customerPhone, String securityPin) {
        this.orderId = orderId;
        this.customerPhone = customerPhone;
        this.securityPin = securityPin;
       
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getSecurityPin() {
        return securityPin;
    }
    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }
   
}
