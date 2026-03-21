let emExecucao = false;

async function consultar() {

    if (emExecucao) return; // 🔥 impede bug de múltiplos cliques

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

    // 🔥 NÃO APAGA RESULTADO ANTERIOR COMPLETAMENTE
    resultadoBox.textContent += "\n🔎 Nova consulta iniciada...\n";

    let progresso = 0;

    const interval = setInterval(() => {
        progresso += 2;

        if (progresso <= 95) {
            barra.style.width = progresso + "%";
            barra.textContent = progresso + "%";
        }
    }, 200);

    try {
        const res = await fetch("http://localhost:8080/consultar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(lista)
        });

        const dados = await res.json();

        clearInterval(interval);

        barra.style.width = "100%";
        barra.textContent = "100%";

        // 🔥 RESULTADO FICA FIXO
        resultadoBox.textContent += "\n" + dados.join("\n");

    } catch (e) {
        clearInterval(interval);
        resultadoBox.textContent += "\n❌ Erro na consulta";
    }

    emExecucao = false;
}

function cancelar() {

    fetch("http://localhost:8080/cancelar", { method: "POST" });

    document.getElementById("resultado").textContent += "\n⛔ Consulta cancelada";
    document.getElementById("progressBar").style.width = "0%";

    emExecucao = false;
}