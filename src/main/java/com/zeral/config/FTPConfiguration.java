package com.zeral.config;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * FTP配置
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Configuration
public class FTPConfiguration {
    private final ApplicationProperties applicationProperties;

    public FTPConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost(applicationProperties.getFtp().getHost());
        sf.setUsername(applicationProperties.getFtp().getUserName());
        sf.setPassword(applicationProperties.getFtp().getPassWord());
        sf.setControlEncoding(StandardCharsets.UTF_8.displayName());
        return new CachingSessionFactory<>(sf);
    }

    @Bean
    @ServiceActivator(inputChannel = "toFtpChannel")
    public MessageHandler ftpHandler() {
        FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
        ExpressionParser parser = new SpelExpressionParser();
        handler.setRemoteDirectoryExpression(parser.parseExpression(  "'"+ applicationProperties.getFtp().getRemoteDirectory() +"'" + " + headers['" + FileHeaders.REMOTE_DIRECTORY + "']"));
        handler.setAutoCreateDirectory(true);
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) {
                return message.getHeaders().get(FileHeaders.FILENAME).toString();
            } else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        return handler;
    }

    @Bean
    public FtpRemoteFileTemplate template() {
        return new FtpRemoteFileTemplate(ftpSessionFactory());
    }
}
