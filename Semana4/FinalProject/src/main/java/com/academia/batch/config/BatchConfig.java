package com.academia.batch.config;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.processor.EstudianteProcessor;
import com.academia.batch.processor.ReporteEstudianteProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public FlatFileItemReader<Estudiante> reader() {
        return new FlatFileItemReaderBuilder<Estudiante>()
                .name("estudianteReader")
                .resource(new ClassPathResource("estudiantes.csv"))
                .delimited()
                .names("nombre", "grupo", "nota1", "nota2", "nota3")
                .targetType(Estudiante.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public EstudianteProcessor processor() {
        return new EstudianteProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Estudiante> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Estudiante>()
                .sql("INSERT INTO estudiantes_procesados " +
                        "(nombre, grupo, nota1, nota2, nota3, promedio) " +
                        "VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Estudiante> reader,
                      EstudianteProcessor processor,
                      JdbcBatchItemWriter<Estudiante> writer) {

        return new StepBuilder("step1", jobRepository)
                .<Estudiante, Estudiante>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public JdbcCursorItemReader<Estudiante> dbReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Estudiante>()
                .name("dbReader")
                .dataSource(dataSource)
                .sql("SELECT nombre, grupo, promedio FROM estudiantes_procesados")
                .rowMapper((rs, rowNum) -> {
                    Estudiante e = new Estudiante();
                    e.setNombre(rs.getString("nombre"));
                    e.setGrupo(rs.getString("grupo"));
                    e.setPromedio(rs.getDouble("promedio"));
                    return e;
                })
                .build();
    }

    @Bean
    public ReporteEstudianteProcessor reporteProcessor() {
        return new ReporteEstudianteProcessor();
    }

    @Bean
    public MongoItemWriter<EstudianteReporte> mongoWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<EstudianteReporte>()
                .template(mongoTemplate)
                .collection("reportes_estudiantes")
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      JdbcCursorItemReader<Estudiante> dbReader,
                      ReporteEstudianteProcessor reporteProcessor,
                      MongoItemWriter<EstudianteReporte> mongoWriter) {

        return new StepBuilder("step2", jobRepository)
                .<Estudiante, EstudianteReporte>chunk(3, transactionManager)
                .reader(dbReader)
                .processor(reporteProcessor)
                .writer(mongoWriter)
                .build();
    }


    @Bean
    public Job job(JobRepository jobRepository, Step step1, Step step2) {

        return new JobBuilder("calificacionesJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }
}