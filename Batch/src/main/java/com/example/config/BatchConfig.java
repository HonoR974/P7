package com.example.config;


import com.example.service.PretService;
import com.example.service.PretServiceImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ConfigurationBatch BatchConfig
 */

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


    /**
     * Execute les
     * @return
     */
    @Bean
    public Job rappel() {
        return jobBuilderFactory.get(JOBNAME)
                .start(verification())
                .next(changeStatutByDate())
                .next(getPret())
                .next(senMail())
                .build();
    }


    /**
     * Verifie les pret a valider
     * @return checkDate
     */
    @Bean
    public Step verification()
    {
        return stepBuilderFactory.get("verification")
                .tasklet(verif())
                .build();
    }


    /**
     * Execute getPretEnCours
     * @return adapter
     */
    @Bean
    public MethodInvokingTaskletAdapter verif()
    {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("getPretEnCours");

        return adapter;
    }

    /**
     * Change le statut
     * @return
     */
    @Bean
    public Step changeStatutByDate()
    {
        return stepBuilderFactory.get("ChangeStatut")
                .tasklet(changeStatut())
                .build();
    }

    /**
     * Execute sendPret
     * @return
     */
    @Bean
    public MethodInvokingTaskletAdapter changeStatut()
    {

        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("sendPret");


        return adapter;
    }

    /**
     *
     * @return
     */
    @Bean
    public Step getPret()
    {
        return this.stepBuilderFactory.get("getPretRetard")
                .tasklet(getPretRetard())
                .build();

    }

    @Bean
    public MethodInvokingTaskletAdapter getPretRetard() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(pretService());
        adapter.setTargetMethod("getPretRetard");

        return adapter;

    }

    @Bean
    public Step senMail()
    {
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

        return adapter;

    }


    @Bean
    public PretService pretService()
    {
        return new PretServiceImpl();
    }

}
