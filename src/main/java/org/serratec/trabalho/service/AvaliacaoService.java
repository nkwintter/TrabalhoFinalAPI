package org.serratec.trabalho.service;

import java.util.List;

import org.serratec.trabalho.domain.Avaliacao;
import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.Pedido;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.AvaliacaoDTO;
import org.serratec.trabalho.exception.ClienteNaoAutorizadoException;
import org.serratec.trabalho.exception.ProdutoNaoEncontradoException;
import org.serratec.trabalho.repository.AvaliacaoRepository;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.repository.PedidoRepository;
import org.serratec.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Avaliacao criarAvaliacao(Long clienteId, AvaliacaoDTO dto) {
        // Verifica se o cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verifica se produto existe
        Produto produto = produtoRepository.findById(dto.getProdutoId())
            .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        // Verifica se o cliente comprou o produto
        List<Pedido> pedidos = pedidoRepository.findPedidosByClienteEProduto(clienteId, dto.getProdutoId());
        if (pedidos.isEmpty()) {
            throw new ClienteNaoAutorizadoException("Você não comprou este produto e não pode avaliá-lo.");
        }

        // Verifica se já existe avaliação do cliente para o mesmo produto
        if (avaliacaoRepository.findByClienteIdAndProdutoId(clienteId, dto.getProdutoId()).isPresent()) {
            throw new ClienteNaoAutorizadoException("Você já avaliou este produto.");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCliente(cliente);
        avaliacao.setProduto(produto);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> listarAvaliacoesPorProduto(Long produtoId) {
        return avaliacaoRepository.findByProdutoId(produtoId);
    }
    
    @Transactional
    public Avaliacao editarAvaliacao(Long clienteId, Long avaliacaoId, AvaliacaoDTO dto) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
            .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        if (!avaliacao.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Cliente não pode editar avaliação de outro usuário");
        }

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return avaliacaoRepository.save(avaliacao);
    }

    @Transactional
    public void deletarAvaliacao(Long clienteId, Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
            .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        if (!avaliacao.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Você não pode deletar avaliação de outro usuário");
        }

        avaliacaoRepository.delete(avaliacao);
    }
}
