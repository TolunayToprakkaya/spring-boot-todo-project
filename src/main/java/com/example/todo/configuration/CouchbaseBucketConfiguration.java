package com.example.todo.configuration;

import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.properties.CouchbaseClusterProperties;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import java.util.Collections;

@Configuration
@EnableCouchbaseRepositories
public class CouchbaseBucketConfiguration extends AbstractCouchbaseConfiguration {

    private final CouchbaseClusterProperties couchbaseClusterProperties;
    private final ApplicationContext applicationContext;

    public CouchbaseBucketConfiguration(CouchbaseClusterProperties couchbaseClusterProperties, ApplicationContext applicationContext) {
        this.couchbaseClusterProperties = couchbaseClusterProperties;
        this.applicationContext = applicationContext;
    }

    @Override
    public String getConnectionString() {
        return couchbaseClusterProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseClusterProperties.getUserName();
    }

    @Override
    public String getPassword() {
        return couchbaseClusterProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseClusterProperties.getBucketTodo().getName();
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping mapping) {
        mapping
                .mapEntity(Todo.class, getCouchbaseTemplate(couchbaseClusterProperties.getBucketTodo().getName()))
                .mapEntity(User.class, getCouchbaseTemplate(couchbaseClusterProperties.getBucketUser().getName()));
    }

    @SneakyThrows
    private CouchbaseTemplate getCouchbaseTemplate(String bucketName) {
        CouchbaseTemplate template = new CouchbaseTemplate(couchbaseClientFactory(bucketName),
                mappingCouchbaseConverter(couchbaseMappingContext(customConversions()), new CouchbaseCustomConversions(Collections.emptyList())));

        template.setApplicationContext(applicationContext);
        return template;
    }

    private CouchbaseClientFactory couchbaseClientFactory(String bucketName) {
        return new SimpleCouchbaseClientFactory(couchbaseCluster(couchbaseClusterEnvironment()), bucketName, this.getScopeName());
    }
}
