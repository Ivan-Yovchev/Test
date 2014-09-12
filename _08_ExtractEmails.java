import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class _08_ExtractEmails {

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		String text = input.nextLine();
		
		Pattern email = Pattern.compile("\\w+([.-_]+\\w+)*@\\w+([.]\\w+)+");
		Matcher matcher = email .matcher(text);
		
		while (matcher.find()){
			
			System.out.println(matcher.group());
			
		}
	}

}
