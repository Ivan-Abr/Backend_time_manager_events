//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//@Configuration/*  w w   w .   de   m  o   2s  .  c  o  m  */
//@EnableWebSecurity
//class SecurityConfig(private val customAuthenticationProvider: CustomAuthenticationProvider) : WebSecurityConfigurerAdapter() {
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(customAuthenticationProvider)
//    }
//
//    override fun configure(http: HttpSecurity) {
//        http
//            .authorizeRequests()
//            .antMatchers("/public/**").permitAll()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .permitAll()
//
//        // Add custom filters if needed
//        http.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter::class.java)
//    }
//
//    @Bean
//    fun customFilter(): CustomFilter {
//        return CustomFilter()
//    }
//}