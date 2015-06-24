package Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class UniformLocationInterpolation1 {

	public static void main(String[] args) throws IOException{
		 File out = new File("src/Location/interpolation1.txt");
	     FileWriter writer = new FileWriter(out);
		 FileInputStream in = new FileInputStream("src/Location/oneway_final.txt");
		 Helper help = new Helper();
		 String data1 = null, data2 = null, pre = null, mid = null, cur = null;
		 String[] line1 = new String[6];
		 String[] line2 = new String[6];
		 String[] temp = new String[6];
		 int count = 0, left = 0, right = 0;
		 double deltadistance = 0, longitude1, latitude1, longitude2=0, latitude2=0;
	     double midlon, midlat, middirection, predirection, supplement=-1, presupplement;
	     double curlon=0, curlat=0, curdir, deltalon, deltalat;
	     int equal = 0;  //定距等分点
		 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		 
		 
		 while((data1 = reader.readLine())!=null){
			 count++;
			 if(count == 1) data2 = reader.readLine();
			 else
			 {
				 data2 = data1;
				 data1 = pre;
			 }
			 line1 = data1.split(" ");
			 if(data2 != null){
				 line2 = data2.split(" ");
				 if(line1[0].equals(line2[0])){             //两行的标签是一样的
					 longitude1 = Double.parseDouble(line1[1]);
					 latitude1 = Double.parseDouble(line1[2]);
					 longitude2 = Double.parseDouble(line2[1]);
					 latitude2 = Double.parseDouble(line2[2]);
					 deltadistance = help.GetDistance(latitude1, longitude1, latitude2, longitude2);  //

					 if(deltadistance > 20){      //距离大于20米
						 
						equal = (int) Math.round(deltadistance/20);
						 
					 }
					 if(equal>0){
					 double step = (double)equal;
					 deltalon = (longitude2-longitude1)/step;
					 deltalat = (latitude2-latitude1)/step;	
			
					 for(int i=0; i<equal; i++){
						curlat = latitude1 + deltalat*i;
						curlon = longitude1 +deltalon*i;
						mid = line1[0]+" "+Double.toString(curlon)+" "+Double.toString(curlat)+" "+line1[3]+"\n";
					     writer.write(mid);
					     System.out.print(mid);
					     }
					 
					 }
					 else{
						 writer.write(data1+"\n");
						 System.out.print(data1+"\n");
					 }
					 equal = 0;
				 }
				 else{
					 
				     writer.write(data1+"\n");
				     System.out.print(data1+"\n");
				     equal = 0;
					 
				 }
					 
				 }
				 pre = data2;
			 }
		
		 writer.close();
		 in.close();
		 reader.close();
	}			
}
