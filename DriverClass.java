import java.io.IOException;
import java.util.Scanner;

public class DriverClass {

	public static int outputChoice;
	
	public static void main(String [] args) throws IOException{
		BehindTheScenes operator = new BehindTheScenes();
		Scanner systemIn = new Scanner(System.in);
		
		//read in code data from spreadsheet
		String [][] localArray = operator.getFinancialInfo("data/itemcodes.xls");
		System.out.println("How would you like your data to be output?");
		System.out.println("1.) In a .txt file");
		System.out.println("2.) In a .xls spreadsheet");
		outputChoice = Integer.parseInt((systemIn.nextLine()));
		System.out.println("1.) Specific year, Specific State");
		System.out.println("2.) All years, Specific State");
		String menuChoice = systemIn.nextLine();
		
		switch(menuChoice){
		case "1":
			operator.processOption1(localArray, outputChoice);
			break;
	
		
		case "2":
			operator.processOption2(localArray, outputChoice);
			break;
			
		}
	}
}
	

