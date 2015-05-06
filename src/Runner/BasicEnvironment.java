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

	@Override
	public void putNew(String name, Object value) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Environment where(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setOuter(Environment e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
