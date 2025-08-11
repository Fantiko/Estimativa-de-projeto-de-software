package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface ICadastroView {
    String getUsuario();
    String getSenha();
    String getConfirmarSenha();

    void setAvancarListener(ActionListener listener);
    void setCancelarListener(ActionListener listener);

    void exibirMensagem(String mensagem);
}
