package Runner;

import java.util.HashMap;

public class BasicEnvironment implements Environment {
	protected HashMap<String, Object> values;

	public BasicEnvironment() {
		values = new HashMap<String, Object>();
	}

	public void put(String name, Object value) {
		values.put(name, value);
	}

	public Object get(String name) {
		return values.get(name);
	}
}
