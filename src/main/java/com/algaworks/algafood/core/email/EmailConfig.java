package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastucture.service.mail.FakeEnvioEmailService;
import com.algaworks.algafood.infrastucture.service.mail.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties properties;

    @Bean
    public EnvioEmailService envioEmailService() {
        if (EmailProperties.TipoImpl.SMTP.equals(properties.getImpl()))
            return new SmtpEnvioEmailService();
        else
            return new FakeEnvioEmailService();
    }
}
