package br.com.ranyel.pedidos.funcoes.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.ranyel.estrutura.dao.DAO;
import br.com.ranyel.estrutura.exception.DaoException;
import br.com.ranyel.estrutura.exception.ServiceException;
import br.com.ranyel.pedidos.domain.Categoria;
import br.com.ranyel.pedidos.domain.DataAcesso;
import br.com.ranyel.pedidos.domain.Empresa;
import br.com.ranyel.pedidos.domain.Produto;
import br.com.ranyel.pedidos.domain.ProdutoTamanho;
import br.com.ranyel.pedidos.domain.Tamanho;
import br.com.ranyel.pedidos.domain.Usuario;
import br.com.ranyel.pedidos.dto.ProdutoQtdDiaValor;
import br.com.ranyel.pedidos.enums.AtivoInativoEnum;
import br.com.ranyel.pedidos.enums.TipoUsuarioEnum;
import br.com.ranyel.pedidos.funcoes.dao.DataAcessoDAO;
import br.com.ranyel.pedidos.funcoes.dao.EmpresaDAO;
import br.com.ranyel.pedidos.funcoes.dao.PedidoDAO;
import br.com.ranyel.pedidos.funcoes.dao.ProdutoDAO;
import br.com.ranyel.pedidos.funcoes.dao.ProdutoTamanhoDAO;
import br.com.ranyel.pedidos.funcoes.dao.TamanhoDAO;
import br.com.ranyel.pedidos.funcoes.dao.UsuarioDAO;
import br.com.ranyel.pedidos.funcoes.dao.categoriaDAO;
import br.com.ranyel.pedidos.funcoes.ui.UsuarioLogadoBean;

public class CadastroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDAO produtoDAO;

	@Inject
	private TamanhoDAO tamanhoDAO;
	
	@Inject
	private DataAcessoDAO dataAcessoDAO;
	
	@Inject
	private PedidoDAO pedidoDAO;
	
	@Inject
	private ProdutoTamanhoDAO produtoTamanhoDAO;

	@Inject
	private categoriaDAO categoriaDAO;

	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private EmpresaDAO empresaDAO;

	public List<Empresa> buscarEmpresas() throws DaoException {
		return empresaDAO.findAll();
	}

	public void salvarProduto(Produto produto) throws DaoException {
		if (produto.getId() != null) {
			produtoDAO.update(produto);

		} else {
			produtoDAO.save(produto);
		}

	}

	public void salvarProdutoTamanho(ProdutoTamanho pt) throws DaoException{
		if (pt.getId() != null) {
			produtoTamanhoDAO.update(pt);

		} else {
			produtoTamanhoDAO.save(pt);
		}
	}
	
	public void excluirProduto(Produto p) throws DaoException {
		p.setAtivoInativo(AtivoInativoEnum.I);
		produtoDAO.update(p);
	}

	public List<Tamanho> buscarTamanhos() throws DaoException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		List<Tamanho> consulta = tamanhoDAO.findByCriterions(criterions);
		return consulta;
	}

	public void salvarTamanho(Tamanho tamanho) throws DaoException {
		if (tamanho.getId() != null) {
			tamanhoDAO.update(tamanho);

		} else {
			tamanhoDAO.save(tamanho);
		}
	}

	public void excluirTamanho(Tamanho p) throws DaoException {
		p.setAtivoInativo(AtivoInativoEnum.I);
		tamanhoDAO.update(p);
	}

	public List<Tamanho> pesquisarTamanho(Tamanho p) throws DaoException {
		if(p.getPartes().equals(0)){
			p.setPartes(null);
		}
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Example.create(p).enableLike(MatchMode.ANYWHERE).ignoreCase());
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		List<Tamanho> consulta = tamanhoDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public List<Categoria> buscarCategorias() throws DaoException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativoEnum", AtivoInativoEnum.A));
		List<Categoria> consulta = categoriaDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public void salvarCategoria(Categoria categoria) throws DaoException {
		if (categoria.getId() != null) {
			categoriaDAO.update(categoria);

		} else {
			categoriaDAO.save(categoria);
		}
	}
	
	public void atualizarProdutoTamanho(ProdutoTamanho pt) throws DaoException {
		if (pt.getId() != null) {
			produtoTamanhoDAO.update(pt);

		} else {
			produtoTamanhoDAO.save(pt);
		}
	}

	public void excluirCategoria(Categoria c) throws DaoException {
		c.setAtivoInativoEnum(AtivoInativoEnum.I);
		categoriaDAO.update(c);
	}

	public List<Categoria> pesquisarCategoria(Categoria p) throws DaoException {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Example.create(p).enableLike(MatchMode.ANYWHERE).ignoreCase());
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativoEnum", AtivoInativoEnum.A));
		List<Categoria> consulta = categoriaDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public List<Produto> buscarCardapio() throws DaoException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		List<Produto> consulta = produtoDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public List<Produto> pesquisarProduto(Produto p) throws DaoException {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Example.create(p).enableLike(MatchMode.ANYWHERE).ignoreCase());
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		List<Produto> consulta = produtoDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}
	
	
	
	public List<ProdutoTamanho> pesquisarProdutoTamanhoPorCategoriaTamanho(ProdutoTamanho pt) throws DaoException {
		return produtoTamanhoDAO.buscarPorCategoriaTamanho(pt);
	}

	public void salvarUsuario(Usuario u) throws ServiceException {
		try {
			if (isExisteUsuarioCadastrado(u)) {
				throw new ServiceException("Dados de Login já cadastrados.");
			} else {
				usuarioDAO.save(u);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void alterarUsuario(Usuario u) throws ServiceException {
		try {
			Usuario usuario = pesquisarUsuarioPorLogin(u.getLogin(), u.getTipoUsuario());

			if (usuario != null && !usuario.getId().equals(u.getId())) {
				throw new ServiceException("Dados de Login já cadastrados.");
			} else {
				usuarioDAO.update(u);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public boolean isExisteUsuarioCadastrado(Usuario u) throws ServiceException {
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			criterions.add(Restrictions.eq("login", u.getLogin()));
			criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
			List<Usuario> consulta = usuarioDAO.findByCriterions(criterions);
			if (consulta.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Usuario procurarUsuario(String login, String senha, TipoUsuarioEnum tipo) throws ServiceException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("login", login));
		criterions.add(Restrictions.eq("senha", senha));
		criterions.add(Restrictions.eq("tipoUsuario", tipo));
		try {
			List<Usuario> resultado = usuarioDAO.findByCriterions(criterions);

			if (resultado.isEmpty()) {
				throw new ServiceException("Usuário não encontrado!");
			} else {
				if (resultado.size() > 1) {
					throw new ServiceException("Foi encontrado mais de um cadastro para o mesmo Login");
				} else {
					return resultado.get(0);
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Usuario procurarUsuario(String login, String senha) throws ServiceException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("login", login));
		criterions.add(Restrictions.eq("senha", senha));
		try {
			List<Usuario> resultado = usuarioDAO.findByCriterions(criterions);

			if (resultado.isEmpty()) {
				throw new ServiceException("Usuário não encontrado!");
			} else {
				if (resultado.size() > 1) {
					throw new ServiceException("Foi encontrado mais de um cadastro para o mesmo Login");
				} else {
					return resultado.get(0);
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Usuario pesquisarUsuarioPorLogin(String login, TipoUsuarioEnum tipo) throws ServiceException {
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			criterions.add(Restrictions.eq("login", login));
			criterions.add(Restrictions.eq("tipoUsuario", tipo));
			criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
			
			List<Usuario> consulta = usuarioDAO.findByCriterions(criterions);
			Collections.sort(consulta);
			
			if (!consulta.isEmpty()) {
				return consulta.get(0);
			} else {
				return null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Usuario pesquisarUsuarioPorNome(String nome, TipoUsuarioEnum tipo) throws ServiceException {
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			criterions.add(Restrictions.like("nome", nome));
			criterions.add(Restrictions.eq("tipoUsuario", tipo));
			criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
			List<Usuario> consulta = usuarioDAO.findByCriterions(criterions);
			if (!consulta.isEmpty()) {
				return consulta.get(0);
			} else {
				return null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void excluirUsuario(Usuario p) throws DaoException {
		p.setAtivoInativo(AtivoInativoEnum.I);
		usuarioDAO.update(p);
	}

	public List<Usuario> pesquisarUsuarios(Usuario p) throws DaoException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Example.create(p).enableLike(MatchMode.ANYWHERE).ignoreCase());
		criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		
		List<Usuario> consulta = usuarioDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public List<Usuario> pesquisarFuncionario(String nome, String login) throws DaoException {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!nome.isEmpty()) {
			criterions.add(Restrictions.eq("nome", nome));
		}
		if (!login.isEmpty()) {
			criterions.add(Restrictions.eq("login", login));
		}

		criterions.add(Restrictions.eq("ativoInativo", AtivoInativoEnum.A));
		criterions.add(Restrictions.ne("tipoUsuario", TipoUsuarioEnum.CLIENTE));
		if (!usuarioLogadoBean.isAdministrador()) {
			criterions.add(Restrictions.eq("empresa", usuarioLogadoBean.getUsuarioLogado().getEmpresa()));
		}
		
		List<Usuario> consulta = usuarioDAO.findByCriterions(criterions);
		Collections.sort(consulta);
		return consulta;
	}

	public Usuario buscarAlgumUsuario() throws ServiceException {
		Usuario teste = new Usuario();
		teste.setId(1l);
		try {
			return (Usuario) usuarioDAO.findByExample(teste).get(0);
		} catch (DaoException e) {
			throw new ServiceException("Ocorreu um erro ao pesquisar usuário.");
		}
	}

	public List<Empresa> pesquisarEmpresa(String nome, String cnpj) throws ServiceException {
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			if (!nome.isEmpty()) {
				criterions.add(Restrictions.eq("nome", nome));
			}

			if (!cnpj.isEmpty()) {
				criterions.add(Restrictions.eq("cnpj", cnpj));
			}
			return empresaDAO.findByCriterions(criterions);
		} catch (DaoException e) {
			throw new ServiceException("Ocorreu um erro ao pesquisar empresas.");
		}
	}

	public void alterarEmpresa(Empresa u) throws ServiceException {
		try {
			Empresa empresa = pesquisarEmpresaPorCnpj(u.getCnpj());

			if (empresa != null && !empresa.getId().equals(u.getId())) {
				throw new ServiceException("Dados de Login já cadastrados.");
			} else {
				empresaDAO.update(u);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Empresa pesquisarEmpresaPorCnpj(String cnpj) throws ServiceException {
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			criterions.add(Restrictions.eq("cnpj", cnpj));
			List<Empresa> consulta = empresaDAO.findByCriterions(criterions);
			if (!consulta.isEmpty()) {
				return consulta.get(0);
			} else {
				return null;
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public void excluirEmpresa(Empresa empresa) throws DaoException {
		empresaDAO.delete(empresa);
	}

	public void salvarEmpresa(Empresa u) throws ServiceException {
		try {
			if (isExisteEmpresaCadastrada(u)) {
				throw new ServiceException("Dados de Login já cadastrados.");
			} else {
				empresaDAO.save(u);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private boolean isExisteEmpresaCadastrada(Empresa u) throws ServiceException {
		Empresa consulta = pesquisarEmpresaPorCnpj(u.getCnpj());
		if (consulta != null) {
			return true;
		} else {
			return false;
		}
	}

	@Inject
	private DAO dao = new DAO();

	@SuppressWarnings("unchecked")
	public List<ProdutoQtdDiaValor> getMaisVendidosLista(String dataInicial, String dataFinal) {
		StringBuilder query = new StringBuilder();
		query.append("select i.produto, count(date(ped.dataPedido)), date(ped.dataPedido), i.valor from TB_ITEM  i ");
		query.append("join TB_PEDIDO ped on i.pedido_id = ped.numero ");
		query.append("where ped.situacao = 6 and ");
		query.append("date(ped.dataPedido) between ");
		query.append("'" + dataInicial + "'" + " ");
		query.append("and ");
		query.append("'" + dataFinal + "'" + " ");
		query.append("group by i.produto, date(ped.dataPedido) ");
		query.append("order by count(date(ped.dataPedido)) DESC");

		List<Object[]> resultado = dao.getEm().createNativeQuery(query.toString()).getResultList();
		List<ProdutoQtdDiaValor> lista = new ArrayList<>();
		for (Object[] o : resultado) {
			String produto = (String) o[0];
			Integer qtd = ((BigInteger) o[1]).intValue();
			Date data = (Date) o[2];
			Double valor = (Double) o[3];

			lista.add(new ProdutoQtdDiaValor(produto, qtd, data, valor));
		}
		return lista;
	}
	
	public void salvarLicensa(DataAcesso da) throws ServiceException{
		try {
			if(da.getId()== null) {
				dataAcessoDAO.save(da);
			} else {
				dataAcessoDAO.update(da);
			}
				
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
