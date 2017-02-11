package legacy.xmi.root.elements;

public enum Visibility {
	PUBLIC ("public"),PRIVATE ("private"), PROTECTED ("protected");
	private String name;
	private Visibility(String name) {
		this.name=name;
	}
	 public String getName() {
	        return name;
	    }
}
