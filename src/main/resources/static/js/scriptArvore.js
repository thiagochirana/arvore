
function renderTree (node) {
    if (node === null || typeof node !== 'object') {
        return '';
    }

    const { nodeEsquerdo, nodeDireito, palavra } = node;

    if (palavra === 'null') {
        return '';
    }

    return `
        <div class="node__element">${palavra}</div>
        ${
        nodeEsquerdo || nodeDireito
        ? `
          <div class="node__bottom-line"></div>
          <div class="node__children">
            ${
            nodeEsquerdo
                ? `
                <div class="node node--left">
                  ${renderTree(nodeEsquerdo)}
                </div>
                `
                : ''
        }
            ${
            nodeDireito
                ? `
                <div class="node node--right">
                  ${renderTree(nodeDireito)}
                </div>
                `
                : ''
        }
          </div>
        `
        : ''
    }
    `;
}


function mostrarArvore(){

    if (dadosDaAPI.tempoDeExecucao == null){

    } else {
        let treeDOMElement = document.querySelector(".tree");
        treeDOMElement.innerHTML = renderTree(arvoreJSON.raiz);

        //Loop para exibir as palavras
        for (let p of arvoreJSON.palavras) {
            mostrarFreqPalavras(p.palavra, p.quantidade)
        }
        console.log(arvoreJSON.isAVL)
        mostrarRelatorio(arvoreJSON.isAVL, arvoreJSON.rotacoes, arvoreJSON.comparacoes, arvoreJSON.tempoDeExecucao)

        console.log("mostrando arvore");
        document.querySelector(".arvore").classList.remove("hidden");
        document.querySelector(".divDosBotoes").classList.add("hidden");
        document.querySelector(".frequenciaPalavras").classList.remove("hidden");
        document.querySelector(".contadoresArvore").classList.remove("hidden");
    }
}

function mostrarFreqPalavras(palavra, qte){
    mostrarRows(palavra,qte,document.querySelector(".listaTable"))
}

function mostrarRelatorio(isAVL,rotacoes,comparacoes,tempoExecucao){
    let body = document.querySelector(".dados")
    if (isAVL){
        mostrarRows("É AVL?","Sim",body)
        mostrarRows("Rotações",rotacoes,body)
    }
    mostrarRows("Comparacões",comparacoes,body)
    mostrarRows("Tempo de Execução",tempoExecucao,body)
}


// private
function mostrarRows(palavra, qte, element){
    let row = document.createElement("tr")
    let cell1 = document.createElement("td")
    let cell2 = document.createElement("td")

    cell1.textContent = palavra;
    cell2.textContent = qte;

    row.append(cell1);
    row.append(cell2);

    element.append(row);
}

function reloadPage(){
    console.log("recarregando...");
    request("http://localhost:8080/arvore","DELETE")
    window.location.reload(true);
}