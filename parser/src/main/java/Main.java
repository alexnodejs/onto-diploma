
public class Main {
	public static void main(String[] args) { 
		 TextParser textparser = new TextParser();
		 String text = textparser.readFile("input.txt");
		 ///"Stanford University is located in California. It is a great university, founded in 1891.";
		 textparser.parseText(text);   
	 }
}
