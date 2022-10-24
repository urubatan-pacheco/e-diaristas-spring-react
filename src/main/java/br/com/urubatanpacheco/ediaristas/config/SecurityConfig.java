package br.com.urubatanpacheco.ediaristas.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.filters.AccessTokenRequestFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

   @Autowired
   private UserDetailsService userDetailsService; 

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Order(1)
   @Configuration
   public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AccessTokenRequestFilter accessTokenRequestFilter;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
       }   

        @Override
        protected void configure(HttpSecurity http) throws Exception {
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
            .cors();        
        } 
        
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            
            return super.authenticationManagerBean();
        }        
    }

    @Order(2)
    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Value("${br.com.urubatanpacheco.ediaristas.rememberMe.key}")
        private String rememberMeKey;

        @Value("${br.com.urubatanpacheco.ediaristas.rememberMe.validitySeconds}")
        private int rememberMeValiditySeconds;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
       }         

        @Override
        protected void configure(HttpSecurity http) throws Exception {

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
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web
            .ignoring()
            .antMatchers("/webjars/**")
            .antMatchers("/img/**"); // aonfr pot padrão ficam os arquivos estáticos da aplicação
        }
    }
}

