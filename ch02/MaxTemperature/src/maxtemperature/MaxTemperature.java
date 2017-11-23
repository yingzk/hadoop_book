package maxtemperature;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.jersey.api.client.ClientResponse.Status;

public class MaxTemperature {
	public static void main(String[] args) throws Exception {
		long startTime=System.currentTimeMillis();
		if (args.length != 2)	{
			System.err.println("Please set the right parammeters.");
			System.exit(-1);
		}
		
		Job job = new Job();
		job.setJarByClass(MaxTemperature.class);
		job.setJobName("Max Temperature");
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(MaxTemperatureMapper.class);
		//job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		boolean status = job.waitForCompletion(true);
		long endTime=System.currentTimeMillis();
		
		System.err.println("Program time: "+(endTime-startTime)+"ms");
		System.exit(status ? 0 : 1);
	}
}

/*
 *  Normal: 4345ms
 *	Combiner: 3219ms
 */