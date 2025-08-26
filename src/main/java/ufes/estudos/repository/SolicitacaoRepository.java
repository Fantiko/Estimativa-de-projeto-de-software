package ufes.estudos.repository;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import ufes.estudos.dao.SolicitacaoDAO;
import ufes.estudos.observer.Subject;
import java.util.List;

public class SolicitacaoRepository extends Subject {
    private static SolicitacaoRepository instance;
    private final SolicitacaoDAO solicitacaoDAO;

    private SolicitacaoRepository() {
        // O repositório agora gerencia um DAO, em vez de uma lista
        this.solicitacaoDAO = new SolicitacaoDAO(new SQLiteConnectionManager());
    }

    public static SolicitacaoRepository getInstance() {
        if (instance == null) {
            instance = new SolicitacaoRepository();
        }
        return instance;
    }

    public void addSolicitacao(SolicitacaoPerfil solicitacao) {
        solicitacaoDAO.insert(solicitacao);
        notifyObservers("ADD", solicitacao);
    }

    public void removeSolicitacao(String nomeUsuario, String perfilSolicitado) {
        solicitacaoDAO.delete(nomeUsuario, perfilSolicitado);
        notifyObservers("REMOVE", null); // Notifica que algo mudou
    }

    public List<SolicitacaoPerfil> getSolicitacoes() {
        // Busca os dados diretamente do banco a cada chamada
        return solicitacaoDAO.getAll();
    }
}