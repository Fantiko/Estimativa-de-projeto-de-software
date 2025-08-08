package ufes.estudos.Presenter;

import ufes.estudos.Views.Observer;

import java.util.ArrayList;
import java.util.List;

public class SistemaPresenter {
    List<Observer> telas;

    public SistemaPresenter() {
        this.telas = new ArrayList<>();
    }

    public void registrarTela(Observer obs){
        telas.add(obs);
    }

    public void removerTela(Observer obs){
        telas.remove(obs);
    }

    private void atualizarTelas(){ //TODO adicionar parametro dos dados e passar
        for (Observer obs : telas){
            obs.atualizar();
        }
    }


}
