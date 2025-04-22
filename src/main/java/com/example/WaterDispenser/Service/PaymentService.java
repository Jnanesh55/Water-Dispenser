package com.example.WaterDispenser.Service;

import com.example.WaterDispenser.DTO.PaymentRequest;
import com.example.WaterDispenser.DTO.PaymentResponse;
import com.example.WaterDispenser.Model.Transaction;
import com.example.WaterDispenser.Repository.TransactionRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${payment.amount}")
    private int amount;

    @Value("${payment.currency}")
    private String currency;

    @Value("${payment.description}")
    private String description;

    private final RazorpayClient razorpayClient;
    private TransactionRepository transactionRepository;  // Removed static

    @Autowired
    public PaymentService(@Value("${razorpay.key_id}") String keyId,
                          @Value("${razorpay.key_secret}") String secret,
                          TransactionRepository transactionRepository)
            throws RazorpayException {
        this.razorpayClient = new RazorpayClient(keyId, secret);
        this.transactionRepository = transactionRepository;  // Use instance variable
    }

    public PaymentResponse verifyAndProcessPayment(String secret, PaymentRequest request) {
        try {
            boolean isValid = verifyPaymentSignature(secret, request);  // Call instance method

            if (isValid) {
                // Create a new transaction and set its attributes
                Transaction transaction = new Transaction();
                transaction.setPaymentId(request.getPaymentId());
                transaction.setOrderId(request.getOrderId());
                transaction.setAmount(amount);
                transaction.setCurrency(currency);
                transaction.setStatus("success");
                transaction.setCreatedAt(LocalDateTime.now());
                transaction.setMotorActivated(false);

                // Save the transaction in the database
                transactionRepository.save(transaction);

                // Return a success response
                return new PaymentResponse(true, "Payment verified successfully", transaction.getId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return a failure response if verification fails
        return new PaymentResponse(false, "Payment verification failed", null);
    }

    // Changed to instance method
    private boolean verifyPaymentSignature(String secret, PaymentRequest request) {
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", request.getOrderId());
        options.put("razorpay_payment_id", request.getPaymentId());
        options.put("razorpay_signature", request.getSignature());

        try {
            // Use the Utils class to verify the payment signature
            Utils.verifyPaymentSignature(options, secret);
            return true; // Signature is valid
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false; // Signature verification failed
        }
    }

    public String getFixedQRPaymentDetails() {
        // Create a JSON object with payment details
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", currency);
        options.put("description", description);

        return options.toString(); // Return the JSON string
    }
}