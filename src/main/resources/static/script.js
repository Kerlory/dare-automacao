function adicionarResultado(guia, situacao) {

    const container = document.getElementById("resultado");

    const linha = document.createElement("div");
    linha.className = "linha";

    const cardGuia = document.createElement("div");
    cardGuia.className = "card guia";
    cardGuia.innerText = guia;

    const cardSituacao = document.createElement("div");
    cardSituacao.className = "card situacao";

    const texto = document.createElement("span");
    texto.innerText = situacao;

    if (situacao.includes("PAGO")) {
        cardSituacao.style.background = "#00c853";
    } else {
        cardSituacao.style.background = "#d50000";
    }

    cardSituacao.appendChild(texto);

    linha.appendChild(cardGuia);
    linha.appendChild(cardSituacao);

    container.appendChild(linha);
}