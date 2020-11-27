// Fazer Requisão
function requestAjax(model, formulario) {
	return Promise.resolve(
	  jQuery.ajax({
	    type: "GET",
	    dataType: "json",
	    data: $(formulario).serialize(),
	    url: "http://localhost:8080/MakeAndCia/Controller?service=" + model
	  })
	).finally(function() {
	});
}

// Verificar se esta logado
function verificaLogin() {
	requestAjax("DadosSessao", this.formularioLogin).then(
				
			(retorno) => {
	        	if(!retorno["Condicao"]) {
					window.location.href = "login.html";
				}
        	}
		);
}