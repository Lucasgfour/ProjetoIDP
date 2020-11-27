verificaLogin();

let cadastroProduto = new CadastroProduto("form-cadastro-produto","tabela-produtos");
let cadastroUsuario = new CadastroUsuario("form-cadastro-usuario","tabela-usuarios");
let vendas = new Vendas("tabela-produtos-carrinho", "form-pre-carrinho", "formularioVenda");
let categoria = new Categoria("form-cadastro-categoria", "tabela-categoria");
let fornecedor = new Fornecedor("form-cadastro-fornecedor", "tabela-fornecedor");
let contasPagar = new ContasPagar("form-cadastro-contasPagar", "tabela-contasPagar");
let contasReceber = new ContasReceber("form-cadastro-contasReceber", "tabela-contasReceber");

document.querySelector("#btnCadastroProduto").addEventListener('click', () => {
    mostraPainelProduto();
})

document.querySelector("#btnCadastroUsuario").addEventListener('click', () => {
    mostraPainelUsuario();
})

document.querySelector("#btnVendas").addEventListener('click', () => {
    mostraPainelVendas();
})

document.querySelector("#btnCategoria").addEventListener('click', () => {
    mostraPainelCategoria();
})

document.querySelector("#btnFornecedor").addEventListener('click', () => {
    mostraPainelFornecedor();
})

document.querySelector("#btnContasPagar").addEventListener('click', () => {
    mostraPainelContasPagar();
})

document.querySelector("#btnContasReceber").addEventListener('click', () => {
    mostraPainelContasReceber();
})

document.querySelector("#show_cadastro-usuario").addEventListener('submit', (e) => {
    e.preventDefault();
})




function mostraPainelVendas() {
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
    document.querySelector("#show_vendas").style.display = "block";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "none";
	vendas.listaProdutos();
}

function mostraPainelUsuario() {
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "block";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "none";
	cadastroUsuario.listaUsuarios();
    
}

function mostraPainelProduto() {
    document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-produto").style.display = "block";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "none";
    cadastroProduto.listaProdutos();
}

function mostraPainelCategoria() {
	document.querySelector("#show_categoria").style.display = "block";
	document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "none";
	categoria.listaCategorias();
}

function mostraPainelFornecedor() {
	document.querySelector("#show_fornecedor").style.display = "block";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "none";
	fornecedor.listaFornecedores();
}

function mostraPainelContasPagar() {
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "block";
	document.querySelector("#show-ContasReceber").style.display = "none";
	contasPagar.listaContasPagar();
}

function mostraPainelContasReceber() {
	document.querySelector("#show_fornecedor").style.display = "none";
	document.querySelector("#show_categoria").style.display = "none";
	document.querySelector("#show_vendas").style.display = "none";
    document.querySelector("#show_cadastro-produto").style.display = "none";
    document.querySelector("#show_cadastro-usuario").style.display = "none";
	document.querySelector("#show-ContasPagar").style.display = "none";
	document.querySelector("#show-ContasReceber").style.display = "block";
	contasReceber.listaContasReceber();
}