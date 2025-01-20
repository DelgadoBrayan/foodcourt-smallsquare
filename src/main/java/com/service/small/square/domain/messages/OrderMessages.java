package com.service.small.square.domain.messages;

public class OrderMessages {
    private OrderMessages() {}
    public static final String ORDER_ALREADY_ACTIVE = "Cliente ya tiene una orden activa";
    public static final String ORDER_NOT_FOUND = "Orden no encontrada: ";
    public static final String ORDER_CANNOT_BE_UPDATED = "Esta orden no se puede actualizar a listo.";
    public static final String ORDER_CANNOT_BE_DELIVERED = "La orden no puede ser entregada, el estado no corresponde";
    public static final String PIN_DOES_NOT_MATCH = "El pin no corresponde a la orden";
    public static final String ORDER_CANCEL_NOT_ALLOWED = "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";
    public static final String EMPLOYEE_NOT_BELONG_TO_RESTAURANT = "El empleado no pertenece a este restaurante.";
    public static final String ORDER_IN_PROCESS = "Tu orden ya a sido asignada a un cheft y esta en proceso";
    public static final String ORDER_READY_NOTIFICATION = "Tu orden está lista, te hemos enviado un mensaje SMS a tu número para que reclames tu pedido";
    public static final String ORDER_DELIVERED_NOTIFICATION = "Tu pedido ha sido entregado. Muchas gracias por tu compra :)";
    public static final String ORDER_CANCELED_NOTIFICATION = "Tu orden ha sido cancelada";
    public static final String RESTAURANT_ID_ISREQUIRED = "El ID del restaurante es requerido";
}