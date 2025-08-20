package ufes.estudos.observer;

public interface Observer {
    void update(String tipoNotificacao, Object dados);
}