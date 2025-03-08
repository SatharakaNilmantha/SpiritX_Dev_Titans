package com.example.SpiritX_Dev_Titans_01_Server_Side;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpiritXDevTitans01ServerSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiritXDevTitans01ServerSideApplication.class, args);
	}

}
