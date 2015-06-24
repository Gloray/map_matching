package cn.edu;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TransWriter extends SequenceFileOutputFormat<Text, Text> {
	public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
		return new TransRecordWriter(context);
	}
	
	private class TransRecordWriter extends RecordWriter<Text, Text> {

		TaskAttemptContext context = null;
		FileSystem fs;
		public TransRecordWriter(TaskAttemptContext context) throws IOException  {
			this.context = context;
			fs=FileSystem.get(context.getConfiguration());
		}
		
		@Override
		public void close(TaskAttemptContext context) throws IOException,InterruptedException {
			
		}

		@Override
		public void write(Text key, Text value) throws IOException,InterruptedException {			
			if (value.toString().trim().length()>0) {				
				String[] pairs=value.toString().split(";");
				FileOutputCommitter committer = (FileOutputCommitter) getOutputCommitter(context);
				Path path = new Path(committer.getWorkPath().toString().split("_")[0], key.toString()+".xls");			
				FSDataOutputStream out=fs.create(path);
				
				Workbook wb = new HSSFWorkbook();   
			    Sheet sheet=wb.createSheet("Sheet1");
			    Row nameRow = sheet.createRow(0);
			    Cell name1 = nameRow.createCell(0);
			    name1.setCellValue("Longitude");
			    Cell name2 = nameRow.createCell(1);
			    name2.setCellValue("Latitude");
			    Cell name3 = nameRow.createCell(2);
			    name3.setCellValue("Time");
			    Cell name4 = nameRow.createCell(3);
			    name4.setCellValue("Direction");
			    
				for (int i = 0; i < pairs.length; i++) {
					
					String[] pair=pairs[i].split(",");
					Row row = sheet.createRow((short)i+1);		    
				    Cell cell0 = row.createCell(0);	
				    cell0.setCellValue(Double.parseDouble(pair[0]));
				    Cell cell1 = row.createCell(1);	
				    cell1.setCellValue(Double.parseDouble(pair[1]));
				    Cell cell2 = row.createCell(2);
				    cell2.setCellValue(Long.parseLong(pair[2]));
				    Cell cell3 = row.createCell(3);
				    cell3.setCellValue(Short.parseShort(pair[3]));
				} 		    
			    wb.write(out);
			    out.close();
			}			
		}
	}
}
