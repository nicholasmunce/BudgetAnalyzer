import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverClass {

	public static void main(String [] args) throws IOException{
		CensusDataReader inputYear = new CensusDataReader();
		RawDataByYear getData = new RawDataByYear();
		Scanner systemIn = new Scanner(System.in);
		
		//read in code data from spreadsheet
		String [][] localArray = inputYear.getExcelInfo("data/itemcodes.xls");
		
		//get user data
		System.out.println("Enter a year to read: ");
		String input = systemIn.nextLine();
		String inputProcessed = "data/" +input + ".txt";
		System.out.println("Enter a filename to save to: ");
		String output = "data/" + systemIn.nextLine() + ".txt";
		System.out.println("Enter the issue that you are interested in:");
		String search = systemIn.nextLine();
		
		//take user info and match it to raw data file information
		double totalMoney = 0;
		ArrayList <String> finalArray = getData.getFinancialInfo(getData.matchToCodes(inputYear.getCodes(search, localArray), getData.getStateInfo(inputProcessed)));
		for(int i = 0; i <finalArray.size(); i++){
			totalMoney += Double.parseDouble(finalArray.get(i));
		}
		
		totalMoney = totalMoney * 1000;
		System.out.println("In the year " + input + " the state of Wisconsin spent $" + totalMoney + " on " + search);
	}
	
}
