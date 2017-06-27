package br.com.ranyel.estrutura.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass=Date.class,value= "dateConverter")
public class DateConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date retorno;
			try {
				retorno = sdf.parse(value);
			} catch (ParseException e) {
				return null;
			}
			return retorno;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(value);
		}
		return null;
	}
}
