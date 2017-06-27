function somenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else  return false;
    }
}

function imprimirPedido() {
	var div = document.getElementById('form:imprimir').innerHTML;
    page = window.open('about:blank');
    conteudo = "<html><head><title></title></head><body>" + div + "</body>";
    page.document.write(conteudo);
    page.window.print();
    page.window.close;
}
 
function imprimirPedidoPopup(){
	var conteudo = document.getElementById('form:imprimir').innerHTML;  
    var page = window.open();  
    page.document.write(conteudo);  
    page.print();  
    page.close();
}

function imprimirPedidoReload() {
    var divElements = document.getElementById('form:imprimir').innerHTML;
    var pagina = document.body.innerHTML;
    document.body.innerHTML = "<html><head><title></title></head><body>" + divElements + "</body>";
    window.print();
    document.body.innerHTML = pagina;
}
