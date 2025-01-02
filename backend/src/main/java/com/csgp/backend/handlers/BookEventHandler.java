package com.csgp.backend.handlers;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.csgp.backend.model.Book;
import com.csgp.backend.model.User;
import com.csgp.backend.repositories.UserRepository;

@Component
@RepositoryEventHandler(Book.class)
public class BookEventHandler {
    private final UserRepository userRepository;

    // Inyección de dependencias a través del constructor
    public BookEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HandleBeforeCreate
    public void addUserToBook(Book book) {
        // Obtener el usuario autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            // Aquí obtienes el ID del usuario a partir del username, por ejemplo:
            // Supongamos que tienes un servicio UserService con un método para obtener el usuario por nombre de usuario.
            User user = userRepository.findByUsername(username).get();
            if (user != null) {
                book.setUser(user);
            }
        }
    }
}
