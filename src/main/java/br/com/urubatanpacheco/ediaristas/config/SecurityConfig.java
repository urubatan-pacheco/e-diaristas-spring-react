package br.com.urubatanpacheco.ediaristas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.filters.AccessTokenRequestFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Value("${br.com.urubatanpacheco.ediaristas.rememberMe.key}")
    private String rememberMeKey;

    @Value("${br.com.urubatanpacheco.ediaristas.rememberMe.validitySeconds}")
    private int rememberMeValiditySeconds;

    @Autowired
    private AccessTokenRequestFilter accessTokenRequestFilter;    


    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;    

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
            http
            .requestMatchers(requestMatcherCustomizer -> 
                requestMatcherCustomizer
                    .antMatchers("/api/**","/auth/**")
            )
            .authorizeRequests(authorizeRequestsCustomizer ->
                authorizeRequestsCustomizer
                    .anyRequest()
                    .permitAll()
            )
            .csrf(csrfCustomizer ->
                csrfCustomizer
                    .disable()
            )
            .sessionManagement(sessionManagementCustomizer ->
                sessionManagementCustomizer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(accessTokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandlingCustomizer -> 
                exceptionHandlingCustomizer
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .cors(); 
                    
            return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
            http
            .requestMatchers(requestMatcherCustomizer -> 
                requestMatcherCustomizer
                    .antMatchers("/admin/**")
            )
            .authorizeRequests(authorizeRequestsCustomizer ->
                authorizeRequestsCustomizer
                    .anyRequest()
                    .hasAuthority(TipoUsuario.ADMIN.name()) // rotas admin apenas para usuários ADMIN
            )
            .formLogin(formLoginCustomizer ->
                formLoginCustomizer
                    .loginPage("/admin/login")
                    .usernameParameter("email")
                    .passwordParameter("senha")
                    .defaultSuccessUrl("/admin/servicos") 
                    .permitAll()                
            )
            .logout(logoutCustomizer ->
                logoutCustomizer
                    .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout","GET"))
                    .logoutSuccessUrl("/admin/login")
            )
            .rememberMe(rememberMeCustomizer ->
                rememberMeCustomizer
                    .rememberMeParameter("lembrar-me")
                    .tokenValiditySeconds(rememberMeValiditySeconds) // dois dias em segundos
                    .key(rememberMeKey)
            )
            .exceptionHandling(exceptionHandlingCustomizer ->
                exceptionHandlingCustomizer
                    .accessDeniedPage("/admin/login")
            );

            return http.build();
    } 

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
            return 
                web -> web
                        .ignoring()
                        .antMatchers("/webjars/**")
                        .antMatchers("/img/**"); // aonfr pot padrão ficam os arquivos estáticos da aplicação
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }     
}
