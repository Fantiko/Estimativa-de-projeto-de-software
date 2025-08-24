package ufes.estudos.repository;

import ufes.estudos.Model.eventos.EventoTimeline;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimelineRepository {
    private static TimelineRepository instance;
    private final List<EventoTimeline> eventos;

    private TimelineRepository() {
        this.eventos = new ArrayList<>();
    }

    public static TimelineRepository getInstance() {
        if (instance == null) {
            instance = new TimelineRepository();
        }
        return instance;
    }

    public void addEvento(EventoTimeline evento) {
        this.eventos.add(evento);
        System.out.println("Novo evento na Timeline: " + evento.getTipoEvento() + " para o item " + evento.getIdcItem());
    }

    public List<EventoTimeline> getTimelineParaItem(String idcItem) {
        return eventos.stream()
                .filter(e -> e.getIdcItem().equals(idcItem))
                .collect(Collectors.toList());
    }
}