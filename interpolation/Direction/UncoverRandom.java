package Direction;

import java.io.IOException;
import java.util.Random;

/*
 * 实现的是在0到14之间产生9个不同的随机数，创建了一个长度为14的Boolean数组用于存放产生的数字是否已经出现过，如果出现过，while循环会一直继续下去直到产生一个从未出现过的数字。
 */
public class UncoverRandom {


	public static void main(String[] args) throws IOException{
	        Random random = new Random ();  
	        boolean[]  bool = new boolean[14];  
	        int randInt = 0;  
	        for(int j = 0; j < 9 ; j++) {  
	              /**得到9个不同的随机数*/  
	        do{  
	            randInt  = random.nextInt(14);  
	            
	          }while(bool[randInt]||randInt==0);  
	        
	         bool[randInt] = true;  
	         System.out.println(randInt);
            }
	        //保存产生的随机数
	        int[] numbers = new int[14];
	        int k = 0;
	        bool[0] = false;
	        for(int i = 0; i < 14; i++) {
	        	if(bool[i] == true){
	        		numbers[k++] = i;
	        	}
	        }
	      //输出产生的随机数
	        for(int i = 0; i < 9; i++){
	        	System.out.println(numbers[i]);
	        }
	        
	        
	}
}