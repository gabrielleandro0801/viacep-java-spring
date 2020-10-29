package com.viacepjavaspring;

import com.viacepjavaspring.utils.Endereco;
import com.viacepjavaspring.utils.ServicoDeCep;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ViacepJavaSpringApplication {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("#######################");
		System.out.println("### Consulta de CEP ###");
		System.out.println("#######################");
		int opcao = 1;
		while(opcao != 0){
			System.out.print("Digite o CEP: ");
			String cep = scanner.next();

			Endereco endereco = ServicoDeCep.buscaEnderecoPeloCep(cep);

			System.out.println("CEP: " + endereco.getCep());
			System.out.println("UF: " + endereco.getUf());
			System.out.println("Cidade: " + endereco.getLocalidade());
			System.out.println("Bairro: " + endereco.getBairro());
			System.out.println("Logradouro: " + endereco.getLogradouro());
			System.out.println("\nDeseja buscar novamente?\n<0> Não\n<1> Sim\nSua opcao: ");
			opcao = scanner.nextInt();
			if(opcao == 0){
				break;
			}
		}
		System.out.print("Fim de programa!");

	}
}





