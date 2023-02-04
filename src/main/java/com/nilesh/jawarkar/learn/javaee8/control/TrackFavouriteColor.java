package com.nilesh.jawarkar.learn.javaee8.control;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.nilesh.jawarkar.learn.javaee8.entity.Color;
import com.nilesh.jawarkar.learn.javaee8.entity.Specification;

@Interceptor
@TrackColor(Color.ANY)
@Priority(Interceptor.Priority.APPLICATION) // This is required to enable the Interceptor
public class TrackFavouriteColor {

	@AroundInvoke
	Object arroundInvoke(final InvocationContext context) throws Exception {
		final TrackColor trackColor = context.getMethod().getAnnotation(TrackColor.class);
		final Color color = trackColor.value();
		final Object[] args = context.getParameters();
		for (final Object arg : args) {
			if (arg instanceof Specification) {
				final Specification spec = (Specification) arg;
				final Color c = spec.getColor();
				if (c != null) {
					if (color == Color.ANY || color == c) {
						System.out.println("Asked car of color - " + c.name());
					}
				}
			}
		}
		return context.proceed();
	}
}
