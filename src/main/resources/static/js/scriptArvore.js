
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


function exibirArvore(){
    request("http://localhost:8080/arvore/buscarInfo","GET",null);

    console.log(dadosDaAPI)

    let divTree = document.querySelector(".tree");
    let h5 = document.createElement("h5");
    h5.textContent = "Arquivo em processamento, por favor aguarde e tente novamente daqui a pouco..."

    if (dadosDaAPI == null){
        divTree.append(h5);
    } else {
        h5.remove();
        if (btnAVL){
            divTree.innerHTML = renderTree(dadosDaAPI.arvoreAVL.raizArvoreAVL);
        } else {
            divTree.innerHTML = renderTree(dadosDaAPI.arvoreBinaria.raizArvoreBinaria);
        }

        //Loop para exibir as palavras
        for (let p of dadosDaAPI.frequenciaPalavras) {
            mostrarFreqPalavras(p.palavra, p.quantidade)
        }

        mostrarRelatorioVetor(dadosDaAPI.vetorBinario.tempoExecucaoBuscaBinaria,
                                dadosDaAPI.tempoLeituraArquivo,
                                dadosDaAPI.vetorBinario.tempoOrdenacaoVetor,
                                dadosDaAPI.vetorBinario.contadorComparacoesBinaria)

        mostrarRelatorioBinario(dadosDaAPI.arvoreBinaria.comparacoes, dadosDaAPI.arvoreBinaria.tempoDeExecucao)

        mostrarRelatorioAVL(dadosDaAPI.arvoreAVL.rotacoes,
                            dadosDaAPI.arvoreAVL.comparacoes,
                            dadosDaAPI.arvoreAVL.tempoDeExecucao)

        console.log("mostrando arvore");
        document.querySelector(".arvore").classList.remove("hidden");
        document.querySelector(".divDosBotoes").classList.add("hidden");
        document.querySelector(".frequenciaPalavras").classList.remove("hidden");
        document.querySelector(".arvoreAVL").classList.remove("hidden");
        document.querySelector(".arvoreBinaria").classList.remove("hidden");
        document.querySelector(".vetorBinario").classList.remove("hidden");
    }
}

function mostrarFreqPalavras(palavra, qte){
    mostrarRows(palavra,qte,document.querySelector(".listaTable"))
}

function mostrarRelatorioAVL(rotacoes, comparacoes, tempoExecucao){
    let body = document.querySelector(".dadosArvAVL");
    mostrarRows("Rotacões",rotacoes,body)
    mostrarRows("Comparacões",comparacoes,body)
    mostrarRows("Tempo de Execução",tempoExecucao,body)
}

function mostrarRelatorioBinario(comparacoes, tempoExecucao){
    let body = document.querySelector(".dadosArvBinario");
    mostrarRows("Comparacões",comparacoes,body)
    mostrarRows("Tempo de Execução",tempoExecucao,body)
}

function mostrarRelatorioVetor(tempoExecucao, tempoLeitura, tempoOrdenacao, comparacoesBusca){
    let body = document.querySelector(".dadosVetorBinario");
    mostrarRows("Tempo de Execução Busca Binária",tempoExecucao,body)
    mostrarRows("Tempo de leitura arquivo",tempoLeitura,body)
    mostrarRows("Tempo de ordenação do vetor",tempoOrdenacao,body)
    mostrarRows("Comparações busca binária",comparacoesBusca,body)
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