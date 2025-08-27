package ufes.estudos.service;

import ufes.estudos.Model.Usuario.Usuario;
import org.example.LogAdapter.*;
import ufes.estudos.service.ServiceInterfaces.ILog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FantikoLogAdapter implements ILog {
    private final LogAdapter logExterno;
    private final DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public FantikoLogAdapter(String formato) {
        Logger logger;
        String nomeArquivo;

        if ("json".equalsIgnoreCase(formato)) {
            nomeArquivo = "log.json";
            logger = new JsonFileLogger(nomeArquivo);
        } else {
            nomeArquivo = "log.xml";
            logger = new XMLFileLogger(nomeArquivo);
        }

        this.logExterno = new LogAdapter(logger);
    }

    @Override
    public void logSucesso(String operacao, String idc, String nome, Usuario usuario) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(dataFormatter);
        String hora = agora.format(horaFormatter);

        String mensagemFormatada = String.format("<<%s>><<%s>>: <<%s>>, (<<%s>>, <<%s>>, e <<%s>>)",
                idc, operacao, nome, data, hora, usuario.getUsuario());

        // --- CORREÇÃO FINAL: Usamos o método .log() que você descobriu ---
        logExterno.log(mensagemFormatada);
    }

    @Override
    public void logFalha(String operacao, String mensagemFalha, String nome, Usuario usuario, String idc) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(dataFormatter);
        String hora = agora.format(horaFormatter);

        String idcFormatado = (idc != null && !idc.isEmpty()) ? ", e <<" + idc + ">>" : "";
        String mensagemFormatada = String.format("Ocorreu a falha <<%s>> ao realizar a \"%s do contato <<%s>>, (<<%s>>, <<%s>>, <<%s>>%s)",
                mensagemFalha, operacao, nome, data, hora, usuario.getUsuario(), idcFormatado);

        // --- CORREÇÃO FINAL: Usamos o método .log() que você descobriu ---
        logExterno.log(mensagemFormatada);
    }
}