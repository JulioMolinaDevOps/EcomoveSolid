package com.EcoMove.servicio;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePagoService {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public String crearSesionPago(Double monto, String descripcion, String currency) throws Exception {
        Stripe.apiKey = stripeSecretKey;
        if (currency == null || currency.isBlank()) {
            currency = "usd";
        }
        long unitAmount = Math.round(monto * 100);
        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:3000") // Cambia esto por la URL de tu front-end
            .setCancelUrl("http://localhost:3000")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(currency)
                            .setUnitAmount(unitAmount)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(descripcion)
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();
        Session session = Session.create(params);
        return session.getUrl();
    }
}
