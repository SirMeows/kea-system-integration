package com.sirmeows.paymentintegration;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeIntegration {
    public Product createProduct() throws StripeException {
        Product product = createStripeProduct();
        var price = assignPrice(product);
        product.setDefaultPrice(price.getId());

        return product;
    }

    public PaymentLink createPaymentLink(Product product, long quantity) throws StripeException {
        PaymentLinkCreateParams link =
                PaymentLinkCreateParams.builder()
                        .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(product.getDefaultPrice())
                                .setQuantity(quantity).build())
                        .build();
        PaymentLink paymentLink = PaymentLink.create(link);
        System.out.println("payment url: " + paymentLink.getUrl() + " payment id: " + paymentLink.getId());

        return paymentLink;
    }

    private static Price assignPrice(Product product) throws StripeException {
        PriceCreateParams params =
                PriceCreateParams
                        .builder()
                        .setProduct(product.getId())
                        .setCurrency("usd")
                        .setUnitAmount(1200L)
                        .build();
        Price price = Price.create(params);
        System.out.println("Success! Here is your starter subscription price id: " + price.getId());
        return price;
    }

    private static Product createStripeProduct() throws StripeException {
        ProductCreateParams productParams =
                ProductCreateParams.builder()
                        .setName("fluffy bunny")
                        .setDescription("beautiful fluffy bunny")
                        .build();
        Product product = Product.create(productParams);
        System.out.println("Success! Here is your starter subscription product id: " + product.getId());
        return product;
    }
}
