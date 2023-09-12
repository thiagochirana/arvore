const getTreeData = () => {
    return {
        palavra: "raiz",
        nodeEsquerdo: {
            palavra: "chirana gay",
            nodeEsquerdo: {
                palavra: 'null',
            },
            nodeDireito: {
                palavra: 'test123',
            },
        },
        nodeDireito: {
            palavra: 'test13',
            nodeEsquerdo: {
                palavra: 'test132',
                nodeEsquerdo: {
                    palavra: 'test1321',
                    nodeEsquerdo: {
                        palavra: 'test13211',
                    },
                },
            },
            nodeDireito: {
                palavra: 'test133',
                nodeEsquerdo: {
                    palavra: 'test1332',
                },
                nodeDireito: {
                    palavra: 'test1333',
                    nodeEsquerdo: {
                        palavra: 'test1321',
                        nodeEsquerdo: {
                            palavra: 'test13211',
                        },
                    },
                    nodeDireito: {
                        palavra: 'test1321',
                        nodeDireito: {
                            palavra: 'test13211',
                        },
                    },
                },
            },
        },
    };
};

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