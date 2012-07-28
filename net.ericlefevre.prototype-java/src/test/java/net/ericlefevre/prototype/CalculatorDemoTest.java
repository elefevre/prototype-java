package net.ericlefevre.prototype;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CalculatorDemoTest {

	private PrototypeObject integer() {
		final PrototypeObject integer = PrototypeObject.create("integer");
		integer.add("plus", new PrototypeObject() {
			@Override
			public PrototypeObject execute(PrototypeObject context,
					PrototypeObject parameter) {
				Integer thisInteger = context._("value");
				Integer rightInteger = parameter._("value");
				return integer.clone().add("value", thisInteger + rightInteger);
			}
		});
		return integer;
	}

	@Test
	public void can_create_an_addition_operation_on_integer() {
		PrototypeObject integer = integer();

		PrototypeObject left = integer.clone().add("value", 42);
		PrototypeObject right = integer.clone().add("value", 3);

		assertThat(left.$("plus", right)).isEqualTo(
				integer.clone().add("value", 45));
	}

	private PrototypeObject string() {
		return PrototypeObject.create("string");
	}

	@Test
	public void can_overload_an_addition_operation_on_integer_so_that_works_with_strings() {
		final PrototypeObject string = string();
		final PrototypeObject integer = integer();
		final PrototypeObject integerThatWorksWithStrings = integer
				.clone("integerThatWorksWithStrings");
		integerThatWorksWithStrings.add("plus", new PrototypeObject() {
			@Override
			public PrototypeObject execute(PrototypeObject context,
					PrototypeObject parameter) {
				if (parameter.isCloneOf(integer)) {
					return integerThatWorksWithStrings.prototype.$(context,
							"plus", parameter);
				}
				Integer thisInteger = context._("value");
				String rightString = parameter._("value");
				return string.clone().add("value", thisInteger + rightString);
			}
		});

		PrototypeObject left = integerThatWorksWithStrings.clone().add("value",
				42);
		PrototypeObject right = integer.clone().add("value", 3);

		assertThat(left.$("plus", right)).isEqualTo(
				integer.clone().add("value", 45));
		assertThat(left.$("plus", string.clone().add("value", "3"))).isEqualTo(
				string.clone().add("value", "423"));
	}
}
