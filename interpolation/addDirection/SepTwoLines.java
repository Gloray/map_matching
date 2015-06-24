package addDirection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SepTwoLines {
	public static void main(String[] args) throws IOException{
		 
		 File out = new File("src/addDirection/interpolation4.txt");
	     FileWriter writer = new FileWriter(out);
		 FileInputStream in = new FileInputStream("src/addDirection/interpolation3.txt");
		 String data1 = null,data2 = null;
		 String[] line1 = new String[6];
		 long temp1,temp2;
		 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		 while((data1 = reader.readLine())!=null){
			
			 line1 = data1.split(" ");
			 temp1 = Math.round(Double.parseDouble(line1[4]));
			 if(temp1 == 360) temp1 = 0;         //Gps方向角在[0,360)变化
			 if(line1[3].equals("D")){
				 temp2 = Math.round(Double.parseDouble(line1[5]));
				 if(temp2 == 360) temp2 = 0;                           //Gps方向角在[0,360)变化
				 data1 = line1[0]+" "+line1[1]+" "+line1[2]+" "+temp1+"\n";
			     data2 = line1[0]+" "+line1[1]+" "+line1[2]+" "+temp2+"\n";
			     data1 = data1+data2;
//			     System.out.println(data1+data2);
			     writer.write(data1);
//			     writer.write(data2);
			 }else{
				 data1 = line1[0]+" "+line1[1]+" "+line1[2]+" "+temp1+"\n";
				 writer.write(data1);
				
		 		
			 }
			 
		 }
		 writer.close();
		 in.close();
		 reader.close();
		 
	}
}


