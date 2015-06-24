package cn.edu;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.sun.corba.se.spi.orb.StringPair;

public class SeperateByIdMapper extends Mapper<LongWritable,Text,Text,Text> {
	
	public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException {
		
		String line = value.toString();		
		try {
			if(line.trim().length()>0 && !line.contains("txt")){
			String regex = ",";
			Pattern p = Pattern.compile(regex);
			String[] item = p.split(line);
			if(item.length == 9){
				String state = item[2];
			if (state.compareTo("0") == 0 || state.compareTo("1") == 0){
				try {
					 Pattern p0 = Pattern.compile(item[0]);
					 Pattern p3 = Pattern.compile(item[3]);
					 Pattern p4 = Pattern.compile(item[4]);
					 Pattern p5 = Pattern.compile(item[5]);
					 if(p0.matches("[0-9]{6}", item[0])&&p0.matches("[0-9]{14}", item[3])
						&&p0.matches("[0-9]{3}.[0-9]{7}", item[4])&&p0.matches("[0-9]{2}.[0-9]{7}", item[5]))
							 
					 {
					 Long.parseLong(item[3]);
					 Double.parseDouble(item[4]);
					 Double.parseDouble(item[5]);
					 Short.parseShort(item[7]);
					 context.write(new Text(item[0]),new Text(item[3]+","+item[4]+","+item[5]+","+item[7]));
					 }
				} catch (Exception e) {}			
			
			   }
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
