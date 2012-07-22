package net.ericlefevre.prototype;

public class Method extends PrototypeObject {

	public Method(Method prototype) {
		super(prototype);
	}

	public Method() {
		super(create());
	}

	public PrototypeObject execute() {
		return context;
	}
}
