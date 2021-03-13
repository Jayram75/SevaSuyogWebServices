package in.sevasuyog.model.enums;

public enum AttributeName {
	VERIFIED("V"), AVAILABLE("AV"), ACTIVE("AC");

	private String guid;
	private AttributeName(String value) {
		this.guid = value;
	}
	public String getGUID() {
		return guid;
	}
}
