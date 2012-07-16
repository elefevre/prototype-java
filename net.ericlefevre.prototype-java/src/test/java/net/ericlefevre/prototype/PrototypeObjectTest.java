package net.ericlefevre.prototype;

import static net.ericlefevre.prototype.PrototypeObject.root;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PrototypeObjectTest {
	@Test
	public void can_clone_an_empty_object() {
		assertThat(root().clone()).isEqualTo(new PrototypeObject());
	}

	@Test
	public void an_object_can_have_an_attribute() {
		assertThat(root().add("attributeName", root()).member("attributeName"))
				.isEqualTo(root());
		assertThat(
				root().add("attributeName", root().add("subAttribute", root()))
						.member("attributeName")).isEqualTo(
				root().add("subAttribute", root()));
		assertThat(
				root().add("firstAttribute",
						root().add("firstSubAttribute", root()))
						.add("secondAttribute",
								root().add("secondSubAttribute", root()))
						.member("firstAttribute")).isEqualTo(
				root().add("firstSubAttribute", root()));
	}

	@Test
	public void an_object_with_an_attribute_is_not_equal_to_an_empty_object() {
		assertThat(root().add("attributeName", root())).isNotEqualTo(root());
	}

	@Test
	public void an_object_with_an_attribute_is_not_equal_to_another_object_with_the_same_attribute() {
		assertThat(root().add("attributeName", root())).isEqualTo(
				root().add("attributeName", root()));
	}
}
