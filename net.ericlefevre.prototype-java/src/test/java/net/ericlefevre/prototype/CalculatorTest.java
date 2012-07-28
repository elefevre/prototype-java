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
					PrototypeObject parameter) {
				Integer leftInteger = context.get("value");
				Integer rightInteger = parameter.get("value");
				return integer.clone().add("value", leftInteger + rightInteger);
			}
		});
		PrototypeObject left = integer.clone().add("value", 42);
		PrototypeObject right = integer.clone().add("value", 3);

		assertThat(left.call("plus", right)).isEqualTo(
				integer.clone().add("value", 45));
	}
}
