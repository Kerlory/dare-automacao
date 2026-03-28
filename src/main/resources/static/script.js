let emExecucao = false;

// 🔥 GARANTE QUE O JS CARREGOU
document.addEventListener("DOMContentLoaded", () => {

    console.log("JS carregado ✅");

    const btn = document.getElementById("btnConsultar");

    if (btn) {
        btn.addEventListener("click", consultar);
    } else {
        console.error("Botão não encontrado ❌");
    }

});

async function consultar() {

    console.log("CLICOU 🔥");

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

        const resposta = await fetch("/consultar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(lista)
        });

        const dados = await resposta.json();

        console.log("Resposta servidor:", dados);

        // 🔥 proteção caso backend envie erro
        if (!Array.isArray(dados)) {
            console.error("Servidor retornou erro:", dados);
            alert("Erro no servidor 🚨");
            emExecucao = false;
            return;
        }

        dados.forEach(linha => {

            if (!linha) return;

            const partes = linha.split(" - ");

            const guia = partes[0] || "DESCONHECIDO";
            const situacao = partes[1] || "SEM STATUS";

            adicionarResultado(guia, situacao);

        });

    } catch (erro) {

        console.error("Erro na requisição:", erro);
        alert("Erro na requisição 🚨");

    }

    // 🔥 PROGRESSO
    const interval = setInterval(async () => {

        try {

            const res = await fetch("/status");
            const dados = await res.json();

            if (!dados || dados.total === 0) return;

            let progresso = Math.floor((dados.processadas / dados.total) * 100);

            barra.style.width = progresso + "%";
            barra.textContent = progresso + "%";

            if (!dados.rodando) {

                clearInterval(interval);

                barra.style.width = "100%";
                barra.textContent = "100%";

                emExecucao = false;

            }

        } catch (e) {
            console.error("Erro status:", e);
        }

    }, 500);
}


// 🔥 MOSTRAR RESULTADOS
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
    };

    cardSituacao.appendChild(texto);
    cardSituacao.appendChild(botaoCopiar);

    // 🔥 PROTEÇÃO CONTRA undefined
    const status = (situacao || "").toUpperCase();

    if (status.includes("PAGO") || status.includes("BAIXA PROVISÓRIA")) {

        cardSituacao.style.background = "#00c853";

    } else if (status.includes("NAO") || status.includes("NÃO")) {

        cardSituacao.style.background = "#d50000";

    } else {

        cardSituacao.style.background = "#ff9800";

    }

    linha.appendChild(cardGuia);
    linha.appendChild(cardSituacao);

    container.appendChild(linha);
}