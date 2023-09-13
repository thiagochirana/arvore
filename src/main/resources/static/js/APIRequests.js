document.getElementById('uploadForm').addEventListener('submit', function (e) {
    try{
        e.preventDefault();
        const arquivo = document.getElementById('file').files[0];
        const formData = new FormData();
        formData.append('txt',arquivo,'texto.txt');

        const headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');

        request('http://localhost:8080/arvore/avl/uploadTXT','POST',formData,headers)
    } catch (e){
        alert(e);
    }

});

function request(url, method, formData,header){
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
