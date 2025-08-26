package ufes.estudos.Logger;

import org.example.LogAdapter.LogAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroLogger {

    private final LogAdapter adapter;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static int contadorLogs = 0;

    public RegistroLogger(LogAdapter adapter) {
        this.adapter = adapter;
    }

    public void logSucesso(String idc, String operacao, String nome, String usuario) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(DATE_FORMATTER);
        String hora = agora.format(TIME_FORMATTER);

        String mensagemFormatada = String.format(
                "%s<%s>: %s, (%s, %s, e %s)",
                idc, operacao, nome, data, hora, usuario
        );

        // Delega a escrita para o adapter
        adapter.log(mensagemFormatada);
    }

    public void logFalha(String mensagemFalha, String operacao, String nome, String usuario, String idc) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(DATE_FORMATTER);
        String hora = agora.format(TIME_FORMATTER);

        // Inclui o ID-C apenas se ele for fornecido
        String idcPart = (idc != null && !idc.isEmpty()) ? ", " + idc : "";

        String mensagemFormatada = String.format(
                "Ocorreu a falha %s ao realizar a \"%s do contato %s, (%s, %s, %s%s, quando for o caso.)\"",
                mensagemFalha, operacao, nome, data, hora, usuario, idcPart
        );

        // Delega a escrita para o adapter
        adapter.log(mensagemFormatada);
    }
}