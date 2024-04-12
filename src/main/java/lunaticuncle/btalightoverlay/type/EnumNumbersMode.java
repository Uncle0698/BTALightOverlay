package lunaticuncle.btalightoverlay.type;

public enum EnumNumbersMode implements IEnumConfigType{
	NONE("","none"),
	BLOCK("","block"),
	SKY("","sky"),
	BOTH("","value");

	private final String name;
	private final String value;

	EnumNumbersMode(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
