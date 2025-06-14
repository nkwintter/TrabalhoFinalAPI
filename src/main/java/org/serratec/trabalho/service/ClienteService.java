package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.EnderecoCliente;
import org.serratec.trabalho.domain.EnderecoViaCep;
import org.serratec.trabalho.dto.ClienteDTO;
import org.serratec.trabalho.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SendEmailService emailService;

    // Buscar todos os clientes
    public List<ClienteDTO> buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesDTO.add(new ClienteDTO(cliente));
        }
        return clientesDTO;
    }

    // Buscar cliente por ID
    public ClienteDTO buscar(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            return new ClienteDTO(clienteOpt.get());
        }
        return null;
    }
    
 // Buscar cliente por email
    public ClienteDTO FindByEmail(String email) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if (clienteOpt.isPresent()) {
            return new ClienteDTO(clienteOpt.get());
        }
        return null;
    }

    // Inserir novo cliente
    public ClienteDTO inserir(ClienteDTO clienteDTO) {
        Cliente cliente = toEntity(clienteDTO);
        
        emailService.SendEmailCadastrado(cliente.getEmail(), "Cadastro realizado com sucesso!",
                cliente.getNome());
        
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }
    
    //REVISAR COM IF'S PARA EVITAR O REPREENCHIMENTO TOTAL !!!
    public ClienteDTO updateByEmail(String email, ClienteDTO dto) {
    	Cliente cliente = clienteRepository.findByEmail(email)
    			.orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    	
    	cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        cliente.setRole("USER");

        if (dto.getEndereco() != null) {
            String novoCep = dto.getEndereco().getCep();
            EnderecoViaCep enderecoApi = ViacepApiService.consultarViaCep(novoCep); 
            EnderecoCliente end = new EnderecoCliente();
            end.setCep(novoCep);
            end.setBairro(enderecoApi.getBairro());
            end.setLocalidade(enderecoApi.getLocalidade());
            end.setLogradouro(enderecoApi.getLogradouro());
            end.setUf(enderecoApi.getUf());
            cliente.setEndereco(end);
            cliente.getEndereco().setComplemeto(dto.getEndereco().getComplemento());
        }
        
        emailService.SendEmailUpdate(cliente.getEmail(), "Cadastro realizado com sucesso!",
                cliente.getNome());
        
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    	
    }
    
    public void deleteByEmail(String email) {
    	Cliente cliente = clienteRepository.findByEmail(email)
    			.orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    	
    	emailService.SendEmailRemovido(cliente.getEmail(), "Cadastro realizado com sucesso!",
                cliente.getNome());
    	
    	clienteRepository.delete(cliente);
    }

    // Conversão DTO -> Entidade
    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        cliente.setRole("USER");

        if (dto.getEndereco() != null) {
            EnderecoCliente end = new EnderecoCliente();
            end.setCep(dto.getEndereco().getCep());
            end.setBairro(dto.getEndereco().getBairro());
            end.setLocalidade(dto.getEndereco().getLocalidade());
            end.setLogradouro(dto.getEndereco().getLogradouro());
            end.setUf(dto.getEndereco().getUf());
            cliente.setEndereco(end);
        }

        return cliente;
    }
}