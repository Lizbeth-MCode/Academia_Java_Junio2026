package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

    private static final Logger log = LoggerFactory.getLogger(ReporteEstudianteProcessor.class);

    @Override
    public EstudianteReporte process(Estudiante estudiante) {

        EstudianteReporte reporte = new EstudianteReporte();

        reporte.setNombre(estudiante.getNombre());
        reporte.setGrupo(estudiante.getGrupo());
        reporte.setPromedio(estudiante.getPromedio());

        if (estudiante.getPromedio() >= 70) {
            reporte.setEstado("APROBADO");
        } else {
            reporte.setEstado("REPROBADO");
        }

        log.info("Reporte generado: {}", reporte);

        return reporte;
    }
}
