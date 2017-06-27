package br.com.ranyel.estrutura.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ranyel.pedidos.domain.Categoria;

@FacesConverter(forClass=Categoria.class, value="categoriaConverter")
public class CategoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent cpn, String str) {
		Categoria categoria = (Categoria) ctx.getExternalContext().getSessionMap().get("objCategoria-"+ str);
		return categoria;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent cpn, Object obj) {
		if (obj instanceof Categoria) {
			Categoria categoria = (Categoria) obj;
			if (categoria.getId() != null) {
				ctx.getExternalContext().getSessionMap().put("objCategoria-" + categoria.getId(), obj);
				return String.valueOf(categoria.getId());
			} else {
				return null;
			}
		}
		return null;
	}
}
