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

    public String crearSesionPago(Double monto, String descripcion) throws Exception {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("https://tuapp.com/success")
            .setCancelUrl("https://tuapp.com/cancel")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("pen")
                            .setUnitAmount((long)(monto * 100))
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
