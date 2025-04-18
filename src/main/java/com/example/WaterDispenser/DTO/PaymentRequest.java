package com.example.WaterDispenser.DTO;

import lombok.Data;

@Data
public class PaymentRequest {

        private String paymentId;
        private String orderId;
        private String signature;
        //private String machineId;
//        private int amount;
//        private String currency;

        public String getPaymentId() {
                return paymentId;
        }

        public void setPaymentId(String paymentId) {
                this.paymentId = paymentId;
        }

        public String getOrderId() {
                return orderId;
        }

        public void setOrderId(String orderId) {
                this.orderId = orderId;
        }

        public String getSignature() {
                return signature;
        }

        public void setSignature(String signature) {
                this.signature = signature;
        }

//        public String getMachineId() {
//                return machineId;
//        }
//
//        public void setMachineId(String machineId) {
//                this.machineId = machineId;
//        }

        public PaymentRequest(String paymentId, String orderId, String signature, String machineId) {
                this.paymentId = paymentId;
                this.orderId = orderId;
                this.signature = signature;
                //this.machineId = machineId;
        }

        @Override
        public String toString() {
                return "PaymentRequest{" +
                        "paymentId='" + paymentId + '\'' +
                        ", orderId='" + orderId + '\'' +
                        ", signature='" + signature + '\'' +
                        '}';
        }
}

