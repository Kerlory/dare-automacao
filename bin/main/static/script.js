let emExecucao = false;

// 🔥 GARANTE QUE O BOTÃO FUNCIONE
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btnConsultar")
        .addEventListener("click", consultar);
});

async function consultar() {

    if (emExecucao) return;

    emExecucao = true;

    const texto = document.getElementById("codigos").value;

    const lista = texto
        .split("\n")
        .map(l => l.trim())
        .filter(l => l !== "");

    const barra = document.getElementById("progressBar");
    const resultadoBox = document.getElementById("resultado");

    barra.style.width = "0%";
    barra.textContent = "0%";

    resultadoBox.innerHTML = "";

    try {

        // 🔥 CHAMADA CORRETA (Render)
        const resposta = await fetch("/consultar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(lista)
        });

        const dados = await resposta.json();

        dados.forEach(linha => {
            const partes = linha.split(" - ");
            adicionarResultado(partes[0], partes[1]);
        });

    } catch (e) {
        alert("Erro ao consultar servidor 🚨");
        console.error(e);
    }

    // 🔥 PROGRESSO
    const interval = setInterval(async () => {

        const res = await fetch("/status");
        const dados = await res.json();

        if (dados.total === 0) return;

        let progresso = Math.floor((dados.processadas / dados.total) * 100);

        barra.style.width = progresso + "%";
        barra.textContent = progresso + "%";

        if (!dados.rodando) {
            clearInterval(interval);
            barra.style.width = "100%";
            barra.textContent = "100%";
            emExecucao = false;
        }

    }, 500);
}

// 🔥 ESSA FUNÇÃO QUE TAVA FALTANDO (ERRO RESOLVIDO)
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

    const botaoCopiar = document.createElement("button");
    botaoCopiar.innerText = "📋";
    botaoCopiar.className = "btn-copy";

    botaoCopiar.onclick = () => {
        navigator.clipboard.writeText(situacao);
        botaoCopiar.innerText = "✔";
        setTimeout(() => botaoCopiar.innerText = "📋", 1000);
    };

    cardSituacao.appendChild(texto);
    cardSituacao.appendChild(botaoCopiar);

    // 🔥 TRATAMENTO DAS SITUAÇÕES
    if (
        situacao.includes("PAGO") ||
        situacao.includes("BAIXA PROVISÓRIA")
    ) {
        cardSituacao.style.background = "#00c853";
    } else {
        cardSituacao.style.background = "#d50000";
    }

    linha.appendChild(cardGuia);
    linha.appendChild(cardSituacao);

    container.appendChild(linha);
}