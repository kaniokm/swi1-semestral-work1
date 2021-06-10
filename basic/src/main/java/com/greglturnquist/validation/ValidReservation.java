package com.greglturnquist.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationValidator.class)
public @interface ValidReservation {
    String message() default  "Person id number is incorrect, or selected time and date is already picked, or invalid nationality";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
