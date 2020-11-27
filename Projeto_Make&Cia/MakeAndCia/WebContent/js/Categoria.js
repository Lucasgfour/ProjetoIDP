class Categoria {

	constructor(formulario, tabelaCategoria) {
		
		this.formulario = document.getElementById(formulario);
        this.tabelaCategoria = document.getElementById(tabelaCategoria);
		this.formCadCategoria();

	}

	// Realiza o cadastro de uma categoria ao bd
	formCadCategoria() { 
		
		let btnCadCategoria = document.querySelector("#btnCadastraCategoria");
	
		this.formulario.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnCadCategoria.addEventListener('click', () => {
			
			//valida se todos os campos foram preenchidos
			if ( ! this.formulario.checkValidity() ) {
				return;
			}
			
			var servico = "";
			let id = this.formulario.querySelector("#identCad");
			
			if (id.value==0) {
				servico = "InserirCategoria";
			} else {
				servico = "AlterarCategoria";
			}
			

			requestAjax(servico, this.formulario).then(
				
				(retorno) => {
			
		        	$.notify({ message: retorno["Mensagem"] },{ type: 'info', placement: { from: 'top', align: 'center' } });
					this.listaCategorias();
					this.mostraFormularioCad();
					let modal = document.querySelector("#ModalFormCategoria");
					modal.style.display = "none";
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formulario.querySelector("#btnCancelarCategoria").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormCategoria");
			modal.style.display = "none";
		});
		
		
		document.querySelector("#btnNovaCategoria").addEventListener('click', () => {
			this.mostraFormularioCad();
		});
	
	}

	// Lista categorias já cadastrado na tabela de produto
	listaCategorias() {
		
		var table = $('#table-categoria').DataTable();
		table.clear().draw();
		table.destroy();
		
		requestAjax("ListarCategoria", this.formulario).then(
			
			(retorno) => {
				
				this.tabelaCategoria.innerHTML = "";
				
				retorno.forEach((campo) => {
				
					let tr = document.createElement('tr');
				
					tr.innerHTML += `
						<td>${campo.id}</td>
						<td>${campo.nome}</td>
						<td>${campo.descricao}</td>
			            <td>
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastroCategoria(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastroCategoria(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaCategoria.appendChild(tr);
				
				});
				
				$('#table-categoria').DataTable( {
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
        document.querySelector("#box-cadastro-categoria-titulo").innerHTML = "Cadastrar Categoria";

		let id = this.formulario.querySelector("#identCad");
		id.value = 0;

		let modal = document.querySelector("#ModalFormCategoria");
		modal.style.display = "block";
    }

    mostraFormularioEdit() {
        document.querySelector("#box-cadastro-categoria-titulo").innerHTML = "Alterar Categoria";
        let modal = document.querySelector("#ModalFormCategoria");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastroCategoria(idCategoria) {
	
	categoria.mostraFormularioEdit();
	
	let formCadastro = document.querySelector("#form-cadastro-categoria");
	let id = formCadastro.querySelector("#identCad");
	id.value = idCategoria;
	
	requestAjax("ConsultarCategoria", formCadastro).then(
			
		(retorno) => {
			
			formCadastro.querySelector("#nomeCategoria").value = retorno.nome
			formCadastro.querySelector("#descricaoCategoria").value = retorno.descricao
				
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function excluiCadastroCategoria(idCategoria) {
		
	var formCadastro = document.querySelector("#form-cadastro-categoria");
	
	let id = formCadastro.querySelector("#identCad");

	id.value = idCategoria;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir a Categoria?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiCategoria", formCadastro).then(
			
			(retorno) => {
				categoria.listaCategorias();
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


