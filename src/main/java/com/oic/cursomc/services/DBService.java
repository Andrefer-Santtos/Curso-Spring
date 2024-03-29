package com.oic.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oic.cursomc.domain.Categoria;
import com.oic.cursomc.domain.Cidade;
import com.oic.cursomc.domain.Cliente;
import com.oic.cursomc.domain.Endereco;
import com.oic.cursomc.domain.Estado;
import com.oic.cursomc.domain.ItemPedido;
import com.oic.cursomc.domain.Pagamento;
import com.oic.cursomc.domain.PagamentoComBoleto;
import com.oic.cursomc.domain.PagamentoComCartao;
import com.oic.cursomc.domain.Pedido;
import com.oic.cursomc.domain.Produto;
import com.oic.cursomc.domain.enums.EstadoPagamento;
import com.oic.cursomc.domain.enums.Perfil;
import com.oic.cursomc.domain.enums.TipoCliente;
import com.oic.cursomc.repositories.CategoriaRepository;
import com.oic.cursomc.repositories.CidadeRepository;
import com.oic.cursomc.repositories.ClienteRepository;
import com.oic.cursomc.repositories.EnderecoRepository;
import com.oic.cursomc.repositories.EstadoRepository;
import com.oic.cursomc.repositories.ItemPedidoRepository;
import com.oic.cursomc.repositories.PagamentoRepository;
import com.oic.cursomc.repositories.PedidoRepository;
import com.oic.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private CategoriaRepository categoriarepository;
	@Autowired
	private ProdutoRepository produtorepository;
	@Autowired
	private EstadoRepository estadorepository;
	@Autowired
	private CidadeRepository cidaderepository;
	@Autowired
	private ClienteRepository clienterepository;
	@Autowired
	private EnderecoRepository enderecorepository;
	@Autowired
	private PedidoRepository pedidorepository;
	@Autowired
	private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itempedidorepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null,"Deck Cards");
		Categoria cat2 = new Categoria(null,"Puzzles");
		Categoria cat3 = new Categoria(null,"Magic Clothes");
		Categoria cat4 = new Categoria(null,"Wands");
		Categoria cat5 = new Categoria(null,"Magic Books");
		
		Produto p1 = new Produto(null,"Noc", 2000.00);
		Produto p2 = new Produto(null,"Bicycle", 800.00);
		Produto p3 = new Produto(null,"Monarchs", 80.00);
		Produto p4 = new Produto(null,"Cartola", 300.00);
		Produto p5 = new Produto(null,"Capa da Invisibilidade", 50.00);
		Produto p6 = new Produto(null,"Varinha das Varinhas", 200.00);
		Produto p7 = new Produto(null,"Magic Ring", 1200.00);
		Produto p8 = new Produto(null,"Fire Book", 800.00);
		Produto p9 = new Produto(null,"Ice Book", 100.00);
		Produto p10 = new Produto(null,"Air Book", 180.00);
		Produto p11 = new Produto(null,"Storm Book", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5));
		produtorepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadorepository.saveAll(Arrays.asList(est1, est2));
		cidaderepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "André Santos", "email", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Cliente cli2 = new Cliente(null, "Adriano Santos", "adriano@gmail.com", "61446398021", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.getTelefones().addAll(Arrays.asList("52863327","12348387"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106", "null", "Centro", "32177789", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienterepository.saveAll(Arrays.asList(cli1, cli2));
		enderecorepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidorepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentorepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidorepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
