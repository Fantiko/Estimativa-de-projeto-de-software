package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface ILoginView {
    String getUsuario();
    String getSenha();

    void setLoginListener(ActionListener listener);
    void setCadastrarListener(ActionListener listener);

    void exibirMensagem(String mensagem);
}
