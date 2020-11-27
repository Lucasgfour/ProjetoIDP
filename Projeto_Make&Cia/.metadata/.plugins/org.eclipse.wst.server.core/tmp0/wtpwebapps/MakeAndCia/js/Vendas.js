class Vendas {
	
	constructor(tabelaProdutos, formulario) {
		this.tabelaProdutos = document.getElementById(tabelaProdutos);
		this.formulario = document.getElementById(formulario);
		
		this.itensCarrinho();
		this.listaProdutos();
	}
	
	
	listaProdutos() {
		
		this.tabelaProdutos.innerHTML = "<h4>Sincronizando os dados...</h4>";
			
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
			
			if (id.value == "" || desc.value == "" || preco.value == "" || quantidade.value == "") {
				alert("Campo quantidade esta vazio!");
			}
			else {
				
				let modal = document.querySelector("#ModalFormCarrinho");
				modal.style.display = "none";
			
				let tabelaCarrinho = document.querySelector("#tabela-itens-vendas");
				let tr = document.createElement('tr');
						
				tr.innerHTML += `
					<td>${desc.value}</td>
					<td>${"R$ " + preco.value}</td>
					<td>${quantidade.value}</td>
					<td>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiItemCarrinho(${id.value})">Remover</button>
			        </td> 
		    	`;
		
				tr.id = id.value;
		
				tabelaCarrinho.appendChild(tr);
				
				valorItemAtual = (preco.value * quantidade.value);
				
				this.formulario.reset();
				
				valorTotalCompra += valorItemAtual;
				
				document.querySelector("#btnFinalizarVenda").addEventListener('click', () => {
					
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
					
				});
				
			};
			
		});
		
		document.querySelector("#btnCancelarModalCarrinho").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormCarrinho");
			modal.style.display = "none";
		})
		
	}
	
}

function adicionaAoPreCarrinho(idProduto) {
		
	let form = document.querySelector("#form-pre-carrinho");
	let id = form.querySelector("#identCad");
	id.value = idProduto;
	
	requestAjax("ConsultarProduto", form).then(
			
		(retorno) => {
			
			form.querySelector("#preCarDescricao").value = retorno.descricao;
			form.querySelector("#preCarPreco-venda").value = retorno.preco_venda;
			
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
			tr.remove();
		}
	});
		
}