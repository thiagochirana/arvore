const getTreeData = () => {
    return    {
        "palavra": "dddddddddddd",
        "nodeEsquerdo": {
            "palavra": "bbbbbbbbbb",
            "nodeEsquerdo": {
                "palavra": "aaaaaaaaaaa",
                "nodeEsquerdo": null,
                "nodeDireito": null
            },
            "nodeDireito": {
                "palavra": "cccccccccccc",
                "nodeEsquerdo": null,
                "nodeDireito": null
            }
        },
        "nodeDireito": {
            "palavra": "ffffffffff",
            "nodeEsquerdo": {
                "palavra": "eeeeeeeeeeee",
                "nodeEsquerdo": null,
                "nodeDireito": null
            },
            "nodeDireito": {
                "palavra": "ggggggggggg",
                "nodeEsquerdo": null,
                "nodeDireito": {
                    "palavra": "hhhhhhhhh",
                    "nodeEsquerdo": null,
                    "nodeDireito": null
                }
            }
        }
}




};


function queryRequest(){
    var input = document.querySelector('input[type="file"]')

    var data = new FormData()
    data.append('file', input.files[0])
    data.append('user', 'hubot')

    fetch('/avatars', {
        method: 'POST',
        body: data
    })
}

const renderTree = (node) => {
    if (node === null || typeof node !== 'object') {
        return ''; // Retorna uma string vazia se o nó for null ou não for um objeto
    }

    const { nodeEsquerdo, nodeDireito, palavra } = node;

    if (palavra === 'null') {
        return ''; // Retorna uma string vazia se o elemento for 'null'
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
};




const main = () => {
    const rootNode = getTreeData();
    const treeDOMElement = document.querySelector(".tree");
    treeDOMElement.innerHTML = renderTree(rootNode);
    console.log("passei aqui");
};

main();