package com.viacepjavaspring;

import com.viacepjavaspring.entity.EnderecoEntity;
import com.viacepjavaspring.service.EnderecoService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ViacepJavaSpringApplication {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("#######################");
		System.out.println("### Consulta de CEP ###");
		System.out.println("#######################");
		EnderecoEntity enderecoEntity = new EnderecoEntity();
		int opcao;

		while(true){
			System.out.print("Digite o CEP: ");
			String cep = scanner.next();

			enderecoEntity = EnderecoService.buscaEnderecoPeloCep(cep);
			System.out.println("\nCEP: " + enderecoEntity.getCep());
			System.out.println("UF: " + enderecoEntity.getUf());
			System.out.println("Cidade: " + enderecoEntity.getLocalidade());
			System.out.println("Bairro: " + enderecoEntity.getBairro());
			System.out.println("Logradouro: " + enderecoEntity.getLogradouro());
			System.out.println("\nDeseja buscar novamente?\n<0> Não\n<1> Sim\nSua opcao: ");
			opcao = scanner.nextInt();
			if(opcao == 0){
				break;
			}
		}
		System.out.print("Fim de programa!");

	}
}





