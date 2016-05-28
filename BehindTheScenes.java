import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;

public class BehindTheScenes {

	private Scanner systemInFile;
	private ArrayList<String> rawArray;
	private ArrayList<String> processedArray;
	private ArrayList<String> codeMatchedArray;
	private ArrayList<String> financeArray;
	private String[][] excelArray;
	private ArrayList<Integer> moneyList = new ArrayList<>();
	private ArrayList<Integer> yearList = new ArrayList<>();
	
	public void processOption1(String [][] localArray, int choice) throws IOException{
		Scanner systemIn = new Scanner(System.in);
		System.out.println("Enter a state: ");
		String state = systemIn.nextLine();
		System.out.println("Enter a year to read: ");
		String input = systemIn.nextLine();
		String inputProcessed = input.substring(2, 4);
		System.out.println("Enter a filename to save to: ");
		String output = systemIn.nextLine();
		String outputTXT = "data/" + output + ".txt";
		String outputXLS = "data/" + output + ".xls";
		PrintWriter fileOut = new PrintWriter(outputTXT);
		System.out.println("Enter the issue that you are interested in:");
		String search = systemIn.nextLine();

		//take user info and match it to raw data file information
		int totalMoney = 0;
		ArrayList <String> finalArray = getFinancialInfo(matchToCodes(getCodes(search, localArray), getStateInfo(inputProcessed, state)));
		for(int i = 0; i <finalArray.size(); i++){
			totalMoney += Double.parseDouble(finalArray.get(i));
		}

		totalMoney = totalMoney * 1000;
		if ( choice == 1){
			fileOut.println(("In the year " + input + " the state of " + state + " spent $" + totalMoney + " on " + search));
			}
		else if (choice == 2){
			Integer money = new Integer(totalMoney);
			moneyList.add(totalMoney);
			Integer year = new Integer(input);
			yearList.add(year);
		}
		fileOut.close();
		if(choice == 2){
			write(outputXLS, moneyList, yearList);
		}
	}
	
	public void processOption2(String [][] localArray, int choice) throws IOException{
		Scanner systemIn = new Scanner(System.in);
		System.out.println("Enter a state: ");
		String state = systemIn.nextLine();
		System.out.println("Enter a filename to save to: ");
		String output = systemIn.nextLine();
		String outputXLS = "data/" + output + ".xls";
		String outputTXT = "data/" + output + ".txt";
		PrintWriter fileOut = new PrintWriter(outputTXT);
		System.out.println("Enter the issue that you are interested in:");
		String search = systemIn.nextLine();
		for(int i = 1996; i<2013; i++){
			if(i != 1999){String inputProcessed = Integer.toString(i).substring(2, 4);
			int totalMoney = 0;
			ArrayList <String> finalArray = getFinancialInfo(matchToCodes(getCodes(search, localArray),getStateInfo(inputProcessed, state)));
			for(int j = 0; j <finalArray.size(); j++){
				totalMoney += Double.parseDouble(finalArray.get(j));
			}

			double newMoney1 = totalMoney * 10;
			double newMoney2 = newMoney1 * 10;
			double newMoney3 = newMoney2 * 10;
			if (choice == 1){
				fileOut.println("In the year " + i + " " + state + " spent $" + newMoney3 + " on " + search);
			}
			else if (choice == 2){
				moneyList.add(totalMoney);
				yearList.add(i);
			}
		}
			
	}
	fileOut.close();
	if (choice == 2){
		write(outputXLS, moneyList, yearList);
	}
}	
	public ArrayList<String> getStateInfo (String fileName, String state) throws IOException{
		rawArray = new ArrayList<>(); 
		processedArray = new ArrayList<>();
		if(fileName.substring(0,1).equals("9")){
			try{
				URL u = new URL("http://www2.census.gov/govs/state/" + fileName + "data35.txt");
			systemInFile = new Scanner (u.openStream());
			} catch (IOException e){
				System.out.println("That year " + fileName +" could not be found in the Census database");
				System.exit(0);
			}
		}
		
		else{
			try{
			URL u = new URL("http://www2.census.gov/govs/state/" + fileName + "state35.txt");
		systemInFile = new Scanner (u.openStream());
		} catch (IOException e){
			System.out.println("That year " + fileName +" could not be found in the Census database");
			System.exit(0);
		}
	}	
		
		//read the raw text file into an arrayList, split based on line
		while(systemInFile.hasNextLine()){
			rawArray.add(systemInFile.nextLine());
		}
		
		//parse the raw data into just Wisconsin data
		setInputFile("data/government_ids.xls");
		String [][] stateArray = read();
		String stateCode = "";
		for(int i = 0; i<50; i++){
			if(stateArray[0][i].trim().equalsIgnoreCase(state)){
				stateCode = stateArray[1][i];
				break;
			}
		}
		for(int i= 0; i<rawArray.size(); i++){
			if(rawArray.get(i).contains(stateCode)){
				processedArray.add(rawArray.get(i));
			}
		}
		
	return processedArray;
	}
	
	public ArrayList<String> matchToCodes(ArrayList<String> codeList, ArrayList<String> stateList){
		codeMatchedArray = new ArrayList<>();
		for(int i = 0; i<stateList.size(); i++){
			for(int j = 0; j<codeList.size(); j++){
				if(stateList.get(i).contains(codeList.get(j))){
					if(stateList.get(i).contains("T")){
					}
					else{
						codeMatchedArray.add(stateList.get(i));
					}

				}

			}
		}

		return codeMatchedArray;
	}
	
	public ArrayList<String> getFinancialInfo(ArrayList<String> codeMatchedArray){
		financeArray = new ArrayList<>();
		for(int i =0; i<codeMatchedArray.size(); i++){
			int fieldLength = codeMatchedArray.get(i).length();
			int endingIndex = fieldLength - 6;
			int counter = 0;

			//start at the end of the field and read until whitespace
			for(int j = fieldLength-1; j > 0; j--){
				String charMatch = "" + codeMatchedArray.get(i).charAt(j);
				if(charMatch.equals(" ")){
					break;
				}
				else{
					counter++;
				}
			}
			int startingIndex = fieldLength - counter;
			financeArray.add(codeMatchedArray.get(i).substring(startingIndex, endingIndex));
		}

		return financeArray;
	}
	
	public String[][] getFinancialInfo(String inputString) throws IOException{
		String [][] returnArray;
		setInputFile(inputString);	
		returnArray = read();
		return returnArray;
	}

	public String[][] matchStateInfo (String inputString) throws IOException{
		String [][] returnArray;
		setInputFile(inputString);	
		returnArray = read();
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
	
	private  String inputFile;
    String[][] data = null;
    public  void setInputFile(String inputFileInput) 
    {
        inputFile = inputFileInput;
    }

    public String[][] read() throws IOException  
    {
    	File inputWorkbook = new File(inputFile);
    	Workbook w;

    	try 
    	{
    		w = Workbook.getWorkbook(inputWorkbook);
    		// Get the first sheet


    		Sheet sheet = w.getSheet(0);
    		data = new String[sheet.getColumns()][sheet.getRows()];

    		for (int j = 0; j <sheet.getColumns(); j++) 
    		{
    			for (int i = 0; i < sheet.getRows(); i++) 
    			{
    				Cell cell = sheet.getCell(j, i);
    				data[j][i] = cell.getContents();
    			}
    		}
    	} 
    	catch (BiffException e) 
    	{
    		e.printStackTrace();
    	}
    	return data;
    }
    
    public void write(String file, ArrayList<Integer> yearList, ArrayList<Integer> moneyList){
    	try {
    		File exlFile = new File(file);
    		WritableWorkbook writableWorkbook = Workbook
    				.createWorkbook(exlFile);

    		WritableSheet writableSheet = writableWorkbook.createSheet(
    				"Sheet1", 0);

    		//Create Cells with contents of different data types.
    		//Also specify the Cell coordinates in the constructor
    		Label year = new Label(0, 0, "Year");
    		Label money = new Label(1,0, "Dollars (in thousands)");

    		//Add the created Cells to the sheet
    		writableSheet.addCell(year);
    		writableSheet.addCell(money);
    		
    		//write the data
    		for(int i = 0; i <yearList.size(); i++){
    			Number newNumber = new Number(0, i, yearList.get(i).intValue());
    			writableSheet.addCell(newNumber);
    		}
    		for(int i = 0; i <moneyList.size(); i++){
    			Number newNumber = new Number(1, i, moneyList.get(i).intValue());
    			writableSheet.addCell(newNumber);
    		}

    		//Write and close the workbook
    		writableWorkbook.write();
    		writableWorkbook.close();

    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (RowsExceededException e) {
    		e.printStackTrace();
    	} catch (WriteException e) {
    		e.printStackTrace();
    	}
    }
}


