const getTreeData = () => {
    return {
        element: "raiz",
        left: {
            element: "chirana gay",
            left: {
                element: 'null',
            },
            right: {
                element: 'test123',
            },
        },
        right: {
            element: 'test13',
            left: {
                element: 'test132',
                left: {
                    element: 'test1321',
                    left: {
                        element: 'test13211',
                    },
                },
            },
            right: {
                element: 'test133',
                left: {
                    element: 'test1332',
                },
                right: {
                    element: 'test1333',
                    left: {
                        element: 'test1321',
                        left: {
                            element: 'test13211',
                        },
                    },
                    right: {
                        element: 'test1321',
                        right: {
                            element: 'test13211',
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

    const { left, right, element } = node;

    if (element === 'null') {
        return ''; // Retorna uma string vazia se o elemento for 'null'
    }

    return `
        <div class="node__element">${element}</div>
        ${
        left || right
            ? `
              <div class="node__bottom-line"></div>
              <div class="node__children">
                ${
                left
                    ? `
                    <div class="node node--left">
                      ${renderTree(left)}
                    </div>
                    `
                    : ''
            }
                ${
                right
                    ? `
                    <div class="node node--right">
                      ${renderTree(right)}
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