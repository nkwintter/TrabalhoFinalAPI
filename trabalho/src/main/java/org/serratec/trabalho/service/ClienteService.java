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
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

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

    // Inserir novo cliente
    public ClienteDTO inserir(ClienteDTO clienteDTO) {
        Cliente cliente = toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }

    // Atualizar cliente
    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNome(clienteDTO.getNome());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setTelefone(clienteDTO.getTelefone());

            if (clienteDTO.getEndereco() != null) {
                String novoCep = clienteDTO.getEndereco().getCep();
                EnderecoViaCep enderecoApi = ViacepApiService.consultarViaCep(novoCep); 
                EnderecoCliente end = new EnderecoCliente();
                end.setCep(novoCep);
                end.setBairro(enderecoApi.getBairro());
                end.setLocalidade(enderecoApi.getLocalidade());
                end.setLogradouro(enderecoApi.getLogradouro());
                end.setUf(enderecoApi.getUf());
                cliente.setEndereco(end);
            }

            cliente = clienteRepository.save(cliente);
            return new ClienteDTO(cliente);
        }
        return null;
    }

    // Deletar cliente
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    // Conversão DTO -> Entidade
    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());

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