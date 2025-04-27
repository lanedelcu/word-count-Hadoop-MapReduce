package neiu.wordcount;

import org.apache.hadoop.io.IntWritable; //Hadoop specific wrappers for Java types
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper; //base class for creating mapper clas
import java.io.IOException;

public class WordCountMapper
        extends Mapper<LongWritable, Text, Text, IntWritable> {
    //<input key type, input value type, output key type, output value type>
    //eg: inputs we have <line number of the text, the actual line itself>, and the outputs <Word,1>
    @Override
    public void map(LongWritable key, Text value, Context context) //called for each input <key, value>
            throws IOException, InterruptedException { //once the <key, val> is processed, we write the output into a context obj

        String line = value.toString(); //converts the input of map(value) to a String
        String[] words = line.split(" "); //split the line of text into an array of individual words

        for (String word : words) {
            if(!word.isEmpty()) { // or word.length()>0 - you could have double space
                context.write(new Text(word), new IntWritable(1)); //writes the output <key, val>
            }
            //new Text(word) =output key. creates a new Text object containing the word
            //new IntWritable(1) = output val. creates a new IntWritable obj with the val 1
        }
    }
}

/*Context Context: part of Hadoop framework.
 * it stores the output
 * It's used to communicate across the MapReduce system.
 * the way we communicate between the mapper, the reducer, the merge, the sort and any other part of the MapReduce infrastructure.
 */
