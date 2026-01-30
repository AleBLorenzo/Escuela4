package com.example.dao;

import com.example.model.Asignacion;
import com.example.model.AsignacionId;

public interface AsignacionDAO {

    void guardar(Asignacion a);

    void actualizar(Asignacion a);

    void eliminar(Asignacion a);

    Asignacion buscarPorId(AsignacionId id);

    int calcularCargaHorariaTotal(Long profesorId);

}
