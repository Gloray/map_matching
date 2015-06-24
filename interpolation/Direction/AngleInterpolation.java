package Direction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*功能：将一般的记录进行方向角插值，插值原则为以当前点为中心，Direction属性上下浮动五个数，加上或减去的数是在(0,15]内的整数，这些数不重复
 * 输入（SepTwoLines。java的输出）：
 * 1 116.74392548193214 39.93028575945051 90
 * 输出：
 * 1 116.74392548193214 39.93028575945051 90
   1 116.74392548193214 39.93028575945051 92
   1 116.74392548193214 39.93028575945051 94
   1 116.74392548193214 39.93028575945051 96
   1 116.74392548193214 39.93028575945051 98
   1 116.74392548193214 39.93028575945051 100
   1 116.74392548193214 39.93028575945051 88
   1 116.74392548193214 39.93028575945051 86
   1 116.74392548193214 39.93028575945051 84
   1 116.74392548193214 39.93028575945051 82
   1 116.74392548193214 39.93028575945051 80
 * 
 */
public class AngleInterpolation {
	
	
	public static void main(String[] args) throws IOException{
		 File out = new File("src/Direction/interpolation5.txt");
	     FileWriter writer = new FileWriter(out);
		 FileInputStream in = new FileInputStream("src/Direction/interpolation4.txt");
		 Random random = new Random();
		 String data1 = null,data2 = null;
		 String[] line1 = new String[4];
		 int temp1=0,temp2=0;
		 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		 while((data1 = reader.readLine())!=null){
			 temp1 = 0;
			 line1 = data1.split(" ");
			 
			 boolean[]  bool = new boolean[16];  
		     int randInt = 0;  
		     for(int j = 0; j < 10 ; j++) {  
		              /**得到10个不同的随机数，不包括0*/  
		        do{  
		            randInt  = random.nextInt(16);  
		            
		          }while(bool[randInt]||randInt==0);  
		        
		         bool[randInt] = true;  
//		         System.out.println(randInt);
	            }
		        //保存产生的随机数
		      int[] numbers = new int[10];
		      int k = 0;
		      bool[0] = false;
		      for(int i = 0; i < 16; i++) {
		         if(bool[i] == true){
		        	numbers[k++] = i;
		        	}
		        }
			 
			 for(int i=0;i<5;i++){
			   temp1 = numbers[i];                 //random.nextInt(16)中的16是随机数的上限,产生的随机数为0-16的整数,不包括16
			   temp2 = Integer.parseInt(line1[3])+temp1;   //在原来的角度基础上加一个随机数
			   if(temp2>360) continue;
			   data1 = data1+"\n"+line1[0]+" "+line1[1]+" "+line1[2]+" "+temp2;
			 }
			 
		    for(int i=0;i<5;i++){
	       
		       temp1 = numbers[i+5];
			   temp2 = Integer.parseInt(line1[3])-temp1;  //在原来的角度上减一个随机数
			   if(temp2<0) continue;
			   data1 = data1+"\n"+line1[0]+" "+line1[1]+" "+line1[2]+" "+temp2;
		   }
		     writer.write(data1);
		     writer.write("\n");
			   
	 }

		 writer.close();
		 in.close();
		 reader.close();
}

}
