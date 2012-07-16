package net.ericlefevre.prototype;

import static net.ericlefevre.prototype.PrototypeObject.create;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PrototypeObjectTest {
	@Test
	public void can_clone_an_object() {
		assertThat(create().clone()).isEqualTo(new PrototypeObject());
		assertThat(create().add("attributeName", create()).clone()).isEqualTo(
				create().add("attributeName", create()));
	}

	@Test
	public void cloned_objects_have_no_impact_on_their_original_prototype() {
		PrototypeObject object = create();
		PrototypeObject clone = object.clone();
		clone.add("attribute", new PrototypeObject());

		assertThat(object).isEqualTo(create());
	}

	@Test
	public void an_object_can_have_an_attribute() {
		assertThat(
				create().add("attributeName", create()).member("attributeName"))
				.isEqualTo(create());
		assertThat(
				create().add("attributeName",
						create().add("subAttribute", create())).member(
						"attributeName")).isEqualTo(
				create().add("subAttribute", create()));
		assertThat(
				create().add("firstAttribute",
						create().add("firstSubAttribute", create()))
						.add("secondAttribute",
								create().add("secondSubAttribute", create()))
						.member("firstAttribute")).isEqualTo(
				create().add("firstSubAttribute", create()));
	}

	@Test
	public void an_object_with_an_attribute_is_not_equal_to_an_empty_object() {
		assertThat(create().add("attributeName", create())).isNotEqualTo(
				create());
	}

	@Test
	public void an_object_with_an_attribute_is_not_equal_to_another_object_with_the_same_attribute() {
		assertThat(create().add("attributeName", create())).isEqualTo(
				create().add("attributeName", create()));
	}

}
