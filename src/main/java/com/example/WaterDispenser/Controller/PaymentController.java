package com.example.WaterDispenser.Controller;

import com.example.WaterDispenser.DTO.PaymentRequest;
import com.example.WaterDispenser.DTO.PaymentResponse;
import com.example.WaterDispenser.Service.Esp32CommunicationService;
import com.example.WaterDispenser.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private Esp32CommunicationService communicationService;

    @PostMapping("/verify")
    public PaymentResponse verifyPayment(@Value("${razorpay.key_secret}") String secret, @RequestBody PaymentRequest request){
        PaymentResponse response = PaymentService.verifyAndProcessPayment(secret,request);

        if(response.isSuccess()){
            //send siganl to esp
            communicationService.sendActivationSignal("/topic/motor","activate");
        }
        return response;
    }
    @GetMapping("/qr")

    public String getFixedQRDetails() {

        return paymentService.getFixedQRPaymentDetails();
    }
}
