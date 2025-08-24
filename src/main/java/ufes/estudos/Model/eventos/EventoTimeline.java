package ufes.estudos.Model.eventos;
import java.time.LocalDateTime;

public class EventoTimeline {
    private final String idcItem;
    private final TipoEvento tipoEvento;
    private final LocalDateTime dataEvento;
    private final double gwpAvoidedNoMomento;
    private final double mciNoMomento;
    private final String detalhes;

    public EventoTimeline(String idcItem, TipoEvento tipoEvento, double gwpAvoidedNoMomento, double mciNoMomento, String detalhes) {
        this.idcItem = idcItem;
        this.tipoEvento = tipoEvento;
        this.dataEvento = LocalDateTime.now();
        this.gwpAvoidedNoMomento = gwpAvoidedNoMomento;
        this.mciNoMomento = mciNoMomento;
        this.detalhes = detalhes;
    }

    // --- GETTERS ADICIONADOS AQUI ---
    public String getIdcItem() {
        return idcItem;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public double getGwpAvoidedNoMomento() {
        return gwpAvoidedNoMomento;
    }

    public double getMciNoMomento() {
        return mciNoMomento;
    }

    public String getDetalhes() {
        return detalhes;
    }
}