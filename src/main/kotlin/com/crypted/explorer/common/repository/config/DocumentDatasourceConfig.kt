package com.crypted.explorer.common.repository.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.security.KeyStore
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


@Configuration
@EnableMongoRepositories(
    basePackages = ["com.crypted.explorer.service"],
//        repositoryBaseClass = BaseMongoRepositoryImpl::class,
    mongoTemplateRef = "mongoTemplate",
)
class DocumentDatasourceConfig : AbstractMongoClientConfiguration() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Value(value = "\${mongo.ssl.trustStore}")
    lateinit var trustStore: String

    @Value(value = "\${mongo.ssl.trustStorePassword}")
    lateinit var trustStorePassword: String

    @Value(value = "\${spring.datasource.mongodb.url}")
    lateinit var url: String

    @Value(value = "\${spring.datasource.mongodb.database}")
    lateinit var dataBaseName: String

    @Value(value = "\${spring.profiles.active}")
    lateinit var env: String
    override fun getDatabaseName(): String {
        return dataBaseName
    }


    //    @Bean
    override fun mongoClient(): MongoClient {
//        checkSslProperties()
        val connectionString = ConnectionString(url)
        //TLS
//        val classPathJksFile = ResourceUtils.getFile(trustStore)
        val ks = KeyStore.getInstance("JKS")
        log.info("### env ::: $env")


        if ("local".equals(env)) {
            val file =  ResourceUtils.getFile(trustStore).absoluteFile
            ks.load(FileInputStream(file.absoluteFile), trustStorePassword.toCharArray())

        } else {
            val inputStream = ClassPathResource(trustStore).inputStream
            ks.load(inputStream, trustStorePassword.toCharArray())
        }
//        val file =  ResourceUtils.getFile(trustStore).absoluteFile
//        ks.load(FileInputStream(file.absoluteFile), trustStorePassword.toCharArray())

        val trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustFactory.init(ks)


        val sc = SSLContext.getInstance("TLS");
        sc.init(null, trustFactory.getTrustManagers(), null)

//        val sslContext = SSLContext()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToSslSettings { block -> block.enabled(true).context(sc) }
            .build()


        return MongoClients.create(mongoClientSettings)
    }

    override fun mongoTemplate(databaseFactory: MongoDatabaseFactory, converter: MappingMongoConverter): MongoTemplate {
        return MongoTemplate(
            mongoClient(), databaseName
        )
    }

    @Bean
    fun mongoTransactionManager(databaseFactory: MongoDatabaseFactory): MongoTransactionManager{
        return MongoTransactionManager(databaseFactory)
    }

    override fun getMappingBasePackages(): MutableCollection<String> {
        return Collections.singleton("com.crypted.explorer.service")
    }


}