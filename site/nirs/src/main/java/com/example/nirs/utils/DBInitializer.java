package com.example.nirs.utils;

import com.example.nirs.models.*;
import com.example.nirs.repository.*;
import com.example.nirs.services.FilmService;
import com.example.nirs.services.UserService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInitializer implements CommandLineRunner {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserService authenticationService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;



    @Override
    public void run(String... args) throws Exception {

        //создание базовых сущностей (будут использоваться после удаления элементов)
        if (!genreRepository.findByName("UNKNOWN_GENRE").isPresent())
            genreRepository.save(new Genre("UNKNOWN_GENRE","No info"));
        if (!filmRepository.findByName("UNKNOWN_FILM").isPresent()){
            Film film = new Film("UNKNOWN_FILM","basic short film info", "basic full film info",
                    new Date(0),genreRepository.findByName("UNKNOWN_GENRE").get(),null,"");
            filmRepository.save(film);
        }

        //создание базовых ролей
        if (!roleRepository.findByAuthority("USER").isPresent())
            roleRepository.save(new Role("USER"));
        if (!roleRepository.findByAuthority("ADMIN").isPresent())
            roleRepository.save(new Role("ADMIN"));
        if (!roleRepository.findByAuthority("WORKER").isPresent())
            roleRepository.save(new Role("WORKER"));

        //создание базового админа
        if (!workerRepository.findById(userRepository.findByUsername("ADMIN").get().getUserId()).isPresent()){
            workerRepository.save(new Worker(userRepository.findByUsername("ADMIN").get().getUserId(),"ADMIN_NAME","ADMIN_SECONDNAME",
                    "ADMIN_SURNAME","ADMIN_PHONE","ADMIN_GENDER",null,0,"ADMIN","ADMIN_EMAIL",null));
        }

        if (!cinemaRepository.findByName("DEFAULT_CINEMA").isPresent())
            cinemaRepository.save(new Cinema("DEFAULT_CINEMA","DEFAULT_CINEMA_ADDRESS",null,null, new String[]{""} ));

        if (!hallRepository.findByName("DEFAULT_HALL").isPresent())
            hallRepository.save(new Hall("DEFAULT_HALL",0,0,cinemaRepository.findByName("DEFAULT_CINEMA").get()));
    }
}
