let emExecucao = false;

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

    // 🔥 CHAMADA CORRETA
    fetch("/consultar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(lista)
    }).then(res => res.json())
        .then(dados => {

            dados.forEach(linha => {
                const partes = linha.split(" - ");
                adicionarResultado(partes[0], partes[1]);
            });

        });

    // progresso em tempo real
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