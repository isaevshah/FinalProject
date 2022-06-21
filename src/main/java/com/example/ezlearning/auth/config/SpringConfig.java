package com.example.ezlearning.auth.config;

import com.example.ezlearning.auth.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.nio.file.AccessDeniedException;

@Configuration
@EnableWebSecurity(debug = true) // указывает Spring контейнеру, чтобы находил файл конфигурации в классе. debug = true - для просмотра лога какие бины были созданы, в production нужно ставить false
    public class SpringConfig extends WebSecurityConfigurerAdapter {
        private UserDetailsServiceImpl userDetailsService;
        @Autowired
        public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) { // внедряем наш компонент Spring @Service
            this.userDetailsService = userDetailsService;
        }
        //Spring security
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        //Spring security
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public CustomSuccesHandler succesHandler(){
            return new CustomSuccesHandler();
        }
        @Bean
        public AccessDeniedHandler accessDeniedHandler(){
            return new CustomAccesDeniedHandler();
        }
        //Spring security
        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // порядок следования настроек внутри метода - неважен
        /* если используется другая клиентская технология (не SpringMVC, а например Angular, React и пр.),
            то выключаем встроенную Spring-защиту от CSRF атак,
            иначе запросы от клиента не будут обрабатываться, т.к. Spring Security будет пытаться в каждом входящем запроcе искать спец. токен для защиты от CSRF
        */
            http.csrf().disable(); // на время разработки проекта не будет ошибок (для POST, PUT и др. запросов) - недоступен и т.д
            http.formLogin().disable(); // отключаем, т.к. форма авторизации создается не на Spring технологии (например, Spring MVC + JSP), а на любой другой клиентской технологии
            http.httpBasic().disable(); // отключаем стандартную браузерную форму авторизации
            http.requiresChannel().anyRequest().requiresSecure(); // обязательное исп. HTTPS
//
//            http.authorizeRequests()
//                    .antMatchers("/login","/register")
//                    .permitAll()
//                    .antMatchers("/register/**").hasAuthority("ADMIN")
//                    .and()
//                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
//                    .and()
//                    .requiresChannel().anyRequest().requiresSecure()
//                    .and();
        }
    }
