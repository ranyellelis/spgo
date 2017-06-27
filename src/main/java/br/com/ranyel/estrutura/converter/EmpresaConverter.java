package br.com.ranyel.estrutura.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ranyel.pedidos.domain.Empresa;

@FacesConverter(forClass=Empresa.class, value="empresaConverter")
public class EmpresaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent cpn, String str) {
		Empresa empresa = (Empresa) ctx.getExternalContext().getSessionMap().get("objEmpresa-"+ str);
		return empresa;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent cpn, Object obj) {
		if (obj instanceof Empresa) {
			Empresa empresa = (Empresa) obj;
			if (empresa.getId() != null) {
				ctx.getExternalContext().getSessionMap().put("objEmpresa-" + empresa.getId(), obj);
				return String.valueOf(empresa.getId());
			} else {
				return null;
			}
		}
		return null;
	}
}
