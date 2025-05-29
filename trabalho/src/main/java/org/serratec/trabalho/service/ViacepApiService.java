package org.serratec.trabalho.service;

import org.serratec.trabalho.domain.EnderecoViaCep;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class ViacepApiService {
	
	//função para pegar os dados do viaCep 
		public static EnderecoViaCep consultarViaCep(String cep) {
		    String url = "https://viacep.com.br/ws/" + cep + "/json/";
		    RestTemplate restTemplate = new RestTemplate();
		    ResponseEntity<EnderecoViaCep> response = restTemplate.getForEntity(url, EnderecoViaCep.class);
		    return response.getBody();
		}
}
