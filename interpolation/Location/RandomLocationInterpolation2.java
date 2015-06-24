package Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


/*
 * 功能：在均匀分布的基础上，在连续两条记录之间进行随机插值，随机增量插值，两个点之间插4个点
 * 输入：9 116.70739778389635 39.922348759147795 T
 * 9 116.70764811342322 39.92235875914831 T
 * 9 116.70789844295007 39.922368759148824 T
 * 输出：
 * 9 116.70767807983368 39.922354357687354 T
 * 9 116.70748007459387 39.92235718455844 T
 * 
 * 备注：最后用这个，等距插值以后就用这个随机插值
 */
public class RandomLocationInterpolation2 {

	public static void main(String[] args) throws IOException{
		 File out = new File("src/Location/interpolation2.txt");
	     FileWriter writer = new FileWriter(out);
		 FileInputStream in = new FileInputStream("src/Location/interpolation1.txt");
		 Random random = new Random();
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
					 writer.write(data1+"\n");
					 
					 double r = 0, s = 0, t = 0, v = 0;
					 r = random.nextDouble();
					 curlat = r*(latitude2-latitude1)+latitude1;
					 curlon = (longitude2-longitude1)*(curlat-latitude1)/(latitude2-latitude1)+longitude1+r*(longitude2-longitude1);
					 mid = line1[0]+" "+Double.toString(curlon)+" "+Double.toString(curlat)+" "+line1[3]+"\n";
				     writer.write(mid);
				     System.out.print(mid);
				    
				     s = random.nextDouble();
					 curlat = (s+0.5)*(latitude2-latitude1)+latitude1;  //1.5, 
					 curlon = (longitude2-longitude1)*(curlat-latitude1)/(latitude2-latitude1)+longitude1-s*0.25*(longitude2-longitude1);//1.25
					 mid = line1[0]+" "+Double.toString(curlon)+" "+Double.toString(curlat)+" "+line1[3]+"\n";
				     writer.write(mid);
				     System.out.print(mid);
				     
				     t = random.nextDouble();
					 curlat = (t-1)*(latitude2-latitude1)+latitude1;
					 curlon = (longitude2-longitude1)*(curlat-latitude1)/(latitude2-latitude1)+longitude1+t*0.5*(longitude2-longitude1);//1.5
					 mid = line1[0]+" "+Double.toString(curlon)+" "+Double.toString(curlat)+" "+line1[3]+"\n";
				     writer.write(mid);
				     System.out.print(mid);
				     
				    
				     v = random.nextDouble();
					 curlat = (v+1)*(latitude2-latitude1)+latitude1;
					 curlon = (longitude2-longitude1)*(curlat-latitude1)/(latitude2-latitude1)+longitude1-v*(longitude2-longitude1);
					 mid = line1[0]+" "+Double.toString(curlon)+" "+Double.toString(curlat)+" "+line1[3]+"\n";
				     writer.write(mid);
				     System.out.print(mid);
				     
					 }
				 else{
					 writer.write(data1+"\n");
				 }
					 
				 }
				 pre = data2;
			 }
		
		 writer.close();
		 in.close();
		 reader.close();
	}			
}
