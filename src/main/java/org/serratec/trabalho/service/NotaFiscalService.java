package org.serratec.trabalho.service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import org.serratec.trabalho.dto.PedidoDTOSimplificado;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class NotaFiscalService {

    public String gerarNotaFiscal(PedidoDTOSimplificado dto) {
        try {
            String nomeArquivo = "nota_fiscal_pedido_" + dto.getId() + ".pdf";

            Path pastaNotas = Paths.get("notasFiscais");
            Files.createDirectories(pastaNotas);
            
            Path caminhoArquivo = pastaNotas.resolve(nomeArquivo);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo.toFile()));
            document.open();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHoraFormatada = dto.getDataHoraCriacao().format(formatter);
            
            document.add(new Paragraph("NOTA FISCAL"));
            document.add(new Paragraph("Pedido nº: " + dto.getId()));
            document.add(new Paragraph("Cliente: " + dto.getCliente().getNome()));
            document.add(new Paragraph("CPF: " + dto.getCliente().getCpf()));
            document.add(new Paragraph("Data e Hora: " + dataHoraFormatada));
            document.add(new Paragraph("Status: " + dto.getStatus()));
            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(3);
            tabela.addCell("Produto");
            tabela.addCell("Quantidade");
            tabela.addCell("Valor Unitário");

            dto.getItens().forEach(item -> {
                tabela.addCell(item.getNomeProduto()) ;
                tabela.addCell(String.valueOf(item.getQuantidade()));
                tabela.addCell("R$" + item.getValorProduto());
            });

            document.add(tabela);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Subtotal: R$" + dto.getTotalSemDesconto()));
            document.add(new Paragraph("Percentual de desconto: " + dto.getPercentualDesconto() + "%"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total do Pedido: R$" + dto.getTotalPedido()));
            
            

            document.close();
            
            
            System.out.println("PDF gerado em: " + caminhoArquivo.toAbsolutePath());

            return "/notasFiscais/" + nomeArquivo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar nota fiscal: " + e.getMessage());
        }
    }
}