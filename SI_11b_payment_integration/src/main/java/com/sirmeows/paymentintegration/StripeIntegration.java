package com.sirmeows.paymentintegration;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeIntegration {
    public void createProduct() throws StripeException {
        Product product = createStripeProduct();

        assignPrice(product);
    }

    private static void assignPrice(Product product) throws StripeException {
        PriceCreateParams params =
                PriceCreateParams
                        .builder()
                        .setProduct(product.getId())
                        .setCurrency("usd")
                        .setUnitAmount(1200L)
                        .setRecurring(
                                PriceCreateParams.Recurring
                                        .builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build())
                        .build();
        Price price = Price.create(params);
        System.out.println("Success! Here is your starter subscription price id: " + price.getId());
    }

    private static Product createStripeProduct() throws StripeException {
        ProductCreateParams productParams =
                ProductCreateParams.builder()
                        .setName("Starter Subscription")
                        .setDescription("$12/Month subscription")
                        .build();
        Product product = Product.create(productParams);
        System.out.println("Success! Here is your starter subscription product id: " + product.getId());
        return product;
    }
}
