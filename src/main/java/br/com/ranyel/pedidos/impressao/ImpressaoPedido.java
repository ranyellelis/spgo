package br.com.ranyel.pedidos.impressao;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.ranyel.estrutura.utils.FacesUtil;
import br.com.ranyel.pedidos.domain.Item;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.ProdutoTamanho;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ImpressaoPedido {

	private Font normal = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private Font negrito = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private Locale ptbr = new Locale("pt", "BR");
	private String corte = "-------------------------------------------------------------";

	private ByteArrayOutputStream geraDocument(Pedido pedido) throws Exception {
		Document document = null;
		ByteArrayOutputStream baos = null;
		try {
			document = new Document(new Rectangle(getPontos(8), getPontos(12)), 1f, 1f, 1f, 1f);
			document.setMargins(10, 10, 10, 10);

			baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			Paragraph title = new Paragraph("SGPO - Sistema de Gerenciamento de Pedidos Online ", new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD));
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(5);

			Phrase num = new Phrase();
			num.add(new Phrase("NÚMERO DO PEDIDO: ", negrito));
			num.add(new Phrase("" + pedido.getNumero(), normal));
			Paragraph numPedido = new Paragraph(num);
			
			
			String horaFormatada = new SimpleDateFormat("HH:mm").format(pedido.getDataPedido());
			Phrase hora = new Phrase();
			hora.add(new Phrase("Hora: ", negrito));
			hora.add(new Phrase("" + horaFormatada, normal));
			Paragraph horaPedido = new Paragraph(hora);
			horaPedido.setSpacingAfter(5);
			
			Paragraph dadosCliente = new Paragraph("DADOS DO CLIENTE:", negrito);

			
			Phrase nomef = new Phrase();
			nomef.add(new Phrase("Nome:", negrito));
			nomef.add(new Phrase(" "+pedido.getCliente().getNome(), normal));
			Paragraph nome = new Paragraph(nomef);

			Phrase enderecof = new Phrase();
			enderecof.add(new Phrase("Endereço:", negrito));
			enderecof.add(new Phrase(" "+pedido.getCliente().getEndereco(), normal));
			Paragraph endereco = new Paragraph(enderecof);

			Phrase telefonef = new Phrase();
			telefonef.add(new Phrase("Telefone:", negrito));
			telefonef.add(new Phrase(" "+pedido.getCliente().getTelefone(), normal));
			Paragraph telefone = new Paragraph(telefonef);
			telefone.setSpacingAfter(7);
			
			Paragraph dadosPedido = new Paragraph("DADOS DO PEDIDO:", negrito);
			dadosPedido.setSpacingAfter(5);
			
			PdfPTable produtos = getTabelaItens(pedido.getItems());
			
			Phrase valorTotalf = new Phrase();
			valorTotalf.add(new Phrase("VALOR TOTAL: ", negrito));
			String valorPedido = NumberFormat.getCurrencyInstance(ptbr).format(pedido.getValorPedido());
			valorTotalf.add(new Phrase(valorPedido, normal));
			Paragraph valorTotal = new Paragraph(valorTotalf);
			
			Paragraph obsLabel = new Paragraph("Observações:", negrito);
			Paragraph obsValor = new Paragraph(pedido.getObservacoes(),normal);
			
			Paragraph corteLabel = new Paragraph(corte, negrito);
			
			document.add(corteLabel);
			document.add(title);
			document.add(numPedido);
			document.add(horaPedido);
			document.add(dadosCliente);
			document.add(nome);
			document.add(endereco);
			document.add(telefone);
			document.add(dadosPedido);
			document.add(produtos);
			document.add(valorTotal);
			document.add(obsLabel);
			document.add(obsValor);
			
			document.add(corteLabel);
			return baos;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("Erro ao gerar impressão");
		} finally {
			document.close();
			baos.close();
		}
	}

	private PdfPTable getTabelaItens(List<Item> lista) throws Exception {
		PdfPTable table = new PdfPTable(4);
		float[] colunas = {15f, 50f, 15f, 20f}; 
		table.setWidthPercentage(colunas, new Rectangle(100f, 100f));
		Phrase item = new Phrase("ITEM", normal);
		table.addCell(item);
		
		Phrase descricao = new Phrase("DESCRIÇÃO", normal);
		table.addCell(descricao);
		
		Phrase tamanho = new Phrase("TAM.", normal);
		table.addCell(tamanho);
		
		Phrase valore = new Phrase("VALOR", normal);
		table.addCell(valore);
		
		String quebraLinha = System.getProperty("line.separator");
		for (Item i : lista) {
			int numItem = 1;
			Phrase num = new Phrase("" + numItem, normal);
			table.addCell(num);
			
			String nomeProduto = "";
			for (ProdutoTamanho pt : i.getProdutoTamanhos()) {
				nomeProduto += i.getProporcao();
				nomeProduto += " ";
				nomeProduto += pt.getProduto().getNome();
				nomeProduto += quebraLinha;
			}
			
			Phrase nome = new Phrase(nomeProduto, normal);
			table.addCell(nome);
			
			String tamanhoValor = i.getProdutoTamanhos().get(0).getTamanho().getSigla();
			Phrase tam = new Phrase(tamanhoValor, normal);
			table.addCell(tam);
			
			String vlr = NumberFormat.getCurrencyInstance(ptbr).format(i.getValor());
			Phrase valor = new Phrase(vlr, normal);
			table.addCell(valor);
			numItem++;
		}
		return table;
	}

	private int getPontos(int cm) {
		double c = 2.54;
		double p = 72;
		return (int) (p * cm / c);
	}

	public void geraImpressao(Pedido pedido) throws Exception {
		FacesContext fc = FacesUtil.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		mostraNoBrowser(response, "Pedido"+pedido.getNumero());

		byte[] bytes = geraDocument(pedido).toByteArray();

		ServletOutputStream sos = response.getOutputStream();
		sos.write(bytes, 0, bytes.length);
		sos.flush();
		sos.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	private void mostraNoBrowser(HttpServletResponse response, String nomeArquivo) {
		response.setHeader("Content-Disposition", "inline;filename=\"" + nomeArquivo + ".pdf" + "\";");
	}

	@SuppressWarnings("unused")
	private void forcaDownload(HttpServletResponse response, String nomeArquivo) {
		response.setHeader("Content-Disposition", "attachment;filename=\"" + nomeArquivo + ".pdf" + "\";");
	}
}