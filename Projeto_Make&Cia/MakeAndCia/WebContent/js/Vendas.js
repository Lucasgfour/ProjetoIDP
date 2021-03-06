class Vendas {
	
	constructor(tabelaProdutos, formulario, formVenda) {
		this.tabelaProdutos = document.getElementById(tabelaProdutos);
		this.formulario = document.getElementById(formulario);
		this.formularioVenda = document.getElementById(formVenda);
		this.carrinho = {};
		
		this.itensCarrinho();
		this.listaProdutos();
		this.salvarVenda();
	}
	
	
	listaProdutos() {
		
		
		this.tabelaProdutos.innerHTML = "";
			
		requestAjax("ListarProduto", this.formulario).then(
			
			(retorno) => {
				
				var table = $('#table-vendas').DataTable();
				table.clear().draw();
				table.destroy();
				
				retorno.forEach((campo) => {
				
					let tr = document.createElement('tr');
				
					tr.innerHTML += `
						<td>${campo.codigo}</td>
						<td>${campo.descricao}</td>
						<td>${"R$ " + campo.preco_custo}</td>
						<td>${"R$ " + campo.preco_venda}</td>
			            <td>${campo.categoria}</td>
			            <td>${campo.cod_fornecedor}</td>
						<td>${campo.quantidade}</td>
						<td>
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="adicionaAoPreCarrinho(${campo.id})">Adicionar</button>
			            </td> 
		        	`;

					tr.id = campo.id;
		
					this.tabelaProdutos.appendChild(tr);
				
				});
				
				$('#table-produtos-vendas').DataTable( {
				    paging: false,
				    ordering: true,
					destroy: true,
				    retrieve: true,
				    language: {
				     search: "Buscar ",
				     info: ""
				    }
				} );
	
		   	}, 
		
			(error) => {
				console.log(error)
			}
			
		);
	
	};
	
	itensCarrinho() {
		
		let valorTotalCompra = 0;
		
		this.formulario.querySelector("#addCarrinho").addEventListener('click', (event) => {
			
			let valorItemAtual = 0;
			
			let id = this.formulario.querySelector("#identCad");
			let desc = this.formulario.querySelector("#preCarDescricao");
			let preco = this.formulario.querySelector("#preCarPreco-venda");
			let quantidade = this.formulario.querySelector("#preCarQuantidade");
			let estoque = this.formulario.querySelector("#preCarEstoque");
			
			if (id.value == "" || desc.value == "" || preco.value == "" || quantidade.value == "") {
				document.getElementById("preCarAlertaDiv").style.display = 'block';
				$("#preCarAlerta").html("Favor verificar o campo quantidade !!");	
			}
			else if(parseInt(estoque.value, 10) < parseInt(quantidade.value)) {
				document.getElementById("preCarAlertaDiv").style.display = 'block';
				$("#preCarAlerta").html("Quantidade em estoque(" + estoque.value + ") é inferior a solicitada(" + quantidade.value + ")");	
			}
			else {
				
				let modal = document.querySelector("#ModalFormCarrinho");
				modal.style.display = "none";
				this.carrinho[id.value] = {"produto":id.value, "preco":preco.value, "quantidade":quantidade.value, "descricao":desc.value};
				limpaTabelaCarrinho();
				preencheTabelaCarrinho(this.carrinho);
				
				valorItemAtual = (preco.value * quantidade.value);
				
				this.formulario.reset();
				
				valorTotalCompra += valorItemAtual;
				
				document.querySelector("#btnFinalizarVenda").addEventListener('click', () => {
					
					if(this.carrinho.Lenght == 0) {
						$.notify({ message: "Não há produtos no carrinho !"  },{ type: 'info', placement: { from: 'top', align: 'center' } });
					} else {
					
						let modal = document.querySelector("#ModalFinalizaVenda");
						modal.style.display = "block";	
						
						document.querySelector("#valorTotalVenda").value = valorTotalCompra;
						
						document.querySelector("#btnCalcularTroco").addEventListener('click', () => {
							
							let valorRecebido = document.querySelector("#valorRecebido").value;
							let valorCompra = document.querySelector("#valorTotalVenda").value;
							let troco = document.querySelector("#valorTroco");
	
							if (parseFloat(valorCompra) <= parseFloat(valorRecebido)){
								troco.value = (valorRecebido - valorCompra);
							}
							else {
								alert("Valor de pagamento esta abaixo");
							}
							
						
						});
						
						document.querySelector("#btnModalCancelar").addEventListener('click', () => {
							modal.style.display = "none";
						});
					}
				});
				
			};
			
		});
		
		document.querySelector("#btnCancelarModalCarrinho").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormCarrinho");
			modal.style.display = "none";
		})
		
	}
	// --------------------------------------------------------- Salvar Venda ---------------------------------------------------------
	salvarVenda() {	
		// salvar Venda
		document.querySelector("#btnCadastrarVenda").addEventListener('click', () => {

			let modal = document.querySelector("#ModalFinalizaVenda");
			// Transformar carrinho em String
			var produtos = "";
			for(var key in this.carrinho) {
				produtos = produtos + this.carrinho[key]["produto"] + "/" + this.carrinho[key]["quantidade"] + "/" + this.carrinho[key]["preco"] + ";";
			}
			
			$("#vendaProdutos").val(produtos);
			requestAjax("InserirVenda", this.formularioVenda).then(
				
			(retorno) => {
				modal.style.display = "none";
				$.notify({ message: retorno.Mensagem  },{ type: 'info', placement: { from: 'top', align: 'center' } });
				if(retorno.Condicao) {
					limpaTabelaCarrinho();
					this.carrinho = {};
					this.listaProdutos();
				}
				
		   	}, 
		
			(error) => {
				console.log(error);
			}
			
		);
			
		})
	}
	//--------------------------------------------------------------------------------------------------------------------------------
}

function adicionaAoPreCarrinho(idProduto) {
		
	let form = document.querySelector("#form-pre-carrinho");
	let id = form.querySelector("#identCad");
	id.value = idProduto;
	
	requestAjax("ConsultarProduto", form).then(
			
		(retorno) => {
			
			form.querySelector("#preCarDescricao").value = retorno.descricao;
			form.querySelector("#preCarPreco-venda").value = retorno.preco_venda;
			form.querySelector("#preCarEstoque").value = retorno.quantidade;
			
			let modal = document.querySelector("#ModalFormCarrinho");
			modal.style.display = "block";
				
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function excluiItemCarrinho(id) {

	let tabela = document.querySelector("#tabela-itens-vendas");
	
	[...tabela.children].forEach( (tr) => {
		if (tr.id == id){
			delete vendas.carrinho[id.toString()];
			tr.remove();
		}
	});
	
}

function limpaTabelaCarrinho() {

	let tabela = document.querySelector("#tabela-itens-vendas");
	
	[...tabela.children].forEach( (tr) => {
			tr.remove();
	});
}

function preencheTabelaCarrinho(Carrinho) {
	for(var key in Carrinho) {
				let tabelaCarrinho = document.querySelector("#tabela-itens-vendas");
				let tr = document.createElement('tr');
				
				//this.carrinho.push([id.value, desc.value, preco.value, quantidade.value]);
				tr.innerHTML += `
					<td>${Carrinho[key]["descricao"]}</td>
					<td>${"R$ " + Carrinho[key]["preco"]}</td>
					<td>${Carrinho[key]["quantidade"]}</td>
					<td>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiItemCarrinho(${Carrinho[key]["produto"]})">Remover</button>
			        </td> 
		    	`;

				tr.id = Carrinho[key]["produto"];
		
				tabelaCarrinho.appendChild(tr);
	}
	
}
