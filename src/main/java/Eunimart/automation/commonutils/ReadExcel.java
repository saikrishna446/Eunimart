package Eunimart.automation.commonutils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
import java.io.FileInputStream;
public class ReadExcel 
{
		public static FileInputStream fis = null;
	    public static XSSFWorkbook workbook = null;
	    public static XSSFSheet sheet = null;
	    public static XSSFRow row = null;
	    public static XSSFCell cell = null;
	 
	    public static void exCelRead() throws Exception
	    {
	       
	    	/*
	    	 * Change the path to the executing machine local path before test
	    	 */
	   fis=new FileInputStream( System.getProperty("user.dir") + System.getProperty("file.separator") +"Excelsheets"+ System.getProperty("file.separator") +"testdata.xlsx" );
	        workbook = new XSSFWorkbook(fis);
//	        fis.close();
	        
	       
	    }
	 
	    public static String getCellData(int row, int col, String sheetName)
	    {
	    	try {
				exCelRead();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            String tEstData = workbook.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
				return tEstData;
	           
	    }
 
}
