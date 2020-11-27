class Login {

	constructor(formularioLoginId) {
		this.formularioLogin = document.getElementById(formularioLoginId);
		this.formLogin();
	}
	
	formLogin() {
		
		let btnEntrar = document.querySelector("#btnEntrar");
		
		this.formularioLogin.addEventListener('submit', (event) => {
			event.preventDefault();
		});
		
		btnEntrar.addEventListener('click', () => {
			
			requestAjax("IniciarSessao", this.formularioLogin).then(
				
				(retorno) => {
					
		        	if(retorno["Condicao"]) {
						$.notify({ message: 'Logado com sucesso ... Redirecionando'  },{ type: 'success', placement: { from: 'bottom', align: 'center' } });
						window.location.href = "index.html";
					} else {
						$.notify({ message: retorno["Mensagem"]  },{ type: 'danger', placement: { from: 'bottom', align: 'center' } });
					}
					
	        	},
	
				(error) => {
					$.notify({ message: error  },{ type: 'danger', placement: { from: 'bottom', align: 'center' } });
				}
			);
		});
	}
	
}