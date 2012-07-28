package net.ericlefevre.prototype;

import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrototypeObject {
	private final Map<String, Object> members = new HashMap<String, Object>();
	private final PrototypeObject prototype;

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
		PrototypeObject clone = instanciateClone();
		return clone;
	}

	protected PrototypeObject instanciateClone() {
		return new PrototypeObject(this);
	}

	public PrototypeObject add(String name, Object attributeValue) {
		members.put(name, attributeValue);

		return this;
	}

	public Object member(String name, PrototypeObject... parameters) {
		Object member = member(name);

		if (member instanceof PrototypeObject) {
			return ((PrototypeObject) member).execute(this, parameters);
		} else {
			return member;
		}
	}

	private Object member(String name) {
		Object member;
		if (members.containsKey(name)) {
			member = members.get(name);
		} else {
			if (prototype == null) {
				throw new RuntimeException("Could not find member '" + name
						+ "'");
			}
			member = prototype.member(name);
		}

		return member;
	}

	public PrototypeObject execute(PrototypeObject context,
			PrototypeObject... parameters) {
		return this;
	}

	public boolean isCloneOf(PrototypeObject prototype) {
		if (this.prototype == null) {
			return false;
		}
		if (this.prototype == prototype) {
			return true;
		}

		return this.prototype.isCloneOf(prototype);
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
