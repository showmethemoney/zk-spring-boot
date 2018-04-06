package zk.springboot.bean;

public class TWIndustry
{
	private String name = null;
	private String code = null;

	public TWIndustry() {
	}

	public TWIndustry(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
