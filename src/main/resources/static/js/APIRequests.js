let btnAVL = true;
let dadosDaAPI = null;

document.getElementById('uploadForm').addEventListener('submit', function (e) {
    try{
        e.preventDefault();
        const arquivo = document.getElementById('file').files[0];
        const formData = new FormData();
        formData.append('txt',arquivo,'texto.txt');

        document.querySelector(".btnNavTipoArvore").classList.remove("hidden");
        request('http://localhost:8080/arvore/uploadTXT','POST',formData)
    } catch (e){
        alert(e);
    }

});

function request(url, method, formData){
    console.log("VOU REQUISITAR A URL ",url)
    if (method === "DELETE"){
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
        let init;
        if (formData == null){
            init = {
                method: method
            }
        } else {
            init = {
                method: method,
                body: formData
            }
        }
        fetch(url, init)
            .then((resposta) => {
                if (resposta.ok) {
                    return resposta.json();
                } else {
                    throw new Error('Erro na resposta da API');
                }
            })
            .then((data) => {
                dadosDaAPI = data;
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
