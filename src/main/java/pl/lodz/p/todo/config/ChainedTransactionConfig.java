package pl.lodz.p.todo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChainedTransactionConfig {

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(
            @Qualifier("defaultTransactionManager") PlatformTransactionManager defaultTransactionManager,
            @Qualifier("analyticsTransactionManager") PlatformTransactionManager analyticsTransactionManager,
            @Qualifier("mongoTransactionManager") MongoTransactionManager mongoTransactionManager) {
        return new ChainedTransactionManager(defaultTransactionManager, analyticsTransactionManager, mongoTransactionManager);
    }
}
