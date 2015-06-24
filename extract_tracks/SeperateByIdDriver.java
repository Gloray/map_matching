package cn.edu;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class SeperateByIdDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();   
//		conf.setStrings("mapred.job.tracker", "local");	
		
	    Job job = new Job(conf,"seperateById");
	    job.setJarByClass(SeperateByIdDriver.class);
	    job.setMapperClass(SeperateByIdMapper.class);
	    job.setReducerClass(SeperateByIdReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));	   
	    FileOutputFormat.setOutputPath(job, new Path(args[1]+ "sd"+new Random().nextInt()));
	    TransWriter.setOutputPath(job, new Path(args[1]));
	    job.setOutputFormatClass(TransWriter.class);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
