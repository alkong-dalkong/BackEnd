package alkong_dalkong.backend.Common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import alkong_dalkong.backend.Common.Filter.LoginFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
        private final ApplicationContext applicationContext;

        @Bean
        public OpenApiCustomizer customSpringSecurityLoginEndpointCustomiser() {
                SecurityScheme securityScheme = new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization");

                SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
                FilterChainProxy filterChainProxy = applicationContext
                                .getBean(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME,
                                                FilterChainProxy.class);
                return openAPI -> {
                        Components components = openAPI.getComponents();
                        Server server = new Server();
                        server.setUrl("https://alkongdalkong.duckdns.org");
                        components.addSecuritySchemes("bearerAuth", securityScheme);
                        openAPI.security(Arrays.asList(securityRequirement))
                                        .info(apiInfo()).servers(List.of(server));

                        for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                                Optional<LoginFilter> optionalLoginFilter = filterChain.getFilters().stream()
                                                .filter(LoginFilter.class::isInstance)
                                                .map(LoginFilter.class::cast)
                                                .findAny();
                                Optional<LoginFilter> optionalLogoutFilter = filterChain.getFilters().stream()
                                                .filter(LoginFilter.class::isInstance)
                                                .map(LoginFilter.class::cast)
                                                .findAny();
                                if (optionalLoginFilter.isPresent()) {
                                        LoginFilter loginFilter = optionalLoginFilter.get();

                                        Operation operation = new Operation();

                                        Schema<?> schema = new ObjectSchema()
                                                        .addProperty("id", new StringSchema())
                                                        .addProperty("password", new StringSchema());

                                        RequestBody requestBody = new RequestBody().content(new Content().addMediaType(
                                                        org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                                                        new MediaType().schema(schema)));
                                        operation.requestBody(requestBody);

                                        ApiResponses apiResponses = new ApiResponses();
                                        apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                                                        new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                                        apiResponses.addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                                                        new ApiResponse().description(
                                                                        HttpStatus.UNAUTHORIZED.getReasonPhrase()));

                                        operation.responses(apiResponses);
                                        operation.addTagsItem("login-endpoint");
                                        PathItem pathItem = new PathItem().post(operation);
                                        openAPI.getPaths().addPathItem("/user/login", pathItem);
                                }

                                if (optionalLogoutFilter.isPresent()) {
                                        LoginFilter logoutFilter = optionalLogoutFilter.get();

                                        Operation operation = new Operation();

                                        ApiResponses apiResponses = new ApiResponses();
                                        apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                                                        new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                                        apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                                        new ApiResponse().description(
                                                                        HttpStatus.BAD_REQUEST.getReasonPhrase()));

                                        operation.responses(apiResponses);
                                        operation.addTagsItem("logout-endpoint");
                                        PathItem pathItem = new PathItem().post(operation);
                                        openAPI.getPaths().addPathItem("/user/logout", pathItem);
                                }
                        }
                };
        }

        @Bean
        public GroupedOpenApi openAPI() {
                return GroupedOpenApi
                                .builder()
                                .group("api")
                                .pathsToMatch("/**")
                                .addOpenApiCustomizer(
                                                customSpringSecurityLoginEndpointCustomiser())
                                .build();
        }

        private Info apiInfo() {
                return new Info()
                                .title("Medical API Documentation")
                                .description("springdoc Swagger UI 테스트")
                                .version("1.0.0");
        }
}