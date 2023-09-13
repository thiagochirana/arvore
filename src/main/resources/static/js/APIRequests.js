let btnAVL = true;

document.getElementById('uploadForm').addEventListener('submit', function (e) {
    try{
        e.preventDefault();
        let url = 'http://localhost:8080/arvore/simples/uploadTXT';
        if (btnAVL){
            url = 'http://localhost:8080/arvore/avl/uploadTXT';
        }
        const arquivo = document.getElementById('file').files[0];
        const formData = new FormData();
        formData.append('txt',arquivo,'texto.txt');

        request(url,'POST',formData)
    } catch (e){
        alert(e);
    }

});

function request(url, method, formData){
    console.log("VOU REQUISITAR A URL ",url)
    if (method == "DELETE"){
        fetch(url, {
            method: method
        })
            .then((resposta) => {
                if (resposta.ok) {
                    alert("Arvores foram limpas")
                } else {
                    throw new Error('Erro na resposta da API');
                }
            })
    } else {
        fetch(url, {
            method: method,
            body: formData
        })
            .then((resposta) => {
                if (resposta.ok) {
                    return resposta.json();
                } else {
                    throw new Error('Erro na resposta da API');
                }
            })
            .then((data) => {
                mostrarArvore(data)
                return data;
            })
    }
}


function selectedTreeType(slc){
    if (slc == 1){
        btnAVL = true;
    } else {
        btnAVL = false;
    }
}
