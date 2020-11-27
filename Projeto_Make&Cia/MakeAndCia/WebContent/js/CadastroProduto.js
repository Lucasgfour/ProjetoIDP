class CadastroProduto {

	constructor(formularioCadastro, tabelaDeProdutos) {
		
		this.formularioCad = document.getElementById(formularioCadastro);
        this.tabelaProdutos = document.getElementById(tabelaDeProdutos);
		this.formCadProduto();

	}

	// Realiza o cadastro de um produto ao bd
	formCadProduto() { 
		
		let btnCadProduto = document.querySelector("#btnCadastraProduto");
	
		this.formularioCad.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnCadProduto.addEventListener('click', () => {
			
			var servico = "";
			let id = this.formularioCad.querySelector("#identCad");
			
			if (id.value==0) {
				servico = "InserirProduto";
			} else {
				servico = "AlterarProduto";
			}
			

			requestAjax(servico, this.formularioCad).then(
				
				(retorno) => {
					
		        	$.notify({ message: retorno  },{ type: 'info', placement: { from: 'top', align: 'center' } });
					this.listaProdutos();
					//this.mostraFormularioCad();
					let modal = document.querySelector("#ModalFormProduto");
					modal.style.display = "none";
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formularioCad.querySelector("#btnCancelarProduto").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormProduto");
			modal.style.display = "none";
		});
		
		
		document.querySelector("#btnNovoProduto").addEventListener('click', () => {
			this.mostraFormularioCad();
		});
	
	}

	// Lista produtos já cadastrado na tabela de produto
	listaProdutos() {
		
		this.tabelaProdutos.innerHTML = "";
		
		requestAjax("ListarProduto", this.formularioCad).then(
			
			(retorno) => {
				
				var table = $('#table-produtos').DataTable();
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
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastro(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastro(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaProdutos.appendChild(tr);
				
				});
				
				var table = $('#table-produtos').DataTable( {
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

	mostraFormularioCad() {
		this.formularioCad.reset();
        document.querySelector("#box-cadastro-produto-titulo").innerHTML = "Cadastrar Produto";

		let id = this.formularioCad.querySelector("#identCad");
		id.value = 0;

		let modal = document.querySelector("#ModalFormProduto");
		modal.style.display = "block";
    }

    mostraFormularioEdit() {
        document.querySelector("#box-cadastro-produto-titulo").innerHTML = "Alterar Produto";
        let modal = document.querySelector("#ModalFormProduto");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastro(idProduto) {
	
	cadastroProduto.mostraFormularioEdit();
	
	let formCadastro = document.querySelector("#form-cadastro-produto");
	let id = formCadastro.querySelector("#identCad");
	id.value = idProduto;
	
	requestAjax("ConsultarProduto", formCadastro).then(
			
		(retorno) => {
			
			formCadastro.querySelector("#codigo").value = retorno.codigo
			formCadastro.querySelector("#descricao").value = retorno.descricao
			formCadastro.querySelector("#preco-custo").value = retorno.preco_custo
			formCadastro.querySelector("#preco-venda").value = retorno.preco_venda
			formCadastro.querySelector("#categoria").value = retorno.categoria
			formCadastro.querySelector("#fornecedor").value = retorno.cod_fornecedor
			formCadastro.querySelector("#qtd-estoque").value = retorno.quantidade
				
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function excluiCadastro(idProduto) {
		
	var formCadastro = document.querySelector("#form-cadastro-produto");
	
	let id = formCadastro.querySelector("#identCad");

	id.value = idProduto;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir o produto?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiProduto", formCadastro).then(
			
			(retorno) => {
				cadastroProduto.listaProdutos();
			}, 
		
			(error) => {
				console.log(error);
			}
				
		);
		
		modal.style.display = "none";	
	});
		
	let opNao = modal.querySelector("#btnModalNao");
	opNao.addEventListener('click', () => {
		modal.style.display = "none"
	});
}


