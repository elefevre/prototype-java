package net.ericlefevre.prototype;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CalculatorDemoTest {
	@Test
	public void can_create_an_addition_operation_on_integer() {
		final PrototypeObject integer = PrototypeObject.create();
		integer.add("plus", new PrototypeObject() {
			@Override
			public PrototypeObject execute(PrototypeObject context,
					PrototypeObject parameter) {
				Integer leftInteger = context._("value");
				Integer rightInteger = parameter._("value");
				return integer.clone().add("value", leftInteger + rightInteger);
			}
		});
		PrototypeObject left = integer.clone().add("value", 42);
		PrototypeObject right = integer.clone().add("value", 3);

		assertThat(left.$("plus", right)).isEqualTo(
				integer.clone().add("value", 45));
	}
}
