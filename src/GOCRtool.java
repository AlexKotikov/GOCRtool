 

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GOCRtool {
   private static final String separator = System.getProperty("line.separator");
	
	
public static void main(String[] args) {	
	System.out.println(" ==Converter==");		
			 if (args.length >0)  {
			 System.out.println("MODE = " + args[0]); }
			 else 
			    {System.out.println("In order to use the Converter specify args: \n" +
			    		"MODE1: pre  <INPUT_TEXT>  <FONT_SIZE> <FONT_NAME>  <IMAGE_NAME>  <IMAGE_FILE_FORMAT>(png\\jpg) \n" 
			    		 +
			    		"MODE2: post <OUTPUT_TXT> <FINAL_TXT>");
				return;	} 	
		try {
			
		if (args[0].equals("pre")) {
			String INPUT_TEXT = args[1];  
			 int FONT_SIZE_FOR_OCR =Integer.valueOf(args[2]); 
			 String FONT_NAME = args[3];  	 
			 String IMAGE_NAME = args[4];  
			 String IMAGE_FILE_FORMAT = args[5]; 
			
			
				new GOCRtool().		
					saveImage(textToImage(
							  prepareTXTFile(new File(INPUT_TEXT)),FONT_SIZE_FOR_OCR,FONT_NAME),
							  new File(IMAGE_NAME+"."+IMAGE_FILE_FORMAT),IMAGE_FILE_FORMAT);
	   }
		
		if (args[0].equals("post")){
			String OUTPUT_TXT = args[1]; 
			String FINAL_TXT = args[2]; 
			
				new GOCRtool().saveTxt(postEditing(new File(OUTPUT_TXT)), new File(FINAL_TXT));
	   }
		
	   } catch (ArrayIndexOutOfBoundsException e){
		   System.err.println("You have to specify all required parameters. ");
		   System.exit(1); 
	   }
	}
	
  private static String[] prepareTXTFile(File file)  
	{	
		String longestString ="";		
		StringBuilder resultString = new StringBuilder("");
		String lineFromFile, normalizedLine;	
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));

		lineFromFile = reader.readLine();
		while (lineFromFile != null)
		{		
			normalizedLine = normalization(lineFromFile);
			if (normalizedLine.length()>longestString.length()) { 			
			    longestString = normalizedLine;
			}
			resultString.append(normalizedLine + separator);
			lineFromFile = reader.readLine();
		}
        	reader.close();
        	
        	
		} catch (FileNotFoundException e) {
			System.err.println("Cannot locate " + file);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Cannot read " + file);
			System.exit(1);
		}
        	
		return new String[]  {resultString.toString(), longestString};
	}	
	

  private static  BufferedImage textToImage(String[] inputData,int fontSizeForOCR,String fontName)
	{
		    Font font = new Font(fontName, Font.PLAIN, fontSizeForOCR); 		    
		    FontMetrics fontmetric = new Canvas().getFontMetrics(font);    		    
		    //Input text to be printed on a canvas
		    String[] arrayWithInputText = inputData[0].split(separator);
		    //this string is used to calculate the width of a JPG\PNG file
		    int widthOfJPGFile = fontmetric.stringWidth(inputData[1]);
		   	int heightOfJPGFile = (arrayWithInputText.length * fontmetric.getHeight())+fontmetric.getAscent();     		   
		    BufferedImage Image = new BufferedImage( widthOfJPGFile, heightOfJPGFile ,BufferedImage.TYPE_INT_RGB);

		    Graphics2D canvas = Image.createGraphics();
		    canvas.setColor(Color.white);
		    canvas.fillRect(0, 0, widthOfJPGFile, heightOfJPGFile);
		    canvas.setColor(Color.black);	    
		    canvas.setFont(font);
		    
		    for(int i = 0 ; i < arrayWithInputText.length; i++) 
			   canvas.drawString(arrayWithInputText[i], 10, i* fontmetric.getHeight() + fontmetric.getAscent());
		   		   
		    canvas.dispose();
  
     return  Image;
	}

	private  void saveImage(BufferedImage image,File file , String imageFileFormat)  {
		try {
			ImageIO.write(image, imageFileFormat, file);
		
		} catch (IOException e) {
			System.err.println("Cannot write "+  file + "to this directory. Exit");
			System.exit(1);
		}
		System.out.println(" == " +file +" file done==");
	}
	
	
	private static  String postEditing(File file) {
		StringBuilder resultstring = new StringBuilder("");
		String lineFromFile = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file)); 

			lineFromFile = reader.readLine();	
			while (lineFromFile != null) {
				resultstring.append(finalParse(lineFromFile));
	            lineFromFile = reader.readLine();
				}
	 			reader.close();
		 
			} 
		catch (FileNotFoundException e) {
			   System.out.println("Cannot locate " + file + " Exit.");
			   System.exit(1);		   
		}
		catch (IOException e) {
				System.out.println("Cannot read data from " + file + " Exit.");
				System.exit(1);
			} 
	    return resultstring.toString(); 
	}

	private static String normalization(String a) {		
	return  new String (
		 	a.replaceAll("\"","<doublequotes>")
			.replaceAll("&&","<doubleamps>")
			.replaceFirst("^\\d+","")
			.replaceFirst("^\\.",""));	   
	}

	private static String finalParse(String a) {		
	return  new String (a.replaceAll("<doublequotes>","\"")
			       .replaceAll("<doubleamps>","&&")
			       .replaceAll("≡","=")) + separator ;		
	}
	
	
	private void saveTxt(String text, File file) {
     	FileWriter writer;
		try {
			writer = new FileWriter(file);
	      	writer.write(text);
	      	writer.close();
        
        } catch (IOException e) {
			System.err.println("Cannot  write " + file);
			System.exit(1);
		}	      
     	System.out.println(" ==" + file + " file done==");		
	}
}