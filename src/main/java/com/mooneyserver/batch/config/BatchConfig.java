package com.mooneyserver.batch.config;

import javax.sql.DataSource;

import lombok.SneakyThrows;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.oxm.Unmarshaller;

import com.mooneyserver.batch.util.MySimpleUnmarshaller;
import com.mooneyserver.batch.util.SqlMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Value("classpath:sql/create-sample-batch-schema.sql")
  private Resource schemaScript;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  private DatabasePopulator databasePopulator() {
    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(schemaScript);
    return populator;
  }

  @SneakyThrows
  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriver(new com.mysql.jdbc.Driver());
    dataSource.setUrl("jdbc:mysql://localhost:3306/spring_batch_sample");
    dataSource.setUsername("root");
    dataSource.setPassword("password");
    DatabasePopulatorUtils.execute(databasePopulator(), dataSource);

    return dataSource;
  }
  
  @Bean
  public Unmarshaller unmarshaller() {
    MySimpleUnmarshaller unmarshaller =  new MySimpleUnmarshaller();
    
    return unmarshaller;
  }

  @Bean
  public ItemReader<String> reader() {
    StaxEventItemReader<String> reader = new StaxEventItemReader<>();
    reader.setFragmentRootElementName("DESIRED_NODE");
    reader.setResource(new FileSystemResource("/tmp/someFile.xml"));
    reader.setUnmarshaller(unmarshaller());

    return reader;
  }

  @Bean
  public ItemWriter<String> writer() {
      final JdbcBatchItemWriter<String> writer = new JdbcBatchItemWriter<String>();
      writer.setItemSqlParameterSourceProvider(new SqlMapper());
      writer.setSql("INSERT INTO FIN_ADVICES (fin_adv, status) VALUES (:fin_adv, 'NEW')");
      writer.setDataSource(dataSource());
      return writer;
  }

  @Bean
  public Job importUserJob() {
    return jobBuilderFactory.get("sampleJob")
        .flow(readProcessWriteSampleStep()).end().build();
  }

  @Bean
  public Step readProcessWriteSampleStep() {
    return stepBuilderFactory.get("readProcessWriteStep")
        .<String, String> chunk(1)
        .reader(reader())
        .writer(writer())
        .build();
  }
}
