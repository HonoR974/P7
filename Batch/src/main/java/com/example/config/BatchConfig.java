package com.example.config;

import com.example.batch.PretProcessor;
import com.example.model.PretDTO;
import com.example.service.PretService;
import com.example.service.PretServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.function.Function;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {

    private static final String STEP_NAME="emaiilRetardStep";

    private static final String JOBNAME = "emailRetardJob";


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job rappel() {
        return jobBuilderFactory.get(JOBNAME)
                .start(getPret())
                .next(senMail())
                .build();
    }

    @Bean
    public Step getPret()
    {
        System.out.println("\n step 1 debut " );
        return this.stepBuilderFactory.get("step1")
                .tasklet(getPretService())
                .build();

    }

    @Bean
    public MethodInvokingTaskletAdapter getPretService()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("getPretRetard");

        System.out.println("\n step 1 fin " );
        return adapter;

    }

    @Bean
    public Step senMail()
    {
        System.out.println("\n step 2 debut " );
        return this.stepBuilderFactory.get("step2")
                .tasklet(sendMailService())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter sendMailService()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("sendMailRetard");

        System.out.println("\n step 2 fin " );
        return adapter;

    }


    @Bean
    public PretService pretService()
    {
        return new PretServiceImpl();
    }



}
