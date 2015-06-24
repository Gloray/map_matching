package Direction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*���ܣ���һ��ļ�¼���з���ǲ�ֵ����ֵԭ��Ϊ�Ե�ǰ��Ϊ���ģ�Direction�������¸�������������ϻ��ȥ��������(0,15]�ڵ���������Щ�����ظ�
 * ���루SepTwoLines��java���������
 * 1 116.74392548193214 39.93028575945051 90
 * �����
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
		              /**�õ�10����ͬ���������������0*/  
		        do{  
		            randInt  = random.nextInt(16);  
		            
		          }while(bool[randInt]||randInt==0);  
		        
		         bool[randInt] = true;  
//		         System.out.println(randInt);
	            }
		        //��������������
		      int[] numbers = new int[10];
		      int k = 0;
		      bool[0] = false;
		      for(int i = 0; i < 16; i++) {
		         if(bool[i] == true){
		        	numbers[k++] = i;
		        	}
		        }
			 
			 for(int i=0;i<5;i++){
			   temp1 = numbers[i];                 //random.nextInt(16)�е�16�������������,�����������Ϊ0-16������,������16
			   temp2 = Integer.parseInt(line1[3])+temp1;   //��ԭ���ĽǶȻ����ϼ�һ�������
			   if(temp2>360) continue;
			   data1 = data1+"\n"+line1[0]+" "+line1[1]+" "+line1[2]+" "+temp2;
			 }
			 
		    for(int i=0;i<5;i++){
	       
		       temp1 = numbers[i+5];
			   temp2 = Integer.parseInt(line1[3])-temp1;  //��ԭ���ĽǶ��ϼ�һ�������
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
