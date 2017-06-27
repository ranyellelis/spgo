package br.com.ranyel.estrutura.utils;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author ranyel
 * 
 */
public class LoggerProducer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Produces
	public Logger produceLogger(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}
}
