class ContasPagar {

	constructor(formulario, tabelaContasPagar) {
		
		this.formulario = document.getElementById(formulario);
        this.tabelaContasPagar = document.getElementById(tabelaContasPagar);
		this.formCadContasPagar();

	}

	// Realiza o cadastro de uma nova conta a pagar
	formCadContasPagar() { 
		
		let btnCadContasPagar = document.querySelector("#btnCadastraContasPagar");
	
		this.formulario.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnCadContasPagar.addEventListener('click', () => {
			
			var servico = "";
			let id = this.formulario.querySelector("#identCad");
			
			if (id.value==0) {
				servico = "InserirContaPagar";
			} else {
				servico = "AlterarContaPagar";
			}
			

			requestAjax(servico, this.formulario).then(
				
				(retorno) => {
					
					console.log(retorno);
			
		        	$.notify({ message: retorno["Mensagem"]  },{ type: 'info', placement: { from: 'top', align: 'center' } });
					this.listaContasPagar();
					this.mostraFormularioCad();
					let modal = document.querySelector("#ModalFormContasPagar");
					modal.style.display = "none";
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formulario.querySelector("#btnCancelarContasPagar").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormContasPagar");
			modal.style.display = "none";
		});
		
		
		document.querySelector("#btnNovaContasPagar").addEventListener('click', () => {
			this.mostraFormularioCad();
		});
	
	}

	// Lista contas a pagar já cadastrada
	listaContasPagar() {
		
		this.tabelaContasPagar.innerHTML = "";
		
		requestAjax("ListarContaPagar", this.formulario).then(
			
			(retorno) => {
				
				var table = $('#table-contasPagar').DataTable();
				table.clear().draw();
				table.destroy();
				
				retorno.forEach((campo) => {
				
					let tr = document.createElement('tr');
				
					tr.innerHTML += `
						<td>${campo.descricao}</td>
						<td>${"R$ " + campo.valor}</td>
						<td>${campo.dataFormatada}</td>
						<td>${campo.status == 'A' ? 'Aberta' : 'Fechada'}</td>
						<td>${campo.parcela}</td>
			            <td>
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastroContasPagar(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastroContasPagar(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaContasPagar.appendChild(tr);
				
				});
				
				$('#table-contasPagar').DataTable( {
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
        document.querySelector("#box-cadastro-contasPagar-titulo").innerHTML = "Cadastrar Conta a pagar";

		let id = this.formulario.querySelector("#identCad");
		id.value = 0;

		let modal = document.querySelector("#ModalFormContasPagar");
		modal.style.display = "block";
    }

    mostraFormularioEdit() {
        document.querySelector("#box-cadastro-contasPagar-titulo").innerHTML = "Alterar Conta a pagar";
        let modal = document.querySelector("#ModalFormContasPagar");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastroContasPagar(idConta) {
	
	contasPagar.mostraFormularioEdit();
	
	let formCadastro = document.querySelector("#form-cadastro-contasPagar");
	let id = formCadastro.querySelector("#identCad");
	id.value = idConta;
	
	requestAjax("ConsultarContaPagar", formCadastro).then(
			
		(retorno) => {
		
			formCadastro.querySelector("#descricaoContasPagar").value = retorno.descricao;
			formCadastro.querySelector("#valorContasPagar").value = retorno.valor;
			formCadastro.querySelector("#dataContasPagar").value = formatarData(retorno.dataFormatada);
			formCadastro.querySelector("#statusContasPagar").value = retorno.status
			formCadastro.querySelector("#parcelaContasPagar").value = retorno.parcela;
				
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function formatarData(dataptbr) {
	var vetor = dataptbr.split("/");
	return vetor[2] + '-' + vetor[1] + '-' + vetor[0]; 
}

function excluiCadastroContasPagar(idConta) {
		
	var formCadastro = document.querySelector("#form-cadastro-contasPagar");
	
	let id = formCadastro.querySelector("#identCad");

	id.value = idConta;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir a Conta a pagar?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiContaPagar", formCadastro).then(
			
			(retorno) => {
				contasPagar.listaContasPagar();
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


