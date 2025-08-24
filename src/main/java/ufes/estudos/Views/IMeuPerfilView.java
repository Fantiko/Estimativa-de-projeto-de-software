package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface IMeuPerfilView {
    void exibirDados(String nome, String email, String telefone, String nivel, double estrelas, String tipoPerfil);
    void setFecharListener(ActionListener listener);
    void setReputacaoVisivel(boolean visivel);
}