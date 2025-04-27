package neiu.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WordCountReducer
        extends Reducer<Text, IntWritable, Text, IntWritable> {
    //<input key type, input value type, output key type, output value type>
    //the input val in our case is a list, but we have IntWritable ?! --> the reducer always gets a list of val for every input key

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) //iterable list of 1's
            throws IOException, InterruptedException { //once the <key, val> is processed, we write the output into a context obj

        int sum = 0;
        for (IntWritable val : values) { //iterate through the list of values that was the function input
            sum += val.get(); //compute the sum of values (extract the int values from IntWritable obj & adds it to the sum)
        }
        context.write(key, new IntWritable(sum)); //writes the output <key, val>
        //we write the key as is -- word itself
        // new IntWritable(sum)= creates a new IntWritable obj with the val of sum
    }
}

//Input: sorted <word, list>: <A,1>, <At,1>, <Barn,1,1,1>, <Behind,1>
//...reduce() -->
//Output: <Word, count>: <Little,2> <Farmer,2> …<Door,1>...