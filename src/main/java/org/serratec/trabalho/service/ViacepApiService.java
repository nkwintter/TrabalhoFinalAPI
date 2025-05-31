package org.serratec.trabalho.service;

import org.serratec.trabalho.domain.EnderecoViaCep;
import org.serratec.trabalho.exception.ViaCepException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class ViacepApiService {
	
	//função para pegar os dados do viaCep 
		public static EnderecoViaCep consultarViaCep(String cep) {
		    String url = "https://viacep.com.br/ws/" + cep + "/json/";
		    RestTemplate restTemplate = new RestTemplate();    
        try {
            ResponseEntity<EnderecoViaCep> response = restTemplate.getForEntity(url, EnderecoViaCep.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new ViaCepException("CEP inválido ou não encontrado. Por favor, verifique o número digitado!");
        } catch (Exception e) {
            throw new ViaCepException("Erro ao consultar o serviço do ViaCEP.");
        }
    }
}

