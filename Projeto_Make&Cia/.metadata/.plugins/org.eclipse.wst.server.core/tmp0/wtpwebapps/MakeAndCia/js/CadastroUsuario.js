class CadastroUsuario {

	constructor(formularioCadastro, tabelaDeUsuarios) {
		
		this.formularioCad = document.getElementById(formularioCadastro);
        this.tabelaUsuario = document.getElementById(tabelaDeUsuarios);
		this.formCadUsuario();

	}

	// Realiza o cadastro de um usuario ao bd
	formCadUsuario() { 
		
		let btnCadUsuario = document.querySelector("#btnCadastroUser");
	
		this.formularioCad.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		-
		btnCadUsuario.addEventListener('click', () => {
			
			var servico = "";
			let id = this.formularioCad.querySelector("#idUserCad");
			
			if (id.value==0) {
				servico = "InserirUsuario";
				
				let isAdmin = document.querySelector("#isAdmin");
				let admin = document.querySelector("#administrador").checked;
				isAdmin.value = admin;
				
			} else {
				servico = "AlterarUsuario";
				
				let isAdmin = document.querySelector("#isAdmin");
				let admin = document.querySelector("#administrador").checked;
				isAdmin.value = admin;
			}
			

			requestAjax(servico, this.formularioCad).then(
				
				(retorno) => {
					$.notify({ message: retorno["Mensagem"]  },{ type: 'info', placement: { from: 'bottom', align: 'center' } });
					this.formularioCad.reset();
					this.listaUsuarios();
					this.mostraFormularioCadUser();
					let modal = document.querySelector("#ModalFormUsuario");
					modal.style.display = "none";
					
	        	},
	
				(error) => {
					console.log(error);
				}
			);
		});
		
		this.formularioCad.querySelector("#btnCancelarUsuario").addEventListener('click', () => {
			let modal = document.querySelector("#ModalFormUsuario");
			modal.style.display = "none";
		});
		
		document.querySelector("#btnNovoUsuario").addEventListener('click', () => {
			this.mostraFormularioCadUser();
		});
	
	}

	// Lista usuários já cadastrado na tabela de produto
	listaUsuarios() {
		
		this.tabelaUsuario.innerHTML = "";
		
		requestAjax("ListarUsuario", this.formularioCad).then(
			
			(retorno) => {
				
				var table = $('#table-usuarios').DataTable();
				table.clear().draw();
				table.destroy();
				
				retorno.forEach((campo) => {
				
					let tr = document.createElement('tr');
				
					tr.innerHTML += `
						<td>${campo.nome}</td>
						<td>${campo.login}</td>
						<td>${campo.administrador ? 'Sim' : 'Não'}</td>
			            <td>
			            <button type="button" class="btn btn-primary btn-edit btn-xs btn-flat" onclick="editaCadastroUser(${campo.id})">Editar</button>
			            <button type="button" class="btn btn-danger btn-xs btn-flat" onclick="excluiCadastroUser(${campo.id})">Excluir</button>
			            </td> 
						
		        	`;

					tr.id = campo.id;
		
					this.tabelaUsuario.appendChild(tr);
				
				});
				
				$('#table-usuarios').DataTable( {
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

	
	
	// Consulta a quantidade de registro no bd para atualizar o info de usuários
	atualizaContadorUser() {

		requestAjax("ListarUsuario", this.formularioCad).then(
			
			(retorno) => {
			
				document.querySelector("#quantidade-usuarios").innerHTML = retorno.length;
		
		   	}, 
		
			(error) => {
				console.log(error)
			}
			
		);

    };

	mostraFormularioCadUser() {
        document.querySelector("#box-cadastro-usuario-titulo").innerHTML = "Cadastrar Usuário";

		let id = this.formularioCad.querySelector("#idUserCad");
		id.value = 0;
		
		let modal = document.querySelector("#ModalFormUsuario");
		modal.style.display = "block";
    }

    mostraFormularioEditUser() {
        document.querySelector("#box-cadastro-usuario-titulo").innerHTML = "Alterar Usuário";

		let modal = document.querySelector("#ModalFormUsuario");
		modal.style.display = "block";
    }	

}

// Adiciona os dados no formulario de edição e altera os valores
function editaCadastroUser(idUsuario) {
	
	cadastroUsuario.mostraFormularioEditUser();
	
	let formCadastro = document.querySelector("#form-cadastro-usuario");
	let id = formCadastro.querySelector("#idUserCad");
	id.value = idUsuario;
	
	requestAjax("ConsultarUsuario", formCadastro).then(
			
		(retorno) => {
			
			formCadastro.querySelector("#nome").value = retorno.nome
			formCadastro.querySelector("#login").value = retorno.login
			formCadastro.querySelector("#senha").value = retorno.senha
			formCadastro.querySelector("#administrador").checked = retorno.administrador
			formCadastro.querySelector("#isAdmin").value = retorno.administrador
	
	   	}, 
	
		(error) => {
			console.log(error);
		}
			
	);

}

function excluiCadastroUser(idUsuario) {
		
	var formCadastro = document.querySelector("#form-cadastro-usuario");
	
	let id = formCadastro.querySelector("#idUserCad");

	id.value = idUsuario;
	
	let modal = document.querySelector("#modalConfirm");
	modal.style.display = "block";
	modal.querySelector("#pModal").innerHTML = "Deseja excluir o Usuário?";
	modal.querySelector("#modalBotoes").innerHTML = '<button type="button" class="btn btn-primary" id="btnModalSim">Sim</button>'
						+ '<button type="button" class="btn btn-secondary" data-dismiss="modal"'
						+ 'id="btnModalNao">Não</button>';
	
	let opSim = modal.querySelector("#btnModalSim");
	opSim.addEventListener('click', () => {
	
		requestAjax("ExcluiUsuario", formCadastro).then(
			
			(retorno) => {
				cadastroUsuario.listaUsuarios();
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


