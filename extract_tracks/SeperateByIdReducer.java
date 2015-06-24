package cn.edu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class SeperateByIdReducer extends Reducer<Text,Text,Text,Text> {

	@Override
	protected void reduce(Text key, Iterable<Text> values,Context contex) throws IOException, InterruptedException {
		List<String> list=new ArrayList<String>();
		List<Location> locationList=new ArrayList<Location>();
	
		double maxlon = 116.75;
		double minlon = 116.7;
		double maxlat = 39.933333333333;
		double minlat = 39.9;
		double x,y;
		for(Text value:values){
			list.add(value.toString());
		}	
		Collections.sort(list,new Comparator<String>(){
			@Override
			public int compare(String text1, String text2) {
				String[] t1 = text1.split(",");
				String[] t2 = text2.split(",");
				if (t1.length > 0 && t2.length > 0) {
					return t1[0].compareTo(t2[0]);
				}
				if (t1.length > 0) {
					return 1;
				} else if (t2.length > 0) {
					return -1;
				}
				return 0;
			}			
		});
		
		for (String  value: list) {

			locationList.add(new Location(value));			
		}
		
		StringBuffer str=new StringBuffer();
		int count = 0;
		double prex = 0,prey = 0;
		long ptime = 0;
		for (Location position : locationList) {
			
			x = position.getLon();
			y = position.getLat();
			if(x>minlon&x<maxlon&y>minlat&y<maxlat){
				if (count ==0 || (position.getTime()-ptime)>=0) {
					
					if(prex!=x||prey!=y) 
						str.append(x+","+y+","+position.getTime()+","+position.getDirection()+";");
					    prex = position.getLon();
					    prey = position.getLat();
					    ptime = position.getTime();
				}	
//				str.append(x+","+y+","+position.getTime()+","+position.getDirection()+";");
//			    prex = position.getLon();
//			    prey = position.getLat();
//			    ptime = position.getTime();
			
			}
			
			count++;
			
		}
		contex.write(key,new Text(str.toString()));
	}
}
