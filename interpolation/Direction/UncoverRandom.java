package Direction;

import java.io.IOException;
import java.util.Random;

/*
 * ʵ�ֵ�����0��14֮�����9����ͬ���������������һ������Ϊ14��Boolean�������ڴ�Ų����������Ƿ��Ѿ����ֹ���������ֹ���whileѭ����һֱ������ȥֱ������һ����δ���ֹ������֡�
 */
public class UncoverRandom {


	public static void main(String[] args) throws IOException{
	        Random random = new Random ();  
	        boolean[]  bool = new boolean[14];  
	        int randInt = 0;  
	        for(int j = 0; j < 9 ; j++) {  
	              /**�õ�9����ͬ�������*/  
	        do{  
	            randInt  = random.nextInt(14);  
	            
	          }while(bool[randInt]||randInt==0);  
	        
	         bool[randInt] = true;  
	         System.out.println(randInt);
            }
	        //��������������
	        int[] numbers = new int[14];
	        int k = 0;
	        bool[0] = false;
	        for(int i = 0; i < 14; i++) {
	        	if(bool[i] == true){
	        		numbers[k++] = i;
	        	}
	        }
	      //��������������
	        for(int i = 0; i < 9; i++){
	        	System.out.println(numbers[i]);
	        }
	        
	        
	}
}