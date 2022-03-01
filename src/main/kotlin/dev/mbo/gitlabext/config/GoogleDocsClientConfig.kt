package dev.mbo.gitlabext.config

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.DataStoreFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.docs.v1.Docs
import com.google.api.services.docs.v1.DocsScopes
import com.google.api.services.drive.Drive
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets


@Configuration
class GoogleDocsClientConfig(
    @Value("\${google.secretsFile}") val googleSecretsFile: String,
    @Value("\${google.credentialStore}") val googleCredentialStore: String,
) {

    @Bean
    fun jsonFactory(): JsonFactory {
        return GsonFactory.getDefaultInstance()
    }

    @Bean
    fun google(
        jsonFactory: JsonFactory,
    ): GoogleClientSecrets {
        return GoogleClientSecrets.load(
            jsonFactory,
            FileReader(googleSecretsFile, StandardCharsets.UTF_8)
        )
    }

    @Bean
    fun dataStoreFactory(): DataStoreFactory {
        return FileDataStoreFactory(File(googleCredentialStore))
    }

    @Bean
    fun netHttpTransport(): NetHttpTransport {
        return GoogleNetHttpTransport.newTrustedTransport()
    }

    @Bean
    fun googleAuthorizationCodeFlow(
        clientSecrets: GoogleClientSecrets,
        jsonFactory: JsonFactory,
        dataStoreFactory: DataStoreFactory,
        netHttpTransport: NetHttpTransport,
    ): AuthorizationCodeFlow {
        return GoogleAuthorizationCodeFlow.Builder(
            netHttpTransport, jsonFactory, clientSecrets,
            setOf(DocsScopes.DRIVE, DocsScopes.DOCUMENTS)
        )
            .setDataStoreFactory(dataStoreFactory)
            .setAccessType("offline")
            .setApprovalPrompt("auto")
            .build()
    }

    @Bean
    fun googleCredential(
        authorizationCodeFlow: AuthorizationCodeFlow
    ): Credential {
        val receiver = LocalServerReceiver.Builder().build()
        return AuthorizationCodeInstalledApp(authorizationCodeFlow, receiver).authorize("user")
    }

    @Bean
    fun googleDocs(
        netHttpTransport: NetHttpTransport,
        jsonFactory: JsonFactory,
        credential: Credential,
    ): Docs {
        return Docs.Builder(
            netHttpTransport, jsonFactory, credential
        )
            .setApplicationName("gitlab-ext")
            .build()
    }

    @Bean
    fun googleFiles(
        netHttpTransport: NetHttpTransport,
        jsonFactory: JsonFactory,
        credential: Credential,
    ): Drive {
        return Drive.Builder(
            netHttpTransport, jsonFactory, credential
        )
            .setApplicationName("gitlab-ext")
            .build()
    }

}