package addDirection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/*���ܣ�Ϊ�˸�ԭʼ·���еĹؼ����¼���direction����
 * �������ݵĸ�ʽ��
 * 1 116.74392548193214 39.93028575945051 T
 * 2 116.74404744423452 39.93043513645234 F
 * 3 116.74405445154181 39.91907360545235 D
 * ��һ����������·��ǩ���ڶ��������Ǿ�γ�ȣ���������oneway�ֶΣ�T(to)�Ǵ����ָ���յ㣬F(from)�Ǵ��յ�ָ����㣬D��ʾ˫���
 * ������ݸ�ʽ��
 * 1 116.74392548193214 39.93028575945051 T 89.75917489962964
 * 2 116.74404744423452 39.93043513645234 F 269.7604258399535
 * 3 116.74405445154181 39.91907360545235 D 90.13130045572312 270.13130045572314
 * 
 * 
 */
public class AppendDirection {
	public static void main(String[] args) throws IOException{
		 Helper help = new Helper();
		 double s,angle,preAngle = 0,supplement = -1,preSupplement = -1;
		 File out = new File("src/addDirection/interpolation3.txt");
	     FileWriter writer = new FileWriter(out);
		 FileInputStream in = new FileInputStream("src/addDirection/interpolation2.txt");
		 String data1 = null,data2 = null,pre = null;
		 String[] line1 = new String[5];
		 String[] line2 = new String[5];
		 String[] lineT = new String[5];
		 int count = 0;
		 boolean flag = false,isFirst = false;  //flagΪtrue��ʾ����һ����ı�ǩ��ͬ
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
				 if(line1[0].equals(line2[0])){

					 angle = help.GetCurDirection(Double.parseDouble(line1[2]),Double.parseDouble(line1[1]),
								Double.parseDouble(line2[2]), Double.parseDouble(line2[1]));
					 
					 if(line1[3].equals("D")){
						 if(angle>=0&&angle<=180) supplement = angle + 180;
						 if(angle>180&&angle<360) supplement = angle - 180;
						 data1 = data1 + " " + Double.toString(angle) + " " + Double.toString(supplement)+"\n";

					 }
					 else{
						 data1 = data1 + " " + Double.toString(angle)+"\n";
					 }
					 preAngle = angle;
					 preSupplement = supplement;
					
				 }
				 else{
					 
					 if(line1[3].equals("D")) 
						 data1 = data1 + " " + Double.toString(preAngle) + " " + Double.toString(preSupplement)+"\n";
					 else
						 data1 = data1 + " " + Double.toString(preAngle)+"\n";

				 }

				 writer.write(data1);
				 pre = data2; 
						
			 }
			 
		 }
		 writer.close();
		 in.close();
		 reader.close();
		 
	}
}

		
