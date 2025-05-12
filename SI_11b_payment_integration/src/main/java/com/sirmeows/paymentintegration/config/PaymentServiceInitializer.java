package com.sirmeows.paymentintegration.config;


import com.sirmeows.paymentintegration.StripeIntegration;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentServiceInitializer {
    @Value("${stripe.secret.key}")
     public String stripeApiKey;

    public final StripeIntegration stripeIntegration;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;

        try {
            stripeIntegration.createProduct();
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
