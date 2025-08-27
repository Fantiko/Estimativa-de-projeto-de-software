package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Usuario;

public interface ILog {
    void logSucesso(String operacao, String idc, String nome, Usuario usuario);
    void logFalha(String operacao, String mensagemFalha, String nome, Usuario usuario, String idc);
}