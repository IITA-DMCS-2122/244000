package pl.lodz.p.todo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "pl.lodz.p.todo.repositories.domain",
        entityManagerFactoryRef = "defaultEntityManager",
        transactionManagerRef = "defaultTransactionManager"
)
public class DefaultDBConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.default-datasource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "defaultEntityManager")
    public LocalContainerEntityManagerFactoryBean defaultManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
        return factoryBuilder
                .dataSource(defaultDataSource())
                .packages("pl.lodz.p.todo.domain")
                .properties(Map.of("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"))
                .build();
    }

    @Primary
    @Bean("defaultTransactionManager")
    public PlatformTransactionManager defaultTransactionManager(
            final @Qualifier("defaultEntityManager") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
