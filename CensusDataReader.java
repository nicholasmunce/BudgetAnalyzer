import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CensusDataReader {

	private String inputFile;
	private String outputFile;
	private String input;
	private String output;
	private String search;
	private static PrintWriter fileOut;
	private ArrayList indexList;
	private String[][] excelArray;
	

	


public String[][] getExcelInfo(String inputString) throws IOException{
	String [][] returnArray;
	this.input = inputString;
	NewExcel newClass = new NewExcel();
	newClass.setInputFile(input);	
	returnArray = newClass.read();
	return returnArray;
}

public ArrayList<String> getCodes(String search, String[][] localArray) throws FileNotFoundException{
	this.excelArray = localArray;
	int counter = 0;
	ArrayList<String> foundCodes = new ArrayList<>();
	
	while(counter <493){
		if(excelArray[1][counter].contains(search)){
			foundCodes.add(excelArray[0][counter]);
		}
		counter++;
	}
	
	return foundCodes;
	}
}



	
	
	
	
	
	
	
	
	
	
	/*for(int i = 0; i< 2; i++){
		for(int j = 0; j <493; j++){
			System.out.println(array[i][j]);
		}
	}*/
	
	
	
	
	
	

	

