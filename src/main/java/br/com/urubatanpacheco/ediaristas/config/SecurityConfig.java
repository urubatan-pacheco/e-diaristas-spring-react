package br.com.urubatanpacheco.ediaristas.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private UserDetailsService userDetailsService; 

   @Autowired
   private PasswordEncoder passwordEncoder;

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
        http.authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/admin/**").hasAuthority(TipoUsuario.ADMIN.toString()) // rotas admin apenas para usuários ADMIN
            .anyRequest().authenticated(); // qualquer outra rota vai ser bloqueada por padrão

        http.formLogin()
            .loginPage("/admin/login")
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/admin/servicos") //, true)
            .permitAll();

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout","GET"))
            .logoutSuccessUrl("/admin/login");

        http.rememberMe()
            .rememberMeParameter("lembrar-me")
            .tokenValiditySeconds(rememberMeValiditySeconds) // dois dias em segundos
            .key(rememberMeKey);

        http.cors();
        http.csrf().ignoringAntMatchers("/api/**");
   }

   @Override
   public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/webjars/**")
           .antMatchers("/img/**"); // aonfr pot padrão ficam os arquivos estáticos da aplicação

   }
}

