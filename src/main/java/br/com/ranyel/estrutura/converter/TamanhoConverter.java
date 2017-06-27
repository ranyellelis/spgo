package br.com.ranyel.estrutura.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ranyel.pedidos.domain.Tamanho;

@FacesConverter(forClass=Tamanho.class, value="tamanhoConverter")
public class TamanhoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent cpn, String str) {
		Tamanho tamanho = (Tamanho) ctx.getExternalContext().getSessionMap().get("objTamanho-"+ str);
		return tamanho;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent cpn, Object obj) {
		if (obj instanceof Tamanho) {
			Tamanho tamanho = (Tamanho) obj;
			if (tamanho.getId() != null) {
				ctx.getExternalContext().getSessionMap().put("objTamanho-" + tamanho.getId(), obj);
				return String.valueOf(tamanho.getId());
			} else {
				return null;
			}
		}
		return null;
	}
}
