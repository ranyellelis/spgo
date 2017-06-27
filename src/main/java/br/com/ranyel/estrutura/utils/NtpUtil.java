package br.com.ranyel.estrutura.utils;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import br.com.ranyel.estrutura.exception.NtpException;

public class NtpUtil {

	public static void main(String[] args) {
		try {
			NtpUtil util = new NtpUtil();
			util.getHoraNtpBr();
			util.Teste();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Date getDataNtpBr() throws NtpException {
		try {
			String ntpServer = getServidorNTP();
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(ntpServer);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			return timeInfo.getMessage().getReceiveTimeStamp().getDate();
		} catch (Exception e) {
			throw new NtpException(e);
		}
	}

	public void getHoraNtpBr() throws NtpException {
		try {
			Date data = getDataNtpBr();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			System.out.println("Data e hora atualizada: " + sdf.format(data));
		} catch (Exception e) {
			throw new NtpException(e);
		}
	}

	private String getServidorNTP() {
		List<String> servidores = getListaServidorNTP();
		Random random = new Random();
		int sorteado = random.nextInt(servidores.size());
		System.out.println(servidores.get(sorteado));
		return servidores.get(sorteado);
	}

	private List<String> getListaServidorNTP() {
		List<String> retorno = new ArrayList<>();
		retorno.add("a.st1.ntp.br");
		retorno.add("b.st1.ntp.br");
		retorno.add("c.st1.ntp.br");
		retorno.add("a.ntp.br");
		retorno.add("b.ntp.br");
		retorno.add("c.ntp.br");
		return retorno;
	}

	public void Teste() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt,br"));
		Date dataAtual = null;
		try {
			String dataDeHoje = sdf.format(new Date());
			System.out.println(dataDeHoje); 
			dataAtual = sdf.parse(dataDeHoje);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(dataAtual);
	}
}
