package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Payment.PaymentRequestDTO;
import com.stripe.exception.StripeException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/V1/payments")
public class PaymentController {

    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();  // Charger le fichier .env
        stripeApiKey = dotenv.get("STRIPE_API_KEY");  // Récupérer la clé API Stripe
        Stripe.apiKey = stripeApiKey;  // Utiliser la clé API pour Stripe
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            Dotenv dotenv = Dotenv.load();
            String stripeApiKey = dotenv.get("STRIPE_API_KEY");
            Stripe.apiKey = stripeApiKey;
            // Conversion du montant en centimes si c'est un Double
            long amountInCents = (long) (paymentRequestDTO.getAmount());
            // Créer les paramètres pour la session de paiement
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/success")  // URL de succès (en développement, ici localhost)
                    .setCancelUrl("http://localhost:4200/cancel")    // URL d'annulation
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)  // Quantité du produit (généralement 1 pour un paiement unique)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("eur")  // Devise
                                                    .setUnitAmount(amountInCents)  // Montant en centimes
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(paymentRequestDTO.getProductName())  // Le nom du stage
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            // Créer la session Stripe
            Session session = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("url", session.getUrl());
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}


