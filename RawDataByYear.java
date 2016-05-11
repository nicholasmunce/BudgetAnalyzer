import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RawDataByYear {

	private File newFile;
	private Scanner systemIn;
	private ArrayList<String> rawArray;
	private ArrayList<String> processedArray;
	private ArrayList<String> codeMatchedArray;
	private ArrayList<String> financeArray;
	
	public ArrayList<String> getStateInfo (String fileName) throws FileNotFoundException{
		rawArray = new ArrayList<>(); 
		processedArray = new ArrayList<>();
		File file = new File(fileName);
		systemIn = new Scanner(file);
		
		//read the raw text file into an arrayList, split based on line
		while(systemIn.hasNextLine()){
			rawArray.add(systemIn.nextLine());
		}
		
		//parse the raw data into just Wisconsin data
		for(int i= 0; i<rawArray.size(); i++){
			if(rawArray.get(i).contains("50000000000000")){
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
					codeMatchedArray.add(stateList.get(i));
					System.out.println("Found: " + stateList.get(i).toString());
					
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
			int placeHolder = fieldLength;
			
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
		for(int i = 0; i<financeArray.size(); i++){
			System.out.println("Found: " + financeArray.get(i).toString());
		}
		return financeArray;
	}
}
