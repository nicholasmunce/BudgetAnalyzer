import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class NewExcel 
{

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
            // Loop over first 10 column and lines
       //     System.out.println(sheet.getColumns() +  " " +sheet.getRows());
            for (int j = 0; j <sheet.getColumns(); j++) 
            {
                for (int i = 0; i < sheet.getRows(); i++) 
                {
                    Cell cell = sheet.getCell(j, i);
                    data[j][i] = cell.getContents();
                  //  System.out.println(cell.getContents());
                }
            }

         /*   for (int j = 0; j < data.length; j++) 
            {
                for (int i = 0; i <data[j].length; i++) 
                {

                    System.out.println(data[j][i]);
                }
            } */

        } 
        catch (BiffException e) 
        {
            e.printStackTrace();
        }
    return data;
    }


}
