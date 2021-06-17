package com.microservicios.springboot.app.oauth.clients;

import com.microservicios.springboot.app.commons.usuarios.models.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

   @GetMapping("/usuarios/search/buscar-username")
   Usuario findByUsername(@RequestParam String username);

}
