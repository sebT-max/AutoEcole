package com.example.AutoEcole.api.controller;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Exemple d'un seul produit
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount(paymentRequest.getAmount()) // en centimes
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(paymentRequest.getProductName())
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType("card") // ou "klarna"
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://ton-site.com/success")
                    .setCancelUrl("https://ton-site.com/cancel")
                    .addLineItem(lineItem)
                    .build();

            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("url", session.getUrl());

            return ResponseEntity.ok(responseData);

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}

