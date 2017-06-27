package br.com.ranyel.pedidos.funcoes.dao;

import br.com.ranyel.estrutura.dao.HibernateDAO;
import br.com.ranyel.pedidos.domain.Pedido;

public class PedidoDAO extends HibernateDAO<Pedido> {

	public void update(Pedido pedido){
		em.merge(pedido);
	}
}
