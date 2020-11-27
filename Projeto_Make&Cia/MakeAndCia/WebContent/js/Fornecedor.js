class Fornecedor {

	constructor(formulario, tabelaFornecedor) {
		
		this.formulario = document.getElementById(formulario);
        this.tabelaFornecedor = document.getElementById(tabelaFornecedor);
		this.formCadFornecedor();

	}

	// Realiza o cadastro de um fornecedor ao bd
	formCadFornecedor() { 
		
		let btnCadFornecedor = document.querySelector("#btnCadastraFornecedor");
	
		this.formulario.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnCadFornecedor.addEventListener('click', () => {
	
			//valida se todos os campos foram preenchidos
			if ( ! this.formulario.checkValidity() ) {
				return;
			}
			
			var servico = "";
			let id = this.formulario.querySelector("#identCad");
	
			
			if (id.value==0) {
				servico = "InserirFornecedor";
			} else {
				servico = "AlterarFornecedor";
			}
			

			requestAjax(servico, this.formulario).then(
				
				(retorno) => {
					
					console.log(retorno);
			
		        	$.notify({ message: retorno["Mensagem"] },{ type: 'info', placement: { from: 'top', align: 'center' } });
					this.listaFornecedores();
					this.mostraFormularioCad();
					let modal = document.querySelector("#ModalFormFornecedor");
					modal.style.display = "none";
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formulario.querySelector("#btnCancelarFornecedor").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormFornecedor");
			modal.style.display = "none";
		});
		
		
		document.querySelector("#btnNovoFornecedor").addEventListener('click', () => {
			this.mostraFormularioCad();
		});
	
	}

	// Lista fornecedores já cadastrado
	listaFornecedores() {
		
		this.tabelaFornecedor.innerHTML = "";
		
		requestAjax("ListarFornecedor", this.formulario).then(
			
			(retorno) => {
				
				var table = $('#table-fornecedor').DataTable();
				table.clear().draw();
				table.destroy();
				
				retorno.forEach((campo) => {
				
					let tr = document.createElement('tr');
				
					tr.innerHTML += `
						<td>${campo.nome}</td>
						<td>${campo.cnpj}</td>
						<td>${campo.endereco}</td>
						<td>${campo.cidade}</td>
						<td>${campo.telefone}</td>
			            <td>
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastroFornecedor(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastroFornecedor(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaFornecedor.appendChild(tr);
				
				});
				
				$('#table-fornecedor').DataTable( {
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
		this.formulario.reset();
        document.querySelector("#box-cadastro-fornecedor-titulo").innerHTML = "Cadastrar Fornecedor";

		let id = this.formulario.querySelector("#identCad");
		id.value = 0;

		let modal = document.querySelector("#ModalFormFornecedor");
		modal.style.display = "block";
    }

    mostraFormularioEdit() {
        document.querySelector("#box-cadastro-fornecedor-titulo").innerHTML = "Alterar Fornecedor";
        let modal = document.querySelector("#ModalFormFornecedor");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastroFornecedor(idFornecedor) {
	
	fornecedor.mostraFormularioEdit();
	
	let formCadastro = document.querySelector("#form-cadastro-fornecedor");
	let id = formCadastro.querySelector("#identCad");
	id.value = idFornecedor;
	
	requestAjax("ConsultarFornecedor", formCadastro).then(
			
		(retorno) => {
			
			formCadastro.querySelector("#nomeFornecedor").value = retorno.nome
			formCadastro.querySelector("#cnpjFornecedor").value = retorno.cnpj
			formCadastro.querySelector("#enderecoFornecedor").value = retorno.endereco
			formCadastro.querySelector("#cidadeFornecedor").value = retorno.cidade
			formCadastro.querySelector("#telefoneFornecedor").value = retorno.telefone
				
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function excluiCadastroFornecedor(idFornecedor) {
		
	var formCadastro = document.querySelector("#form-cadastro-fornecedor");
	
	let id = formCadastro.querySelector("#identCad");

	id.value = idFornecedor;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir o Fornecedor?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiFornecedor", formCadastro).then(
			
			(retorno) => {
				fornecedor.listaFornecedores();
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


