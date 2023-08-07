package com.alkemy.ong.util;

import com.alkemy.ong.security.dto.RolePostDto;
import com.alkemy.ong.security.dto.UserPostDto;
import com.alkemy.ong.security.service.IUserService;
import com.alkemy.ong.service.IRoleService;
import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {

            //ROLES
            if(roleService.getAllRoles().isEmpty()){
                    RolePostDto roleAdmin = new RolePostDto(ROLE_ADMIN);
                    roleService.save(roleAdmin);
                    RolePostDto roleRegular = new RolePostDto(ROLE_USER);
                    roleService.save(roleRegular);
            }


            //ADMIN USERS
            if(userService.getAllUsers().isEmpty()) {
                    UserPostDto userAdmin1 = new UserPostDto(
                            "Alden",
                            "Ferrel",
                            "alden.admin1@ongadmin.com",
                            "1234Admin1!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin1);

                    UserPostDto userAdmin2 = new UserPostDto(
                            "Juan",
                            "Berger",
                            "juan.admin2@ongadmin.com",
                            "1234Admin2!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin2);

                    UserPostDto userAdmin3 = new UserPostDto(
                            "Maria",
                            "Macias",
                            "maria.admin3@ongadmin.com",
                            "1234Admin3!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin3);

                    UserPostDto userAdmin4 = new UserPostDto(
                            "Amal",
                            "Stuart",
                            "amal.admin4@ongadmin.com",
                            "1234Admin4!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin4);

                    UserPostDto userAdmin5 = new UserPostDto(
                            "Connor",
                            "Wood",
                            "connor.admin5@ongadmin.com",
                            "1234Admin5!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin5);

                    UserPostDto userAdmin6 = new UserPostDto(
                            "Rodrigo",
                            "Perez",
                            "rodrigo.admin6@ongadmin.com",
                            "1234Admin6!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin6);

                    UserPostDto userAdmin7 = new UserPostDto(
                            "Samanta",
                            "Claker",
                            "samanta.admin7@ongadmin.com",
                            "1234Admin7!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin7);

                    UserPostDto userAdmin8 = new UserPostDto(
                            "Catherine",
                            "Steve",
                            "catherine.admin8@ongadmin.com",
                            "1234Admin8!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin8);

                    UserPostDto userAdmin9 = new UserPostDto(
                            "Esteban",
                            "Rush",
                            "esteban.admin9@ongadmin.com",
                            "1234Admin9!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin9);

                    UserPostDto userAdmin10 = new UserPostDto(
                            "Lana",
                            "Allison",
                            "lana.admin10@ongadmin.com",
                            "1234Admin10!",
                            "example.png");
                    userService.saveTestDataUser(userAdmin10);


                    //REGULAR USERS
                    UserPostDto userRegular1 = new UserPostDto(
                            "Alice",
                            "Lott",
                            "alice.l123@hotm.ca",
                            "1234User1!",
                            "example.png");
                    userService.saveTestDataUser(userRegular1);

                    UserPostDto userRegular2 = new UserPostDto(
                            "Meredith",
                            "Mathews",
                            "mathMer.90@yah.couk",
                            "1234User2!",
                            "example.png");
                    userService.saveTestDataUser(userRegular2);

                    UserPostDto userRegular3 = new UserPostDto(
                            "Walter",
                            "Mason",
                            "walter1998@google.ca",
                            "1234User3!",
                            "example.png");
                    userService.saveTestDataUser(userRegular3);

                    UserPostDto userRegular4 = new UserPostDto(
                            "Barbara",
                            "Contreras",
                            "barbaraC-cont@google.ca",
                            "1234User4!",
                            "example.png");
                    userService.saveTestDataUser(userRegular4);

                    UserPostDto userRegular5 = new UserPostDto(
                            "Pedro",
                            "Hudson",
                            "pedroH@hotm.ca",
                            "1234User5!",
                            "example.png");
                    userService.saveTestDataUser(userRegular5);

                    UserPostDto userRegular6 = new UserPostDto(
                            "Felipe",
                            "Benitez",
                            "f.Benitez@yah.couk",
                            "1234User6!",
                            "example.png");
                    userService.saveTestDataUser(userRegular6);

                    UserPostDto userRegular7 = new UserPostDto(
                            "Virginia",
                            "Walters",
                            "walters.virg123@google.net",
                            "1234User7!",
                            "example.png");
                    userService.saveTestDataUser(userRegular7);

                    UserPostDto userRegular8 = new UserPostDto(
                            "Julian",
                            "Velasquez",
                            "jul.vel@example.ca",
                            "1234User8!",
                            "example.png");
                    userService.saveTestDataUser(userRegular8);

                    UserPostDto userRegular9 = new UserPostDto(
                            "Gabriel",
                            "Carter",
                            "gabriel.carter00@google.org",
                            "1234User9!",
                            "example.png");
                    userService.saveTestDataUser(userRegular9);

                    UserPostDto userRegular10 = new UserPostDto(
                            "Tatiana",
                            "Castaneda",
                            "tatiana.elit-you@yah.couk",
                            "1234User10!",
                            "example.png");
                    userService.saveTestDataUser(userRegular10);
            }
    }
}
