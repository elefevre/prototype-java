package net.ericlefevre.prototype;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CalculatorTest {
	@Test
	public void can_create_an_addition_operation_on_integer() {
		final PrototypeObject integer = PrototypeObject.create();
		integer.add("plus", new PrototypeObject() {
			@Override
			public PrototypeObject execute(PrototypeObject context,
					PrototypeObject... parameters) {
				Integer leftInteger = (Integer) context.member("value");
				Integer rightInteger = (Integer) parameters[0].member("value");
				return integer.clone().add("value", leftInteger + rightInteger);
			}
		});
		PrototypeObject left = integer.clone().add("value", 42);
		PrototypeObject right = integer.clone().add("value", 3);

		assertThat(
				((PrototypeObject) left.member("plus", right)).member("value"))
				.isEqualTo(45);
	}
}
