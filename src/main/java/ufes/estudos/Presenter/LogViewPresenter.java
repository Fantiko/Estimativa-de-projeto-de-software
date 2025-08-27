package ufes.estudos.Presenter;

import ufes.estudos.Views.TelaLogView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogViewPresenter {
    private final TelaLogView view;

    public LogViewPresenter(TelaLogView view) {
        this.view = view;
        carregarLog();
    }

    private void carregarLog() {
        try {
            // Por padrão, a biblioteca salva em "log.<formato>"
            // Vamos assumir TXT por enquanto
            String conteudo = Files.readString(Paths.get("log.txt"));
            view.setLogText(conteudo);
        } catch (IOException e) {
            view.setLogText("Erro ao carregar o arquivo de log: " + e.getMessage() +
                    "\n\nVerifique se já foi realizada alguma operação para gerar o arquivo.");
        }
    }
}