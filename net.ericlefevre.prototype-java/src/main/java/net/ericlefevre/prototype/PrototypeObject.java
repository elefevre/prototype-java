package net.ericlefevre.prototype;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrototypeObject {
	private final Map<String, PrototypeObject> members = new HashMap<String, PrototypeObject>();

	public static PrototypeObject root() {
		return new PrototypeObject();
	}

	public PrototypeObject clone() {
		return new PrototypeObject();
	}

	public PrototypeObject add(String name, PrototypeObject attributeValue) {
		members.put(name, attributeValue);

		return this;
	}

	public PrototypeObject member(String name) {
		return members.get(name);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
