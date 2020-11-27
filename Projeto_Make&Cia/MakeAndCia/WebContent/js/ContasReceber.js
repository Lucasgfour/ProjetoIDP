class ContasReceber {

	constructor(formulario, tabelaContasReceber) {
		
		this.formulario = document.getElementById(formulario);
        this.tabelaContasReceber = document.getElementById(tabelaContasReceber);
		this.formCadContasReceber();

	}

	// Realiza o cadastro de uma nova conta a Receber
	formCadContasReceber() { 
		
		let btnCadContasReceber = document.querySelector("#btnCadastraContasReceber");
	
		this.formulario.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnCadContasReceber.addEventListener('click', () => {
			
			var servico = "";
			let id = this.formulario.querySelector("#identCad");
			
			if (id.value==0) {
				servico = "InserirContaReceber";
			} else {
				servico = "AlterarContaReceber";
			}
			

			requestAjax(servico, this.formulario).then(
				
				(retorno) => {
					
					console.log(retorno);
			
		        	$.notify({ message: retorno["Mensagem"] },{ type: 'info', placement: { from: 'top', align: 'center' } });
					this.listaContasReceber();
					this.mostraFormularioCad();
					let modal = document.querySelector("#ModalFormContasReceber");
					modal.style.display = "none";
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formulario.querySelector("#btnCancelarContasReceber").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormContasReceber");
			modal.style.display = "none";
		});
		
		
		document.querySelector("#btnNovaContasReceber").addEventListener('click', () => {
			this.mostraFormularioCad();
		});
	
	}

	// Lista contas a Receber já cadastrada
	listaContasReceber() {
		
		this.tabelaContasReceber.innerHTML = "";
		
		requestAjax("ListarContaReceber", this.formulario).then(
			
			(retorno) => {
				
				var table = $('#table-contasReceber').DataTable();
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
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastroContasReceber(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastroContasReceber(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaContasReceber.appendChild(tr);
				
				});
				
				$('#table-contasReceber').DataTable( {
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
        document.querySelector("#box-cadastro-contasReceber-titulo").innerHTML = "Cadastrar Conta a Receber";

		let id = this.formulario.querySelector("#identCad");
		id.value = 0;

		let modal = document.querySelector("#ModalFormContasReceber");
		modal.style.display = "block";
    }

    mostraFormularioEdit() {
        document.querySelector("#box-cadastro-contasReceber-titulo").innerHTML = "Alterar Conta a Receber";
        let modal = document.querySelector("#ModalFormContasReceber");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastroContasReceber(idConta) {
	
	contasReceber.mostraFormularioEdit();
	
	let formCadastro = document.querySelector("#form-cadastro-contasReceber");
	let id = formCadastro.querySelector("#identCad");
	id.value = idConta;
	
	requestAjax("ConsultarContaReceber", formCadastro).then(
			
		(retorno) => {
		
			formCadastro.querySelector("#descricaoContasReceber").value = retorno.descricao;
			formCadastro.querySelector("#valorContasReceber").value = retorno.valor;
			formCadastro.querySelector("#dataContasReceber").value = formatarData(retorno.dataFormatada);
			formCadastro.querySelector("#statusContasReceber").value = retorno.status
			formCadastro.querySelector("#parcelaContasReceber").value = retorno.parcela;
				
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

function excluiCadastroContasReceber(idConta) {
		
	var formCadastro = document.querySelector("#form-cadastro-contasReceber");
	
	let id = formCadastro.querySelector("#identCad");

	id.value = idConta;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir a Conta a Receber?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiContaReceber", formCadastro).then(
			
			(retorno) => {
				contasReceber.listaContasReceber();
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


