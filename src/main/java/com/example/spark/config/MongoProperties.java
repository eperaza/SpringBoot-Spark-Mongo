package com.example.spark.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;
import lombok.Getter;
@Configuration
@ConfigurationProperties(prefix = "mongo")
@Setter
@Getter

public class MongoProperties {
	private String host;
	private String database;
	private String collectionName;

	public String getMongoUri() {
		return "mongodb://" + getHost() + "/" + getDatabase() + "." + getCollectionName();
	}
}