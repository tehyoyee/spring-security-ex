package com.taehyeong.backend.authentication.entity;

//@Entity
//@Getter
//@Setter
public enum AuthorityType {

    MENU1_READ,
    MENU1_WRITE,
    MENU2_READ,
    MENU2_WRITE,


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String name;
//
//    @JoinColumn(name = "role")
//    @ManyToOne
//    private Role role;

}

//        CREATE TABLE authority (
//        role_id BIGINT NOT NULL,
//        name VARCHAR(255) NOT NULL,
//        PRIMARY KEY (role_id, authorities),
//        CONSTRAINT FK_role_authority FOREIGN KEY (role_id)
//        REFERENCES role (id) ON DELETE CASCADE
//) ENGINE=InnoDB;
