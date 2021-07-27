package com.example.spark.service;

import java.util.ArrayList;
import java.util.List;

import com.example.spark.model.User;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.Serializable;

@Service
public class UserService implements IUserService, Serializable {

    @Autowired
    //needs to be transient to be serializable
    private transient JavaSparkContext context;

    List<User> users = new ArrayList<>();
        
    @Override
    public String addUsers(){
        // Create a RDD of 10 documents
        users.add(new User("gandalf","x@x.com"));
        users.add(new User("bilbo","x@x.com"));

        JavaRDD<Document> documents = context.parallelize(users).map
                (new Function<User, Document>() {
        public Document call(final User i) throws Exception {
            return Document.parse("{name: '" + i.getName()+ "', email: '"+i.getEmail()+"' }");
        }
        });
        /*Start Example: Save data from RDD to MongoDB*****************/
        MongoSpark.save(documents);
        /*End Example**************************************************/
        context.close();

        return "Saved";  
    }
    
    @Override
    public String findAll(){
        //Start Example: Read data from MongoDB************************
        JavaMongoRDD<Document> rdd = MongoSpark.load(context);
        //End Example**************************************************
        // Analyze data from MongoDB
        System.out.println(rdd.count());
        //System.out.println("es este"+rdd.first());
        //rdd.first().toJson();
        rdd.foreach(x->System.out.println(x.toJson()));

        Dataset<Row> implicitDS = MongoSpark.load(context).toDF();
        implicitDS.printSchema();
        implicitDS.show(20, false);

        context.close(); 

        return "x";
    }
    
    
}
