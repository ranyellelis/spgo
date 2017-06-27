package br.com.ranyel.pedidos.funcoes.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.pedidos.domain.Item;
import br.com.ranyel.pedidos.domain.Pedido;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.enums.SituacaoPedidoEnum;
import br.com.ranyel.pedidos.funcoes.dao.PedidoDAO;

public class PedidoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoDAO dao;
	
	public void buscarPorId(Long id) throws DaoException {
		dao.findById(id);
	}
	
	public void salvarPedido(Pedido p) throws DaoException {
		dao.update(p);
	}

	public void excluirPedido(Pedido p) throws DaoException {
		dao.delete(p);
	}

	public List<Pedido> buscarPedidos() throws DaoException {
		List<Pedido> lista = dao.findAll();
		Collections.sort(lista);
		return lista;
	}

	public List<Pedido> pesquisarPedido(Pedido p) throws DaoException {
		List<Pedido> lista = dao.findByExample(p);
		Collections.sort(lista);
		return lista;
	}
	
	public List<Pedido> pesquisarMeusPedidos(Usuario cliente){
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			criterions.add(Restrictions.eq("cliente", cliente));
			criterions.add(Restrictions.ne("situacao", SituacaoPedidoEnum.PAGO));
			criterions.add(Restrictions.ne("situacao", SituacaoPedidoEnum.CANCELADO));
			List<Pedido> lista = dao.findByCriterions(criterions);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public List<Pedido> pesquisarPedidosEfetuados(){
		Pedido ex = new Pedido();
		ex.setSituacao(SituacaoPedidoEnum.EFETUADO);
		try {
			List<Pedido> lista = dao.findByExample(ex);
			carregaItemProdutos(lista);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public List<Pedido> pesquisarPedidosEmPreparo(){
		Pedido ex = new Pedido();
		ex.setSituacao(SituacaoPedidoEnum.PREPARANDO);
		try {
			List<Pedido> lista = dao.findByExample(ex);
			carregaItemProdutos(lista);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	private void carregaItemProdutos(List<Pedido> pedidos){
		for (Pedido pedido : pedidos) {
			for (Item i : pedido.getItems()) {
				i.getProdutoTamanhos();
			}
		}
	}
	
	public List<Pedido> pesquisarPedidosNaoPagos(Usuario cliente){
		try {
			List<Criterion> criterions = new ArrayList<>();
			criterions.add(Restrictions.eq("situacao", SituacaoPedidoEnum.ENTREGUE));
			criterions.add(Restrictions.eq("cliente", cliente));
			List<Pedido> lista = dao.findByCriterions(criterions);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public List<Pedido> pesquisarPedidosEmEntrega(){
		Pedido ex = new Pedido();
		ex.setSituacao(SituacaoPedidoEnum.ENTREGANDO);
		try {
			List<Pedido> lista = dao.findByExample(ex);
			carregaItemProdutos(lista);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public List<Pedido> pesquisarPedidosProntos(){
		Pedido ex = new Pedido();
		ex.setSituacao(SituacaoPedidoEnum.PRONTO);
		try {
			List<Pedido> lista = dao.findByExample(ex);
			carregaItemProdutos(lista);
			Collections.sort(lista);
			return lista;
		} catch (DaoException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public PedidoDAO getDao() {
		return dao;
	}
}
