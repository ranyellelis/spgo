package br.com.ranyel.pedidos.funcoes.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.ranyel.estrutura.dao.HibernateDAO;
import br.com.ranyel.pedidos.domain.ProdutoTamanho;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;

public class ProdutoTamanhoDAO extends HibernateDAO<ProdutoTamanho>{

	public List<ProdutoTamanho> buscarPorCategoriaTamanho(ProdutoTamanho pt){
		Criteria crit = getSession().createCriteria(ProdutoTamanho.class);
		crit.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		crit.add(Restrictions.eq("tamanho", pt.getTamanho()));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		Criteria crit2 = crit.createCriteria("produto");
		crit2.add(Restrictions.eq("empresa", pt.getProduto().getEmpresa()));
		crit2.add(Restrictions.eq("categoria", pt.getProduto().getCategoria()));
		crit2.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		
		List<ProdutoTamanho> retorno = crit.list();
		if (retorno == null) {
			return Collections.emptyList();
		}
		return retorno;
	}
}
