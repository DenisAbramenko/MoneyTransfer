package org.example.moneytransfer;

import org.springframework.boot.SpringApplication;

public class TestMoneyTransferApplication {

	public static void main(String[] args) {
		SpringApplication.from(MoneyTransferApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
