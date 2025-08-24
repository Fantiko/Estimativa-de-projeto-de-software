package ufes.estudos.repository;

import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import ufes.estudos.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoRepository extends Subject {
    private static SolicitacaoRepository instance;
    private final List<SolicitacaoPerfil> solicitacoes;

    private SolicitacaoRepository() {
        this.solicitacoes = new ArrayList<>();
    }

    public static SolicitacaoRepository getInstance() {
        if (instance == null) {
            instance = new SolicitacaoRepository();
        }
        return instance;
    }

    public void addSolicitacao(SolicitacaoPerfil solicitacao) {
        // Evita solicitações duplicadas
        for (SolicitacaoPerfil s : solicitacoes) {
            if (s.getNomeUsuario().equals(solicitacao.getNomeUsuario()) && s.getPerfilSolicitado().equals(solicitacao.getPerfilSolicitado())) {
                return; // Já existe, não faz nada
            }
        }
        this.solicitacoes.add(solicitacao);
        notifyObservers("ADD", solicitacao);
    }

    public void removeSolicitacao(String nomeUsuario, String perfilSolicitado) {
        solicitacoes.removeIf(s -> s.getNomeUsuario().equals(nomeUsuario) && s.getPerfilSolicitado().equals(perfilSolicitado));
        notifyObservers("REMOVE", null); // Notifica que algo mudou
    }

    public List<SolicitacaoPerfil> getSolicitacoes() {
        return new ArrayList<>(solicitacoes);
    }
}