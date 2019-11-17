package ohm.softa.a06.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Peter Kurfer
 * Created on 11/9/17.
 */
public final class Joke {

	@SerializedName("id")
	private int number;

	@SerializedName("joke")
	private String content;

	@SerializedName("categories")
	private List<String> rubrics;

	//private String[] rubrics;

	public Joke(){
		number = -1;
		content = "Not correctly deserialized";
		rubrics = new LinkedList<>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number){ this.number = number;}

	public String getContent() {
		return content;
	}

	public void setContent(String content){ this.content = content;}

	public List<String> getRubrics(){ return rubrics;}

	public void setRubrics(List<String> rubrics){ this.rubrics = rubrics;}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Joke)) return false;

		Joke joke1 = (Joke) o;

		return new EqualsBuilder()
				.append(getNumber(), joke1.getNumber())
				.append(getContent(), joke1.getContent())
				.append(rubrics, joke1.rubrics)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(getNumber())
				.append(getContent())
				.append(rubrics)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("number", number)
				.append("content", content)
				.append("rubrics", rubrics)
				.toString();
	}
}
