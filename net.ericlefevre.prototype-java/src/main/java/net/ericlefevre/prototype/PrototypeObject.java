package net.ericlefevre.prototype;

import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrototypeObject {
	private final Map<String, PrototypeObject> members = new HashMap<String, PrototypeObject>();
	private final PrototypeObject prototype;
	PrototypeObject context;

	PrototypeObject(PrototypeObject prototype) {
		this.prototype = prototype;
	}

	protected PrototypeObject() {
		this(null);
	}

	public static PrototypeObject create() {
		return new PrototypeObject(null);
	}

	public PrototypeObject clone() {
		PrototypeObject clone = new PrototypeObject(this);
		for (Entry<String, PrototypeObject> member : members.entrySet()) {
			clone.add(member.getKey(), member.getValue());
		}
		return clone;
	}

	public PrototypeObject add(String name, PrototypeObject attributeValue) {
		members.put(name, attributeValue);
		attributeValue.context(this);

		return this;
	}

	private void context(PrototypeObject context) {
		this.context = context;
	}

	public PrototypeObject member(String name) {
		PrototypeObject member;
		if (members.containsKey(name)) {
			member = members.get(name);
		} else {
			member = prototype.member(name);
		}

		return member.execute();
	}

	public PrototypeObject execute() {
		return this;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj,
				new String[] { "context" });
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
	}
}
