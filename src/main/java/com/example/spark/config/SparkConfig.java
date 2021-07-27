package com.example.spark.config;

import java.io.Serializable;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig implements Serializable {
    @Autowired
	private MongoProperties mongoProperties;
	
	@Autowired
	private SparkProperties sparkProperties;

	@Value("${spring.application.name}")
	String appName;

    @Bean
	public SparkSession sparkSession() {
		return SparkSession.builder()
			      .master(sparkProperties.getMasterUri())
			      .appName(appName)
			      .config(sparkProperties.getInputUriName(), mongoProperties.getMongoUri())
			      .config(sparkProperties.getOutputUriName(), mongoProperties.getMongoUri())
			      .getOrCreate();

	}

	@Bean
	public JavaSparkContext javaSparkContext() {
		return new JavaSparkContext(sparkSession().sparkContext());
	}
    
}
