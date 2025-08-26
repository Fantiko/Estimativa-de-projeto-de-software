package ufes.estudos.repository;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.eventos.EventoTimeline;
import ufes.estudos.dao.TimelineDAO;

import java.util.List;

public class TimelineRepository {
    private static TimelineRepository instance;
    private final TimelineDAO timelineDAO;

    private TimelineRepository() {
        this.timelineDAO = new TimelineDAO(new SQLiteConnectionManager());
    }

    public static TimelineRepository getInstance() {
        if (instance == null) {
            instance = new TimelineRepository();
        }
        return instance;
    }

    public void addEvento(EventoTimeline evento) {
        timelineDAO.insert(evento);
        System.out.println("Novo evento na Timeline (DB): " + evento.getTipoEvento() + " para o item " + evento.getIdcItem());
    }

    public List<EventoTimeline> getTimelineParaItem(String idcItem) {
        return timelineDAO.getByItemId(idcItem);
    }
}