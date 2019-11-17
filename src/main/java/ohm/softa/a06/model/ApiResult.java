package ohm.softa.a06.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ApiResult<T> {

	private String type;

	private T value;

	public ApiResult() {
		type = "error";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isSuccessfull() {
		return type != null && type.equals("success");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof ApiResult)) return false;

		ApiResult<?> that = (ApiResult<?>) o;

		return new EqualsBuilder()
			.append(getType(), that.getType())
			.append(getValue(), that.getValue())
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(getType())
			.append(getValue())
			.toHashCode();
	}
}
