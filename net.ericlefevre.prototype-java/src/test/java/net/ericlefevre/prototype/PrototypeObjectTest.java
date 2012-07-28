package net.ericlefevre.prototype;

import static net.ericlefevre.prototype.PrototypeObject.create;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PrototypeObjectTest {
	private static final PrototypeObject VALUE = create().add("attributeName",
			create());

	@Test
	public void cloned_objects_have_no_impact_on_their_original_prototype() {
		PrototypeObject object = create();
		PrototypeObject clone = object.clone();
		clone.add("attribute", create());

		assertThat(object).isEqualTo(create());
	}

	@Test
	public void cloned_objects_receive_the_new_members_added_on_their_prototype() {
		PrototypeObject object = create();
		PrototypeObject clone = object.clone();
		object.add("attribute", create());

		assertThat(clone.member("attribute")).isEqualTo(create());
	}

	@Test
	public void cloned_objects_receive_the_new_values_for_the_existing_members_on_their_prototype() {
		PrototypeObject object = create();
		object.add("attribute", create());
		PrototypeObject clone = object.clone();
		object.add("attribute", 1);

		assertThat(clone.member("attribute")).isEqualTo(1);
	}

	@Test
	public void an_object_can_have_an_attribute() {
		assertThat(VALUE.member("attributeName")).isEqualTo(create());
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
		assertThat(VALUE).isNotEqualTo(create());
	}

	@Test
	public void an_object_with_an_attribute_is_not_equal_to_another_object_with_the_same_attribute() {
		assertThat(VALUE).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_return_a_value() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject... parameters) {
						return VALUE;
					}
				});
		assertThat(object.member("methodName")).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_take_parameters() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject... parameters) {
						return parameters[0];
					}
				});
		assertThat(object.member("methodName", VALUE)).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_refer_to_members_of_their_context() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject... parameters) {
						return (PrototypeObject) context.member("value");
					}
				});
		object.add("value", VALUE);
		assertThat(object.member("methodName")).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_have_no_parameters() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context) {
						return VALUE;
					}
				});
		assertThat(object.member("methodName")).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_have_a_single_parameter() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject parameter) {
						return parameter;
					}
				});
		assertThat(object.member("methodName", VALUE)).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_have_two_parameters() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject parameter1,
							PrototypeObject parameter2) {
						return parameter2;
					}
				});
		assertThat(object.member("methodName", null, VALUE)).isEqualTo(VALUE);
	}

	@Test
	public void methods_can_refer_to_members_of_clones_of_their_context() {
		PrototypeObject object = create().add("methodName",
				new PrototypeObject() {
					@Override
					public PrototypeObject execute(PrototypeObject context,
							PrototypeObject... parameters) {
						return (PrototypeObject) context.member("value");
					}
				});
		PrototypeObject clone = object.clone().add("value", VALUE);
		System.out.println(clone.member("value"));
		assertThat(clone.member("methodName")).isEqualTo(VALUE);
	}

	@Test
	public void can_check_if_a_prototype_is_a_clone_of_another() {
		assertThat(create().isCloneOf(create())).isFalse();
		PrototypeObject prototype = create();
		assertThat(prototype.clone().isCloneOf(prototype)).isTrue();
		assertThat(prototype.clone().clone().isCloneOf(prototype)).isTrue();
	}
}
