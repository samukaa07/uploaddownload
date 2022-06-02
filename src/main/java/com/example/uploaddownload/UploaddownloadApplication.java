package com.example.uploaddownload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.uploaddownload.property.FileStorageProperties;

@SpringBootApplication

//EnableConfigurationProperties usado em qlq classe de configuração para habilitar ConfigurationProperties
@EnableConfigurationProperties({FileStorageProperties.class}) //@EnableConfigurationProperties usado em qlq classe de configuração para habilitar @ConfigurationProperties 
public class UploaddownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploaddownloadApplication.class, args);
	}

}
