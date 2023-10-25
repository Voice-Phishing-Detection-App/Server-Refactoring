package PhishingUniv.Phinocchio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket Doubt() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("Doubt-api")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Doubt.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    @Bean
    public Docket Login() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("Login-api")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Login.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket Report() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("Report-api")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Report.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket Twilio() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("Twilio-api")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Twilio.controller"))
                .paths(PathSelectors.ant("/twilio/**"))
                .build()
                .apiInfo(apiInfo());
    }

//    @Bean
//    public Docket TwilioWebhook() {
//
//        return new Docket(DocumentationType.OAS_30)
//                .groupName("Twilio-Webhook-api")
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Twilio.controller"))
//                .paths(PathSelectors.ant("/voice/**"))
//                .build()
//                .apiInfo(apiInfo());
//    }

    @Bean
    public Docket Sos() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("Sos-api")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("PhishingUniv.Phinocchio.domain.Sos.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Your API Title")
                .description("Your API Description")
                .version("1.0.0")
                .build();
    }
}
