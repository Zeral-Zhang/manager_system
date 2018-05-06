package com.zeral.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Manager.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Ftp ftp = new Ftp();

    public Ftp getFtp() {
        return ftp;
    }

    public static class Ftp {

        private String host;

        private String userName;

        private String passWord;

        private String port;

        // 远程ftp文件夹路径
        private String remoteDirectory;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getRemoteDirectory() {
            return remoteDirectory;
        }

        public void setRemoteDirectory(String remoteDirectory) {
            this.remoteDirectory = remoteDirectory;
        }
    }
}
