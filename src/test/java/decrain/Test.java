package decrain;

import com.decrain.spark.mllib.classifier.DataFactory;
import com.decrain.spark.mllib.classifier.NaiveBayesTest;
import com.decrain.spark.mllib.classifier.NaiveBayesTrain;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

/**
 * Created by Decrain on 2017/7/26.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String log4jConfPath = "/Users/decrain/IdeaProjects/Classifier/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);


        DataFactory.main(null);
        NaiveBayesTrain.main(null);
        NaiveBayesTest.main(null);

    }

}
